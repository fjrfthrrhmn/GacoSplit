# Controller — Pengatur Halaman Web

Folder ini berisi **Page Controller**, yaitu bagian dari aplikasi yang bertugas nentuin halaman HTML mana yang harus ditampilkan ketika user membuka URL tertentu.

## 📌 Tujuan

- **Routing halaman**: menghubungkan URL (misal `/` atau `/start`) ke file HTML yang sesuai
- **Return view name**: method di sini cuma ngembaliin nama file template, Thymeleaf yang nanti render jadi HTML
- Pisahin **routing** dari **logic bisnis** — controller cari tahu halaman mana yang diminta, bukan ngitung atau ngolah data

## 📂 Isi Folder

| File                  | Fungsi                                                                                                                                                                                                                                                            |
| --------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `PageController.java` | Controller yang handle semua halaman web aplikasi. Method `home()` untuk `/` (landing page), `start()` untuk `/start` (halaman utama kalkulator split bill). Pake anotasi `@Controller` biar Spring Boot tahu ini controller yang ngerender view, bukan REST API. |

## Tips

Method di PageController ditandai `@Controller` (bukan `@RestController`), karena tugasnya nampilin HTML, bukan balikin data JSON. Coba buka `http://localhost:8080/` dan `http://localhost:8080/start` — dua halaman itu diatur dari sini.

Penggunaan `@GetMapping("/...")` nentuin path URL-nya. Nama yang di-_return_ (misal `"index"`) adalah nama file template di folder `templates/`.

## 📎 Related

- `/resources/templates` — tempat file HTML yang dirender Thymeleaf
- `/gacosplit/controller` — buat REST API (balikin JSON), bukan halaman web
