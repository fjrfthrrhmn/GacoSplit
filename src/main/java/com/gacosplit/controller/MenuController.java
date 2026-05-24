package com.gacosplit.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * =========================================================================
 *  MenuController — REST API untuk daftar menu Gacoan
 * =========================================================================
 *
 *  Fungsi: Menyediakan daftar menu Gacoan untuk frontend.
 *
 *  Saat ini data menu di-hardcode (sama seperti di frontend app.js).
 *  Nanti bisa diambil dari database jika ada fitur admin menu.
 *
 *  Endpoint:
 *  - GET /api/menu → daftar menu beserta harga
 *
 *  Response:
 *  [
 *    { "name": "Mie Gacoan Level 1", "price": 16000 },
 *    { "name": "Mie Gacoan Level 2", "price": 17000 },
 *    ...
 *  ]
 * =========================================================================
 */

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    /*
     * Data menu template Gacoan.
     * Sumber: docs/architecture/data-model.md
     * Sama persis dengan data di frontend (src/main/resources/static/js/app.js).
     *
     * Nanti bisa diganti dengan query ke database.
     */
    private static final Map<String, BigDecimal> MENU_ITEMS = new HashMap<>();

    static {
        MENU_ITEMS.put("Mie Gacoan Level 1", new BigDecimal("16000"));
        MENU_ITEMS.put("Mie Gacoan Level 2", new BigDecimal("17000"));
        MENU_ITEMS.put("Mie Gacoan Level 3", new BigDecimal("18000"));
        MENU_ITEMS.put("Mie Gacoan Level 4", new BigDecimal("19000"));
        MENU_ITEMS.put("Mie Gacoan Level 5", new BigDecimal("20000"));
        MENU_ITEMS.put("Mie Katsu", new BigDecimal("20000"));
        MENU_ITEMS.put("Mie Pangsit", new BigDecimal("20000"));
        MENU_ITEMS.put("Dimsum", new BigDecimal("15000"));
        MENU_ITEMS.put("Es Teh Manis", new BigDecimal("5000"));
        MENU_ITEMS.put("Es Jeruk", new BigDecimal("6000"));
        MENU_ITEMS.put("Tahu Crispy", new BigDecimal("8000"));
    }

    // ==================================================================
    //  1. GET /api/menu — Ambil daftar menu
    // ==================================================================
    /*
     * Fungsi: Mengembalikan daftar menu Gacoan yang tersedia.
     *
     * Output (200 OK):
     *   [
     *     { "name": "Mie Gacoan Level 1", "price": 16000 },
     *     { "name": "Mie Gacoan Level 2", "price": 17000 },
     *     ...
     *   ]
     *
     * Kenapa GET? Karena hanya membaca data, tidak mengubah apapun.
     * Ini adalah endpoint publik — tidak perlu authentication.
     */
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getMenu() {

        List<Map<String, Object>> menuList = new ArrayList<>();

        for (Map.Entry<String, BigDecimal> entry : MENU_ITEMS.entrySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", entry.getKey());
            item.put("price", entry.getValue());
            menuList.add(item);
        }

        return ResponseEntity.ok(menuList);
    }
}
