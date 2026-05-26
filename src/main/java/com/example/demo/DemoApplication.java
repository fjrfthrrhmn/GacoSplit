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