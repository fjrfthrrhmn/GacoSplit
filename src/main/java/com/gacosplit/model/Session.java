package com.gacosplit.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
 * =========================================================================
 *  Session — Entity untuk sesi split bill
 * =========================================================================
 *
 *  Fungsi:
 *  - Mewakili satu sesi makan bersama (misal: "Makan Siang Kantor")
 *  - Menampung daftar orang (peserta) dan semua item pesanan
 *  - Setiap session memiliki ID unik (UUID), nama, dan timestamp
 *
 *  Relasi:
 *  - @OneToMany ke Person: satu session bisa punya banyak peserta
 *    (cascade ALL + orphanRemoval = true → jika session dihapus,
 *     semua person ikut terhapus)
 *  - @OneToMany ke Item: satu session bisa punya banyak item
 *    (baik personal maupun shared)
 *
 *  Flow data:
 *  1. User buat session baru → endpoint POST /api/sessions
 *  2. User tambah orang → endpoint POST /api/sessions/{id}/people
 *  3. User tambah item → endpoint POST /api/sessions/{id}/items
 *  4. User hitung tagihan → endpoint GET /api/sessions/{id}/calculate
 *
 *  Field @Transient:
 *  - totalAmount dan sharedAmount TIDAK disimpan di database
 *  - Nilainya dihitung oleh CalculationService nanti
 *  - Disimpan di sini agar mudah diakses saat response
 * =========================================================================
 */

@Entity
/*
 * @Entity → memberitahu JPA/Hibernate bahwa class ini adalah entity
 * yang akan dipetakan ke tabel database.
 *
 * @Table → menentukan nama tabel di database.
 * "sessions" karena "session" adalah reserved word di SQL.
 */
@Table(name = "sessions")
public class Session {

    /*
     * ─────────────────────────────────────────────────────────────
     *  PRIMARY KEY: UUID String
     * ─────────────────────────────────────────────────────────────
     *
     *  Kenapa String, bukan Long auto-increment?
     *  - UUID unik secara global, aman untuk API
     *  - Tidak perlu khawatir konflik ID saat multiple session
     *  - Bisa generate dari frontend (seperti di app.js) atau backend
     *
     *  Nilai UUID digenerate di constructor (lihat bawah).
     *  Tidak pakai @GeneratedValue karena kita generate manual.
     */
    @Id
    private String id;

    /*
     * Nama sesi, misal: "Makan Siang Kantor" atau "Nongkrong Gacoan"
     * Boleh null → nanti akan diisi default "Session" di service layer
     */
    private String name;

    /*
     * Timestamp kapan session dibuat.
     * Diset otomatis di constructor (LocalDateTime.now()).
     * Berguna untuk menampilkan riwayat sesi.
     */
    private LocalDateTime createdAt;

    /*
     * ─────────────────────────────────────────────────────────────
     *  RELASI: Session → Person (One-to-Many)
     * ─────────────────────────────────────────────────────────────
     *
     *  Satu session bisa memiliki banyak peserta (Person).
     *  Di database: tabel "persons" punya foreign key "session_id"
     *  yang mengacu ke tabel "sessions".
     *
     *  mappedBy = "session" → field "session" di class Person
     *  yang memiliki relasi (pemilik foreign key).
     *
     *  cascade = CascadeType.ALL:
     *    - Jika session di-save, semua person ikut di-save
     *    - Jika session di-delete, semua person ikut di-delete
     *
     *  orphanRemoval = true:
     *    - Jika person dihapus dari list people, otomatis
     *      di-delete dari database
     */
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Person> people = new ArrayList<>();

    /*
     * ─────────────────────────────────────────────────────────────
     *  RELASI: Session → Item (One-to-Many)
     * ─────────────────────────────────────────────────────────────
     *
     *  Satu session bisa memiliki banyak item pesanan.
     *  Baik item personal (milik orang tertentu) maupun
     *  item bersama (shared).
     *
     *  Sama seperti Person: cascade ALL + orphanRemoval.
     */
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    /*
     * ─────────────────────────────────────────────────────────────
     *  FIELD KALKULASI (@Transient)
     * ─────────────────────────────────────────────────────────────
     *
     *  @Transient → field ini TIDAK disimpan di database.
     *  Nilainya akan dihitung oleh CalculationService saat
     *  mengembalikan response ke frontend.
     *
     *  Kenapa disimpan di entity? Agar mudah diakses saat
     *  membuat response DTO.
     */
    @Transient
    private BigDecimal totalAmount;

    @Transient
    private BigDecimal sharedAmount;

    /*
     * =============================================================
     *  CONSTRUCTOR
     * =============================================================
     *
     *  Constructor kosong (no-arg) WAJIB ada untuk JPA.
     *  Hibernate membutuhkan constructor default untuk membuat
     *  instance entity via reflection.
     */
    public Session() {
    }

    /*
     * Constructor dengan nama session.
     * Otomatis:
     * - Generate UUID untuk id
     * - Set createdAt = waktu sekarang
     */
    public Session(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.createdAt = LocalDateTime.now();
    }

    /*
     * =============================================================
     *  GETTER & SETTER
     * =============================================================
     *
     *  Getter/setter manual (tanpa Lombok) agar lebih transparan
     *  untuk pembelajaran.
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getSharedAmount() {
        return sharedAmount;
    }

    public void setSharedAmount(BigDecimal sharedAmount) {
        this.sharedAmount = sharedAmount;
    }

    /*
     * Helper method: tambah orang ke sesi
     * Sekaligus set relasi bidirectional (person.session = this)
     * Biar tidak lupa nyetel relasi dua arah.
     */
    public void addPerson(Person person) {
        people.add(person);
        person.setSession(this);
    }

    /*
     * Helper method: tambah item ke sesi
     */
    public void addItem(Item item) {
        items.add(item);
        item.setSession(this);
    }
}
