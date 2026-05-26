package com.gacosplit.config;

import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * =========================================================================
 *  H2ConsoleConfig — Mengaktifkan H2 Database Console
 * =========================================================================
 *
 *  H2 Console adalah web interface bawaan database H2.
 *  Berguna buat: ngecek isi tabel, nyoba query, debugging data.
 *
 *  Cara akses: http://localhost:8080/h2-console
 *  JDBC URL:   jdbc:h2:file:~/test (lihat log pas aplikasi jalan)
 *  Username:   sa
 *  Password:   (kosongkan)
 *
 *  Konfigurasi ini cuma aktif di mode development.
 *  Untuk production, H2 Console harus dimatikan.
 * =========================================================================
 */

@Configuration
public class H2ConsoleConfig {

    /*
     * Mendaftarkan H2 Console sebagai servlet di path /h2-console.
     * Load on startup = 1: servlet langsung siap pas aplikasi jalan.
     */
    @Bean
    public ServletRegistrationBean<JakartaWebServlet> h2ConsoleServlet() {
        JakartaWebServlet servlet = new JakartaWebServlet();
        ServletRegistrationBean<JakartaWebServlet> bean =
                new ServletRegistrationBean<>(servlet, "/h2-console/*");
        bean.setLoadOnStartup(1);
        return bean;
    }
}
