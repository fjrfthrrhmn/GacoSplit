package com.gacosplit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.util.UUID;

/*
 * =========================================================================
 *  Person — Entity untuk peserta split bill
 * =========================================================================
 *
 *  Fungsi:
 *  - Mewakili satu orang peserta dalam sesi split bill
 *  - Setiap Person terikat ke satu Session (tidak bisa pindah)
 *  - Menyimpan nama peserta saja; data pesanan ada di Item
 *
 *  Relasi:
 *  - @ManyToOne ke Session: banyak Person bisa dalam satu Session
 *    (Ini adalah sisi "pemilik" relasi, ditandai dengan @JoinColumn)
 *
 *  Kenapa tidak ada @OneToMany ke Item?
 *  - Item punya field "orderedBy" (String) yang menyimpan ID Person
 *  - Hubungan ini sengaja TIDAK dibuat relasi JPA formal
 *  - Lebih sederhana: cukup query "WHERE orderedBy = :personId"
 *  - Nanti pada Fase 3.3 (Service Layer) akan dijelaskan caranya
 *
 *  Field @Transient:
 *  - personalTotal, sharedPortion, amountOwed TIDAK disimpan
 *  - Nilainya dihitung oleh CalculationService
 * =========================================================================
 */

@Entity
/*
 * @Table(name = "persons") — defaultnya "person", tapi kita
 * pake "persons" (plural) biar konsisten dengan standar tabel.
 */
@Table(name = "persons")
public class Person {

    /*
     * ─────────────────────────────────────────────────────────────
     *  PRIMARY KEY: UUID String
     * ─────────────────────────────────────────────────────────────
     *
     *  Sama seperti Session: UUID sebagai String.
     *  Digenerate manual di constructor.
     *
     *  Kenapa bukan auto-increment Long?
     *  - UUID bisa digenerate dari frontend (app.js pakai Date.now())
     *  - Lebih aman untuk API (tidak bisa ditebak urutan ID)
     */
    @Id
    private String id;

    /*
     * Nama peserta.
     * Contoh: "Fajar", "Rudi", "Toni"
     */
    private String name;

    /*
     * ─────────────────────────────────────────────────────────────
     *  RELASI: Person → Session (Many-to-One)
     * ─────────────────────────────────────────────────────────────
     *
     *  Banyak Person bisa merujuk ke Session yang sama.
     *  Di database: tabel "persons" punya kolom "session_id"
     *  sebagai foreign key ke tabel "sessions".
     *
     *  @JoinColumn(name = "session_id"):
     *    - Menentukan nama kolom foreign key di tabel persons
     *    - Kolom ini akan berisi ID session
     *
     *  Ini sisi "pemilik" relasi (owner side).
     *  Karena Person yang punya foreign key, bukan Session.
     *
     *  Fetch type: default untuk @ManyToOne adalah EAGER.
     *  Artinya data Session langsung di-load bersamaan dengan Person.
     *  Ini OK untuk skala kecil; nanti bisa diubah ke LAZY.
     */
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    /*
     * ─────────────────────────────────────────────────────────────
     *  FIELD KALKULASI (@Transient)
     * ─────────────────────────────────────────────────────────────
     *
     *  personalTotal: jumlah harga semua item personal orang ini
     *  sharedPortion: bagian dari shared item yang dibagi rata
     *  amountOwed: total yang harus dibayar = personalTotal + sharedPortion
     *
     *  Semua diisi oleh CalculationService saat GET /calculate
     */
    @Transient
    private BigDecimal personalTotal;

    @Transient
    private BigDecimal sharedPortion;

    @Transient
    private BigDecimal amountOwed;

    /*
     * =============================================================
     *  CONSTRUCTORS
     * =============================================================
     *
     *  Constructor no-arg: WAJIB untuk JPA.
     *  Constructor with name: generate UUID otomatis.
     */
    public Person() {
    }

    public Person(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
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

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public BigDecimal getPersonalTotal() {
        return personalTotal;
    }

    public void setPersonalTotal(BigDecimal personalTotal) {
        this.personalTotal = personalTotal;
    }

    public BigDecimal getSharedPortion() {
        return sharedPortion;
    }

    public void setSharedPortion(BigDecimal sharedPortion) {
        this.sharedPortion = sharedPortion;
    }

    public BigDecimal getAmountOwed() {
        return amountOwed;
    }

    public void setAmountOwed(BigDecimal amountOwed) {
        this.amountOwed = amountOwed;
    }
}
