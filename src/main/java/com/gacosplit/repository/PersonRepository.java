package com.gacosplit.repository;

import com.gacosplit.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * =========================================================================
 *  PersonRepository — akses data Person ke database
 * =========================================================================
 *
 *  Sama seperti SessionRepository: extends JpaRepository.
 *  Semua method CRUD standar otomatis tersedia.
 *
 *  Nanti di Fase 3.3 akan ditambah method kustom:
 *  - findBySessionId(String sessionId) → cari semua person dalam session
 *  - countBySessionId(String sessionId) → hitung jumlah person
 * =========================================================================
 */

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

    /*
     * Query method: Spring Data JPA akan otomatis membuat
     * implementasi dari method ini berdasarkan nama method.
     *
     * Contoh: findBySessionId(String sessionId)
     * akan menghasilkan query:
     *   SELECT * FROM persons WHERE session_id = ?
     *
     * Method ini akan ditambahkan nanti saat membuat Service Layer
     * (Fase 3.3).
     */
}
