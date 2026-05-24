# controller/

Folder ini berisi **Controller** — komponen yang menangani request HTTP dan menentukan response (halaman HTML atau data JSON).

## Fungsi

| File                   | Penjelasan                                                                                                        |
| ---------------------- | ----------------------------------------------------------------------------------------------------------------- |
| `PageController.java`  | Mengatur routing halaman web dengan `@Controller`. Setiap method me-return nama view (file HTML di `templates/`). |
| `HelloController.java` | Contoh REST API sederhana dengan `@RestController`. Me-return data teks, bukan halaman HTML.                      |

## Cara Kerja

1. User membuka URL (misal: `http://localhost:8080/tambah-teman`)
2. Spring Boot mencari method `@GetMapping("/tambah-teman")` di Controller
3. Method menjalankan logika (jika ada) dan me-return nama view
4. Thymeleaf mencari file HTML yang sesuai di `src/main/resources/templates/`
5. HTML dikirim ke browser user