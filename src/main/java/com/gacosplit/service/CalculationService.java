package com.gacosplit.service;

import com.gacosplit.model.Item;
import com.gacosplit.model.Person;
import com.gacosplit.model.Session;
import com.gacosplit.repository.ItemRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/*
 * =========================================================================
 *  CalculationService — Logika Perhitungan Split Bill
 * =========================================================================
 *
 *  Fungsi:
 *  - Memisahkan logika perhitungan dari controller
 *  - Menghitung total personal, shared, dan grand total
 *  - Mengembalikan data terstruktur untuk response API
 *
 *  Kenapa dipisah dari SessionService?
 *  - CalculationService FOKUS pada SATU tanggung jawab: hitung
 *  - SessionService urus CRUD (create-read-update-delete)
 *  - Separation of Concerns: kalau ada perubahan rumus,
 *    kita hanya edit satu file (CalculationService)
 *  - Lebih mudah di-test: bisa test calculation tanpa setup session
 *
 *  Flow data:
 *  Controller menerima request
 *      → panggil SessionService.getSession(id) untuk validasi
 *      → panggil CalculationService.calculate(session, people, items)
 *      → hasil perhitungan dikembalikan ke controller
 *      → controller format response JSON
 *
 *  Kenapa tidak taruh logic di controller?
 *  - Controller harus fokus pada HTTP (request/response)
 *  - Kalau logic ada di controller, susah di-test
 *  - Logic perhitungan bisa dipakai ulang (misal: untuk export PDF)
 * =========================================================================
 */

@Service
/*
 * @Service → memberitahu Spring bahwa class ini adalah Service Bean.
 * Spring akan otomatis membuat instance-nya (dependency injection).
 * Beda dengan @Component: @Service lebih spesifik untuk business logic.
 */
public class CalculationService {

    /*
     * PPN (Pajak Pertambahan Nilai) — Indonesia VAT rate.
     * Berlaku 11% sejak 1 April 2022 (UU HPP).
     * Digunakan untuk menghitung pajak dari total pesanan per orang.
     */
    private static final BigDecimal PPN_RATE = new BigDecimal("0.11");

    private final ItemRepository itemRepository;

    /*
     * Constructor Injection — kebutuhan repository di-inject otomatis.
     * Final field → tidak bisa diganti setelah di-set.
     */
    public CalculationService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /*
     * ================================================================
     *  METHOD UTAMA: calculate
     * ================================================================
     *
     *  Fungsi: menghitung rincian tagihan split bill untuk satu session.
     *
     *  Alur kalkulasi:
     *  1. Ambil semua item dalam session
     *  2. Pisahkan shared items vs personal items
     *  3. Hitung total shared (price × quantity untuk semua shared item)
     *  4. Hitung shared per orang (total shared ÷ jumlah orang)
     *  5. Untuk setiap orang:
     *     a. Hitung total personal items miliknya
     *     b. Total tagihan = personalTotal + sharedPerPerson
     *  6. Grand total = jumlah semua tagihan per orang
     *
     *  Rumus (dari docs/ARCHITECTURE.md):
     *    PersonalTotal(orang) = Σ(item.price × item.quantity)
     *    SharedTotal = Σ(sharedItem.price × sharedItem.quantity)
     *    SharedPerOrang = SharedTotal ÷ JumlahOrang
     *    DasarPengenaanPajak = PersonalTotal + SharedPerOrang
     *    PPN = DasarPengenaanPajak × 11% (dibulatkan ke rupiah penuh)
     *    TotalPerOrang = PersonalTotal + SharedPerOrang + PPN
     *
     *  Edge cases:
     *  - Session tanpa orang → return error
     *  - Session tanpa item → semua total = 0
     *  - Hanya 1 orang → tetap dihitung (meskipun tidak ada yang dibagi)
     *  - Shared item tanpa personal item → total cuma dari shared
     *
     *  Kenapa pakai BigDecimal?
     *  - double/float punya masalah presisi (0.1 + 0.2 = 0.30000000000000004)
     *  - BigDecimal menjamin akurasi perhitungan uang
     *  - RoundingMode.HALF_UP = pembulatan standar Indonesia
     *  - Scale 0 = tidak ada desimal (karena uang Indonesia pakai rupiah,
     *    tidak perlu pecahan sen)
     *
     *  @param session Session entity yang akan dihitung
     *  @param people  Daftar peserta dalam session
     *  @return Map<String, Object> berisi hasil perhitungan lengkap
     * ================================================================
     */
    public Map<String, Object> calculate(Session session, List<Person> people) {

        /*
         * ───────────────────────────────────────────────────────────
         *  Validasi: minimal 1 orang
         * ───────────────────────────────────────────────────────────
         *  Kalau tidak ada orang, tidak bisa hitung shared per orang.
         *  (Pembagian dengan nol akan error.)
         */
        if (people == null || people.isEmpty()) {
            throw new IllegalArgumentException(
                    "Session harus memiliki minimal 1 orang untuk kalkulasi");
        }

        // Ambil semua item milik session ini dari database
        List<Item> allItems = itemRepository.findBySessionId(session.getId());

        // ──────────────────────────────────────────────────────────
        //  Langkah 1: Pisahkan shared items dan personal items
        // ──────────────────────────────────────────────────────────
        List<Item> sharedItems = new ArrayList<>();
        Map<String, List<Item>> personalItemsByPerson = new HashMap<>();

        for (Item item : allItems) {
            if (Boolean.TRUE.equals(item.getIsShared())) {
                // Item bersama — dibagi rata ke semua orang
                sharedItems.add(item);
            } else {
                // Item personal — kelompokkan berdasarkan ID pemesan
                String personId = item.getOrderedBy();
                if (personId != null) {
                    personalItemsByPerson
                            .computeIfAbsent(personId, k -> new ArrayList<>())
                            .add(item);
                }
            }
        }

        // ──────────────────────────────────────────────────────────
        //  Langkah 2: Hitung total shared
        // ──────────────────────────────────────────────────────────
        //  Jumlahkan semua (price × quantity) dari shared items.
        BigDecimal sharedTotal = BigDecimal.ZERO;
        for (Item item : sharedItems) {
            sharedTotal = sharedTotal.add(item.getSubtotal());
        }

        // ──────────────────────────────────────────────────────────
        //  Langkah 3: Hitung shared per orang
        // ──────────────────────────────────────────────────────────
        //  Rumus: sharedTotal ÷ jumlahOrang
        //  Dibulatkan ke rupiah penuh (scale = 0).
        //  HALF_UP = 0.5 dibulatkan ke atas.
        int peopleCount = people.size();
        BigDecimal sharedPerPerson = sharedTotal.divide(
                BigDecimal.valueOf(peopleCount), 0, RoundingMode.HALF_UP);

        // ──────────────────────────────────────────────────────────
        //  Langkah 4: Hitung per orang
        // ──────────────────────────────────────────────────────────
        List<Map<String, Object>> perPersonList = new ArrayList<>();
        BigDecimal grandTotal = BigDecimal.ZERO;

        for (Person person : people) {
            // Ambil item personal orang ini (atau empty list)
            List<Item> personItems = personalItemsByPerson.getOrDefault(
                    person.getId(), new ArrayList<>());

            // Hitung personal total: jumlah semua (price × quantity)
            BigDecimal personalTotal = BigDecimal.ZERO;
            List<Map<String, Object>> itemDetails = new ArrayList<>();
            for (Item item : personItems) {
                BigDecimal subtotal = item.getSubtotal();
                personalTotal = personalTotal.add(subtotal);

                Map<String, Object> detail = new HashMap<>();
                detail.put("name", item.getName());
                detail.put("price", item.getPrice());
                detail.put("quantity", item.getQuantity());
                detail.put("subtotal", subtotal);
                itemDetails.add(detail);
            }

            // Total yang harus dibayar = personal + shared portion
            BigDecimal amountOwed = personalTotal.add(sharedPerPerson);

            // Hitung PPN 11% dari Dasar Pengenaan Pajak (personal + shared)
            BigDecimal ppn = amountOwed.multiply(PPN_RATE)
                    .setScale(0, RoundingMode.HALF_UP);

            // Total akhir setelah PPN
            BigDecimal totalAfterPpn = amountOwed.add(ppn);
            grandTotal = grandTotal.add(totalAfterPpn);

            // Set transient fields di Person entity
            // (berguna kalau entity ini dipakai lagi nanti)
            person.setPersonalTotal(personalTotal);
            person.setSharedPortion(sharedPerPerson);
            person.setAmountOwed(totalAfterPpn);
            person.setPpn(ppn);

            // Format response per orang
            Map<String, Object> personResult = new HashMap<>();
            personResult.put("personId", person.getId());
            personResult.put("name", person.getName());
            personResult.put("personalTotal", personalTotal);
            personResult.put("sharedPortion", sharedPerPerson);
            personResult.put("ppn", ppn);
            personResult.put("total", totalAfterPpn);
            personResult.put("items", itemDetails);

            perPersonList.add(personResult);
        }

        // ──────────────────────────────────────────────────────────
        //  Langkah 5: Detail shared items
        // ──────────────────────────────────────────────────────────
        List<Map<String, Object>> sharedItemDetails = new ArrayList<>();
        for (Item item : sharedItems) {
            Map<String, Object> detail = new HashMap<>();
            detail.put("name", item.getName());
            detail.put("price", item.getPrice());
            detail.put("quantity", item.getQuantity());
            detail.put("subtotal", item.getSubtotal());
            sharedItemDetails.add(detail);
        }

        // ──────────────────────────────────────────────────────────
        //  Set transient fields di Session entity
        // ──────────────────────────────────────────────────────────
        session.setTotalAmount(grandTotal);
        session.setSharedAmount(sharedTotal);

        // ──────────────────────────────────────────────────────────
        //  Build response lengkap
        // ──────────────────────────────────────────────────────────
        Map<String, Object> result = new HashMap<>();
        result.put("sessionId", session.getId());
        result.put("sessionName", session.getName());
        result.put("peopleCount", peopleCount);
        result.put("sharedTotal", sharedTotal);
        result.put("sharedPerPerson", sharedPerPerson);

        // Hitung total PPN dari semua orang
        BigDecimal ppnTotal = BigDecimal.ZERO;
        for (Map<String, Object> p : perPersonList) {
            ppnTotal = ppnTotal.add((BigDecimal) p.get("ppn"));
        }
        result.put("ppnRate", PPN_RATE);
        result.put("ppnTotal", ppnTotal);

        result.put("grandTotal", grandTotal);
        result.put("perPerson", perPersonList);
        result.put("sharedItems", sharedItemDetails);

        return result;
    }
}
