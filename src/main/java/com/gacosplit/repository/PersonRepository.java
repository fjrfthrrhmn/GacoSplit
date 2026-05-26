package com.gacosplit.repository;

import com.gacosplit.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * =========================================================================
 *  PersonRepository — Akses data tabel persons
 * =========================================================================
 *
 *  extends JpaRepository<Person, String>:
 *  - Person: entity yang dikelola
 *  - String: tipe data primary key (UUID)
 *
 *  Method GRATIS dari JpaRepository:
 *  - findAll()       → ambil semua person
 *  - findById(id)    → cari person berdasarkan ID
 *  - save(person)    → simpan person baru / update
 *  - delete(person)  → hapus person
 *
 *  Kalau butuh query spesifik (misal cari person dalam satu session),
 *  tinggal tambah method dengan nama yang sesuai — Spring otomatis
 *  bikin query-nya dari nama method.
 * =========================================================================
 */

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

    /*
     * Saat ini pake method standar dari JpaRepository.
     * Method kustom kayak findBySessionId() bisa ditambah kapan aja —
     * Spring Data JPA otomatis bikin implementasinya dari nama method.
     */
}
