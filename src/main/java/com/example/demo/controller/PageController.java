package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * PageController
 *
 * @Controller digunakan untuk kelas yang merender view (HTML),
 * berbeda dengan @RestController yang langsung mengembalikan data (JSON).
 */
@Controller
public class PageController {

    /**
     * Halaman utama (landing page).
     * URL: http://localhost:8080/
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }

    /**
     * Halaman kalkulator (aplikasi utama).
     * URL: http://localhost:8080/start
     */
    @GetMapping("/start")
    public String start() {
        return "start";
    }

    /**
     * Halaman tentang aplikasi.
     * URL: http://localhost:8080/tentang
     */
    @GetMapping("/tentang")
    public String about() {
        return "about"; 
    }

    /**
     * Halaman tambah teman.
     * Menambahkan peserta yang ikut split bill.
     * URL: http://localhost:8080/tambah-teman
     */
    @GetMapping("/tambah-teman")
    public String addFriends() {
        return "addfriends"; 
    }

    /**
     * Halaman input pesanan / order.
     * Memilih menu makanan untuk setiap peserta.
     * URL: http://localhost:8080/order-input
     */
    @GetMapping("/order-input")
    public String orderInput() {
        return "orderinput";
    }

    /**
     * Halaman hasil perhitungan split bill.
     * Menampilkan rincian tagihan per orang.
     * URL: http://localhost:8080/hasil
     */
    @GetMapping("/hasil")
    public String result() {
        return "result";
    }

    /**
     * Halaman riwayat split bill.
     * Menampilkan daftar sesi split bill sebelumnya.
     * URL: http://localhost:8080/riwayat
     */
    @GetMapping("/riwayat")
    public String history() {
        return "splithistory";
    }
}
