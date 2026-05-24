package com.gacosplit.repository;

import com.gacosplit.model.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * =========================================================================
 *  ItemRepository — akses data Item ke database
 * =========================================================================
 *
 *  Repository untuk entity Item.
 *  Nanti akan digunakan untuk query:
 *  - Semua item dalam session
 *  - Item personal seseorang (WHERE orderedBy = ?)
 *  - Item shared (WHERE isShared = true)
 * =========================================================================
 */

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

    /*
     * Cari semua item dalam satu session.
     * Spring Data JPA otomatis membuat query:
     *   SELECT * FROM items WHERE session_id = ?
     *
     * @param sessionId ID session
     * @return List<Item> semua item dalam session tersebut
     */
    List<Item> findBySessionId(String sessionId);

    /*
     * Cari item personal milik orang tertentu.
     * Query: SELECT * FROM items WHERE ordered_by = ?
     *
     * @param personId ID person
     * @return List<Item> item personal orang tersebut
     */
    List<Item> findByOrderedBy(String personId);

    /*
     * Cari semua item bersama (shared) dalam session.
     * Query: SELECT * FROM items WHERE session_id = ? AND is_shared = true
     *
     * @param sessionId ID session
     * @return List<Item> shared items
     */
    List<Item> findBySessionIdAndIsSharedTrue(String sessionId);
}
