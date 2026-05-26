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
}
