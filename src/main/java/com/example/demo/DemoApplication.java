package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
 * =========================================================================
 *  GacoSplitApplication — entry point
 * =========================================================================
 *
 *  @SpringBootApplication:
 *  - @Configuration: menjadikan class ini sumber definisi bean
 *  - @EnableAutoConfiguration: konfigurasi otomatis Spring Boot
 *  - @ComponentScan: scan komponen di package ini dan sub-package-nya
 *
 *  Karena entity dan repository ada di package "com.gacosplit" (bukan
 *  "com.example.demo"), kita perlu:
 *  1. scanBasePackages → Spring ComponentScan menjangkau com.gacosplit
 *  2. @EnableJpaRepositories → Spring Data JPA scan repository
 *  3. @EntityScan → Hibernate scan @Entity class
 *
 *  @EnableJpaRepositories(basePackages = "com.gacosplit.repository"):
 *  - Memberi tahu Spring Data JPA di mana mencari interface repository
 *  - Tanpa ini, repository tidak terdeteksi (log: "Found 0 JPA repository interfaces")
 *
 *  @EntityScan(basePackageClasses = { com.gacosplit.model.Session.class }):
 *  - Memberi tahu Hibernate di mana mencari @Entity class
 *  - @EntityScan di Spring Boot 4.x pindah ke spring-boot-persistence module
 *
 *  Kenapa perlu explicit? Karena main class di com.example.demo,
 *  sedangkan entity & repository di com.gacosplit.* (package berbeda).
 *  Spring Data JPA secara default cuma scan sub-package dari main class.
 * =========================================================================
 */

@SpringBootApplication(scanBasePackages = {
    "com.example.demo",
    "com.gacosplit"
})
@EnableJpaRepositories(basePackages = "com.gacosplit.repository")
@EntityScan(basePackageClasses = { com.gacosplit.model.Session.class })
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}