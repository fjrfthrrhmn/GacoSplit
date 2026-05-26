# Config — Konfigurasi Tambahan Aplikasi

Folder ini berisi file konfigurasi khusus yang gak bisa diatur lewat `application.properties` doang. Biasanya buat enable fitur tertentu atau setting bean Spring Boot.

## 📌 Tujuan

- Ngatur **H2 Console** (database web interface) biar bisa diakses pas mode development

## 📂 Isi Folder

| File                   | Fungsi                                                                                                                                                                                 |
| ---------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `H2ConsoleConfig.java` | Mengaktifkan H2 Database Console (`/h2-console`) biar kita bisa liat isi database langsung dari browser. Berguna pas development buat cek data tanpa perlu aplikasi database terpisah. |

## Tips

H2 Console cuma aktif di mode development / non-production. URL-nya: `http://localhost:8080/h2-console`. JDBC URL bisa diliat di log pas aplikasi jalan.

## 📎 Related

- `application.properties` — tempat ngatur koneksi database, port, dan setting lain
- `/gacosplit/repository` — koneksi ke database via JPA
