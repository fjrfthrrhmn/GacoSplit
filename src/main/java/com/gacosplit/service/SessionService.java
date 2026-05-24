package com.gacosplit.service;

import com.gacosplit.model.Item;
import com.gacosplit.model.Person;
import com.gacosplit.model.Session;
import com.gacosplit.model.dto.SessionResponse;
import com.gacosplit.repository.ItemRepository;
import com.gacosplit.repository.PersonRepository;
import com.gacosplit.repository.SessionRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * =========================================================================
 *  SessionService — Business Logic untuk CRUD Session
 * =========================================================================
 *
 *  Fungsi:
 *  - Mengelola siklus hidup session (buat, baca, reset)
 *  - Mengelola peserta (tambah, hapus)
 *  - Mengelola item pesanan (tambah, update, hapus)
 *  - Validasi business rules sebelum menyimpan ke database
 *
 *  Kenapa perlu dipisah dari controller?
 *  - Controller hanya urus HTTP: method, path, status code, headers
 *  - Service urus LOGIKA: validasi, koordinasi repository, transaksi
 *  - Tanpa service, controller jadi "gemuk" dan susah di-test
 *  - Service bisa dipanggil dari mana saja (controller, scheduler,
 *    test) tanpa tergantung HTTP
 *
 *  Kenapa dipisah dari CalculationService?
 *  - SessionService = urus data (CRUD)
 *  - CalculationService = urus hitung (kalkulasi)
 *  - Dua tanggung jawab berbeda, dua file berbeda
 *  - Kalau ada bug di kalkulasi, tidak perlu edit SessionService
 *
 *  Flow data:
 *  HTTP Request
 *      → Controller (parse request, panggil service)
 *      → SessionService (validasi, koordinasi)
 *      → Repository (simpan/ambil dari database)
 *      ← Response balik ke controller → HTTP Response
 * =========================================================================
 */

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final PersonRepository personRepository;
    private final ItemRepository itemRepository;
    private final CalculationService calculationService;

    /*
     * Constructor injection — semua dependency di-inject otomatis.
     * Final fields → tidak bisa diubah (immutable).
     */
    public SessionService(SessionRepository sessionRepository,
                          PersonRepository personRepository,
                          ItemRepository itemRepository,
                          CalculationService calculationService) {
        this.sessionRepository = sessionRepository;
        this.personRepository = personRepository;
        this.itemRepository = itemRepository;
        this.calculationService = calculationService;
    }

    // ==================================================================
    //  SESSION OPERATIONS
    // ==================================================================

    /*
     * createSession: Membuat sesi split bill baru.
     *
     * @param name Nama sesi (bisa null → default "Session")
     * @return Session entity yang sudah disimpan
     *
     * Business rules:
     * - Nama tidak boleh kosong (trim)
     * - Kalau null atau kosong, pakai default "Session"
     */
    public Session createSession(String name) {
        if (name == null || name.trim().isEmpty()) {
            name = "Session";
        }
        Session session = new Session(name.trim());
        return sessionRepository.save(session);
    }

    /*
     * getSessionById: Mencari session berdasarkan ID.
     *
     * @param id UUID session (String)
     * @return Session entity jika ditemukan
     * @throws IllegalArgumentException jika session tidak ditemukan
     *
     * Error handling: method ini lempar exception, controller yang
     * akan menangkap dan mengembalikan HTTP 404.
     */
    public Session getSessionById(String id) {
        Session session = sessionRepository.findById(id).orElse(null);
        if (session == null) {
            throw new IllegalArgumentException("Session tidak ditemukan");
        }
        return session;
    }

    /*
     * resetSession: Menghapus semua data (people + items) dalam session,
     * tapi session-nya tetap ada.
     *
     * @param id UUID session
     *
     * @Transactional → semua operasi database dalam satu transaksi.
     * Kalau salah satu gagal, semua di-rollback (tidak ada data setengah
     * terhapus).
     */
    @Transactional
    public void resetSession(String id) {
        Session session = getSessionById(id);

        // Hapus semua item milik session ini dari database
        List<Item> items = itemRepository.findBySessionId(id);
        itemRepository.deleteAll(items);

        // Hapus relasi Person → Session, lalu hapus person
        for (Person person : session.getPeople()) {
            person.setSession(null);
            personRepository.delete(person);
        }
        session.getPeople().clear();

        sessionRepository.save(session);
    }

    // ==================================================================
    //  PERSON OPERATIONS
    // ==================================================================

    /*
     * addPerson: Menambahkan peserta baru ke dalam session.
     *
     * @param sessionId UUID session tujuan
     * @param name      Nama peserta
     * @return Person entity yang sudah dibuat
     *
     * Validasi:
     * - Nama tidak boleh kosong
     * - Session harus ada (di-validasi oleh getSessionById)
     *
     * Kenapa simpan lewat sessionRepository.save() bukan personRepository?
     * Karena Person punya relasi @ManyToOne ke Session.
     * Dengan cascade = CascadeType.ALL di Session.people,
     * menyimpan session otomatis menyimpan semua person-nya juga.
     */
    public Person addPerson(String sessionId, String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama tidak boleh kosong");
        }

        Session session = getSessionById(sessionId);
        Person person = new Person(name.trim());
        session.addPerson(person);
        sessionRepository.save(session);

        return person;
    }

    /*
     * removePerson: Menghapus peserta beserta semua item personal-nya.
     *
     * @param sessionId UUID session
     * @param personId  UUID peserta yang akan dihapus
     *
     * Yang terjadi:
     * 1. Validasi session dan person ada
     * 2. Hapus semua item personal milik orang ini (orderedBy = personId)
     * 3. Hapus person dari list people session
     * 4. Hapus entity Person dari database
     *
     * @Transactional → atomic: semua atau tidak sama sekali
     */
    @Transactional
    public void removePerson(String sessionId, String personId) {
        Session session = getSessionById(sessionId);

        Person person = personRepository.findById(personId).orElse(null);
        if (person == null) {
            throw new IllegalArgumentException("Person tidak ditemukan");
        }

        // Hapus semua item personal milik orang ini
        List<Item> personalItems = itemRepository.findByOrderedBy(personId);
        itemRepository.deleteAll(personalItems);
        session.getItems().removeAll(personalItems);

        // Hapus orang dari session dan database
        session.getPeople().remove(person);
        person.setSession(null);
        personRepository.delete(person);

        sessionRepository.save(session);
    }

    // ==================================================================
    //  ITEM OPERATIONS
    // ==================================================================

    /*
     * addItem: Menambahkan item (personal atau shared) ke session.
     *
     * @param sessionId UUID session tujuan
     * @param name      Nama menu
     * @param price     Harga satuan
     * @param quantity  Jumlah
     * @param isShared  true = shared, false = personal
     * @param orderedBy ID Person (null untuk shared item)
     * @return Item entity yang sudah dibuat
     *
     * Validasi:
     * - Nama tidak boleh kosong
     * - Harga harus >= 0
     * - Quantity harus > 0
     * - orderedBy harus ada untuk personal item (isShared = false)
     */
    public Item addItem(String sessionId, String name, BigDecimal price,
                        Integer quantity, Boolean isShared, String orderedBy) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama item tidak boleh kosong");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Harga harus angka valid");
        }
        if (quantity == null || quantity < 1) {
            throw new IllegalArgumentException("Quantity minimal 1");
        }
        if (!Boolean.TRUE.equals(isShared) && (orderedBy == null || orderedBy.isEmpty())) {
            throw new IllegalArgumentException(
                    "Personal item harus memiliki pemilik (orderedBy)");
        }

        Session session = getSessionById(sessionId);

        // Untuk shared item, orderedBy = null
        String finalOrderedBy = Boolean.TRUE.equals(isShared) ? null : orderedBy;

        Item item = new Item(name.trim(), price, quantity, isShared, finalOrderedBy);
        session.addItem(item);
        sessionRepository.save(session);

        return item;
    }

    /*
     * updateItem: Mengubah data item yang sudah ada.
     *
     * @param sessionId UUID session
     * @param itemId    UUID item yang akan diubah
     * @param body      Map berisi field yang akan diubah
     * @return Item entity yang sudah diupdate
     *
     * Partial update: field yang tidak dikirim tidak berubah.
     * Ini disebut "MERGE" semantics — sesuai dengan HTTP PUT.
     */
    public Item updateItem(String sessionId, String itemId,
                           Map<String, Object> body) {

        // Validasi session dan item ada
        getSessionById(sessionId);

        Item item = itemRepository.findById(itemId).orElse(null);
        if (item == null) {
            throw new IllegalArgumentException("Item tidak ditemukan");
        }

        // Partial update — field yang dikirim saja yang diubah
        if (body.containsKey("name")) {
            String name = body.get("name").toString();
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Nama item tidak boleh kosong");
            }
            item.setName(name.trim());
        }
        if (body.containsKey("price")) {
            try {
                BigDecimal price = new BigDecimal(body.get("price").toString());
                if (price.compareTo(BigDecimal.ZERO) < 0) {
                    throw new IllegalArgumentException("Harga tidak boleh negatif");
                }
                item.setPrice(price);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Harga harus angka valid");
            }
        }
        if (body.containsKey("quantity")) {
            try {
                Integer quantity = Integer.valueOf(body.get("quantity").toString());
                if (quantity < 1) {
                    throw new IllegalArgumentException("Quantity minimal 1");
                }
                item.setQuantity(quantity);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Quantity harus angka valid");
            }
        }

        return itemRepository.save(item);
    }

    /*
     * removeItem: Menghapus item dari session.
     *
     * @param sessionId UUID session
     * @param itemId    UUID item yang akan dihapus
     */
    @Transactional
    public void removeItem(String sessionId, String itemId) {
        Session session = getSessionById(sessionId);

        Item item = itemRepository.findById(itemId).orElse(null);
        if (item == null) {
            throw new IllegalArgumentException("Item tidak ditemukan");
        }

        // Hapus dari relasi session dan dari database
        session.getItems().remove(item);
        item.setSession(null);
        itemRepository.delete(item);

        sessionRepository.save(session);
    }

    // ==================================================================
    //  CALCULATION DELEGATION
    // ==================================================================

    /*
     * calculateSession: Menghitung tagihan untuk session tertentu.
     *
     * Method ini adalah JEMBATAN antara SessionService dan
     * CalculationService:
     * - SessionService menyediakan data (session, people)
     * - CalculationService melakukan perhitungan
     *
     * Kenapa tidak langsung panggil calculationService.calculate() dari
     * controller? Karena controller tidak perlu tahu bahwa ada
     * CalculationService. Controller cukup panggil
     * sessionService.calculateSession(id).
     */
    public Map<String, Object> calculateSession(String sessionId) {
        Session session = getSessionById(sessionId);

        // Validasi: minimal 1 orang
        // (CalculationService juga validasi, tapi lebih baik deteksi dini)
        List<Person> people = session.getPeople();
        if (people == null || people.isEmpty()) {
            throw new IllegalArgumentException(
                    "Minimal 1 orang untuk kalkulasi");
        }

        // Delegasikan perhitungan ke CalculationService
        return calculationService.calculate(session, people);
    }

    // ==================================================================
    //  DTO CONVERSION
    // ==================================================================

    /*
     * toSessionResponse: Mengonversi Session entity ke SessionResponse DTO.
     *
     * Fungsi:
     * - Memfilter data yang dikembalikan ke frontend
     * - Mencegah infinite loop JSON (karena relasi bidirectional)
     * - Hanya mengembalikan field yang frontend butuhkan
     *
     * Kenapa method ini di service, bukan di controller?
     * - Konversi entity → DTO adalah business logic
     * - Bisa dipakai ulang kalau ada consumer lain (WebSocket, dll)
     * - Controller jadi lebih bersih
     */
    public SessionResponse toSessionResponse(String sessionId) {
        Session session = getSessionById(sessionId);

        SessionResponse response = new SessionResponse(session);

        // People summaries
        List<SessionResponse.PersonSummary> personSummaries = new ArrayList<>();
        for (Person person : session.getPeople()) {
            personSummaries.add(new SessionResponse.PersonSummary(
                    person.getId(), person.getName()));
        }
        response.setPeople(personSummaries);

        // Items summaries
        List<SessionResponse.ItemSummary> itemSummaries = new ArrayList<>();
        for (Item item : session.getItems()) {
            itemSummaries.add(new SessionResponse.ItemSummary(
                    item.getId(), item.getName(), item.getPrice(),
                    item.getQuantity(), item.getIsShared(), item.getOrderedBy()));
        }
        response.setItems(itemSummaries);

        return response;
    }
}
