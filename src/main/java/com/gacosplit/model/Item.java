package com.gacosplit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

/*
 * =========================================================================
 *  Item — Entity untuk item pesanan (personal & shared)
 * =========================================================================
 *
 *  Fungsi:
 *  - Mewakili satu item pesanan dalam sesi split bill
 *  - Bisa berupa item personal (milik orang tertentu) ATAU
 *    item bersama (shared — dibagi rata ke semua peserta)
 *  - Semua item (personal + shared) disimpan dalam SATU tabel
 *
 *  Pembedaan personal vs shared:
 *  - isShared = false → item personal, orderedBy berisi ID Person
 *  - isShared = true  → item bersama, orderedBy = null
 *
 *  Kenapa satu tabel, bukan dua?
 *  - Struktur field-nya sama (nama, harga, jumlah)
 *  - Memudahkan query "semua item dalam session"
 *  - Tidak perlu join/union dua tabel
 *  - Lebih sederhana untuk pembelajaran
 *
 *  Relasi:
 *  - @ManyToOne ke Session: setiap item pasti milik satu session
 *
 *  Kenapa orderedBy String, bukan @ManyToOne ke Person?
 *  - Lebih sederhana: cukup string ID, bukan relasi formal
 *  - Item personal bisa dicari dengan query sederhana
 *  - Shared item tidak punya owner (orderedBy = null)
 *  - Menghindari relasi bidirectional yang kompleks
 * =========================================================================
 */

@Entity
@Table(name = "items")
public class Item {

    /*
     * ─────────────────────────────────────────────────────────────
     *  PRIMARY KEY: UUID String
     * ─────────────────────────────────────────────────────────────
     */
    @Id
    private String id;

    /*
     * Nama menu (sama persis dengan nama di template Gacoan).
     * Contoh: "Mie Gacoan Level 3", "Tahu Crispy"
     */
    private String name;

    /*
     * Harga SATUAN item (bukan total).
     * Total = price × quantity.
     * Contoh: Mie Gacoan Level 3 = 18000
     */
    private BigDecimal price;

    /*
     * Jumlah item yang dipesan.
     * Contoh: 2 porsi Tahu Crispy → quantity = 2
     * Validasi: min 1, max 99 (dari frontend)
     */
    private Integer quantity;

    /*
     * Penanda jenis item:
     * - false = personal item (milik orang tertentu)
     * - true  = shared item (dibagi rata ke semua peserta)
     *
     * Default: false (personal item)
     */
    private Boolean isShared = false;

    /*
     * ID orang yang memesan item ini.
     * Hanya berlaku untuk item personal (isShared = false).
     * Untuk shared item (isShared = true), field ini null.
     *
     * Ini STRING biasa, bukan @ManyToOne ke Person.
     * Kenapa? Karena shared item tidak punya pemilik.
     * Kalau pakai @ManyToOne nullable, jadi aneh secara konsep.
     *
     * Nanti di Service Layer, kita akan query:
     * "cari semua Item WHERE orderedBy = :personId"
     * untuk mendapatkan item personal seseorang.
     */
    private String orderedBy;

    /*
     * ─────────────────────────────────────────────────────────────
     *  RELASI: Item → Session (Many-to-One)
     * ─────────────────────────────────────────────────────────────
     *
     *  Setiap item (personal maupun shared) pasti milik satu session.
     *  Di database: tabel "items" punya kolom "session_id".
     *
     *  Fetch type default @ManyToOne = EAGER.
     *  Artinya Session ikut di-load saat Item diambil.
     *  Untuk skala kecil ini tidak masalah.
     */
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    /*
     * =============================================================
     *  CONSTRUCTORS
     * =============================================================
     */
    public Item() {
    }

    /*
     * Constructor untuk membuat item baru.
     *
     * @param name     Nama menu
     * @param price    Harga satuan
     * @param quantity Jumlah
     * @param isShared true = shared, false = personal
     * @param orderedBy ID person (null jika shared)
     */
    public Item(String name, BigDecimal price, Integer quantity,
                Boolean isShared, String orderedBy) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.isShared = isShared;
        this.orderedBy = orderedBy;
    }

    /*
     * =============================================================
     *  GETTER & SETTER
     * =============================================================
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getIsShared() {
        return isShared;
    }

    public void setIsShared(Boolean isShared) {
        this.isShared = isShared;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    /*
     * Helper: hitung subtotal item ini (price × quantity)
     * Berguna nanti di service layer untuk perhitungan.
     */
    public BigDecimal getSubtotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
