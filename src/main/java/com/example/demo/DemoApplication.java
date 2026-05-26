package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
 * =========================================================================
 *  DemoApplication — Entry point aplikasi (pintu masuk server)
 * =========================================================================
 *
 *  File ini adalah yang pertama dijalankan pas kita ketik:
 *  ./mvnw.cmd spring-boot:run
 *
 *  Method main() bakal nyalain server Spring Boot, yang otomatis:
 *  1. Nge-scan semua file @Controller, @Service, @Repository
 *  2. Nyambung ke database H2
 *  3. Siap nerima HTTP request di port 8080
 *
 *  Kenapa ada scanBasePackages + @EntityScan + @EnableJpaRepositories?
 *  Karena entry point di package "com.example.demo", sedangkan entity,
 *  repository, dan service kita ada di "com.gacosplit".
 *  Spring cuma scan package-nya sendiri secara default — jadi kita
 *  perlu kasih tahu secara manual.
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