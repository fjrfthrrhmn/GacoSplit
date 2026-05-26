# Demo — Entry Point Aplikasi

Folder ini berisi file utama (`DemoApplication.java`) yang jadi **pintu masuk** (entry point) seluruh aplikasi. 
Tanpa file ini, aplikasi Spring Boot kita gak bakal bisa jalan.

## 📌 Tujuan

- Sebagai **main class** yang menjalankan server Spring Boot
- Mengatur **package scanning** biar Spring bisa nemuin file-file `com.gacosplit.*` (entity, controller, repository, service)
- Konfigurasi **JPA repository** dan **entity scan** karena letaknya beda package dari kode utama

## 📎 Related

- `/gacosplit/repository` — isinya interface JPA yang harus ditemuin Spring
- `/gacosplit/model` — isinya entity class yang butuh di-scan Hibernate
- `/gacosplit/controller` — isinya REST controller yang dipanggil lewat HTTP
