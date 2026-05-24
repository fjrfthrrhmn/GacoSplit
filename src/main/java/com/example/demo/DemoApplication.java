package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DemoApplication — entry point aplikasi Spring Boot.
 *
 * @SpringBootApplication menandakan bahwa ini adalah aplikasi Spring Boot
 * dan mengaktifkan auto-configuration, component scanning, dan konfigurasi
 * tambahan secara otomatis.
 *
 * Semua routing halaman (endpoint GET) dipindahkan ke
 * PageController agar lebih rapi dan sesuai pola MVC.
 */
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
