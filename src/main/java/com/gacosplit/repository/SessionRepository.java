package com.gacosplit.repository;

import com.gacosplit.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * =========================================================================
 *  SessionRepository — akses data Session ke database
 * =========================================================================
 *
 *  Repository adalah komponen Spring Data JPA yang menyediakan
 *  operasi CRUD standar secara otomatis.
 *
 *  Dengan extends JpaRepository<Session, String>:
 *  - Session: entity yang dikelola
 *  - String: tipe data primary key (ID)
 *
 *  Method GRATIS dari JpaRepository (tanpa perlu implementasi):
 *  - findAll()       → SELECT * FROM sessions
 *  - findById(id)    → SELECT * FROM sessions WHERE id = ?
 *  - save(entity)    → INSERT atau UPDATE
 *  - delete(entity)  → DELETE
 *  - count()         → SELECT COUNT(*) FROM sessions
 *  - existsById(id)  → cek apakah ID ada
 *
 *  Ini yang disebut "Spring Data magic" — cukup deklarasi interface,
 *  Spring langsung menyediakan implementasi secara otomatis.
 * =========================================================================
 */

@Repository
/*
 * @Repository memberitahu Spring bahwa interface ini adalah
 * bean repository yang akan di-inject ke komponen lain
 * (misalnya ke SessionService nanti).
 */
public interface SessionRepository extends JpaRepository<Session, String> {

    /*
     * Tidak perlu method tambahan untuk sekarang.
     * Method CRUD standar dari JpaRepository sudah cukup.
     *
     * Nanti di Fase 3.3 kita bisa tambah method kustom:
     * - findBy... untuk query spesifik
     * - @Query untuk query manual
     */
}
