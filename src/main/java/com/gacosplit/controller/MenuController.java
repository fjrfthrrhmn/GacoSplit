package com.gacosplit.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    /*
     * Data menu Gacoan dari README.md.
     * Sumber: https://www.tokopedia.com/blog/menu-mie-gacoan-tvl/
     * Sync dengan data di frontend (src/main/resources/static/js/app.js).
     */
    private static final Map<String, BigDecimal> MENU_ITEMS = new LinkedHashMap<>();

    static {
        /* Mie */
        MENU_ITEMS.put("Mie Gacoan", new BigDecimal("10000"));
        MENU_ITEMS.put("Mie Gacoan lvl 6 - 8", new BigDecimal("10900"));
        MENU_ITEMS.put("Mie Hompimpa", new BigDecimal("10000"));
        MENU_ITEMS.put("Mie Hompimpa lvl 6 - 8", new BigDecimal("10900"));
        MENU_ITEMS.put("Mie Suit", new BigDecimal("10000"));

        /* Cemilan */
        MENU_ITEMS.put("Udang Keju", new BigDecimal("9100"));
        MENU_ITEMS.put("Udang Rambutan", new BigDecimal("9100"));
        MENU_ITEMS.put("Lumpia Udang", new BigDecimal("9100"));
        MENU_ITEMS.put("Siomay", new BigDecimal("9100"));
        MENU_ITEMS.put("Pangsit Goreng", new BigDecimal("10000"));

        /* Es */
        MENU_ITEMS.put("Es Gobak Sodor", new BigDecimal("9100"));
        MENU_ITEMS.put("Es Teklek", new BigDecimal("5900"));
        MENU_ITEMS.put("Es Sluku Bathok", new BigDecimal("5900"));
        MENU_ITEMS.put("Es Petak Umpet", new BigDecimal("9100"));

        /* Minuman */
        MENU_ITEMS.put("Air Mineral", new BigDecimal("4100"));
        MENU_ITEMS.put("Lemon Tea", new BigDecimal("5900"));
        MENU_ITEMS.put("Milo", new BigDecimal("8200"));
        MENU_ITEMS.put("Orange", new BigDecimal("5000"));
        MENU_ITEMS.put("Es Teh", new BigDecimal("4100"));
        MENU_ITEMS.put("Tea Tarik", new BigDecimal("6400"));
        MENU_ITEMS.put("Vanila Latte", new BigDecimal("8200"));
        MENU_ITEMS.put("Thai Tea", new BigDecimal("8200"));
        MENU_ITEMS.put("Thai Green Tea", new BigDecimal("8200"));
        MENU_ITEMS.put("Es Coklat", new BigDecimal("8200"));
    }

    // ==================================================================
    //  1. GET /api/menu — Ambil daftar menu
    // ==================================================================
    /*
     * Mengirim daftar menu Gacoan ke frontend (app.js).
     * Data di-loop dari MENU_ITEMS dan diubah formatnya jadi array JSON.
     * Endpoint ini cuma baca (read-only) — gak ada yang berubah di server.
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
