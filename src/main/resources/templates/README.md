# templates/

Folder ini berisi file **HTML template** yang dirender oleh Thymeleaf.

## Cara Kerja

1. `PageController.java` me-return nama view, misalnya `"index"`
2. Thymeleaf mencari file yang sesuai di folder ini: `templates/index.html`
3. HTML dikirim ke browser user

## Daftar Halaman

| File                | Route            | Method Controller         | Halaman                    |
|---------------------|------------------|---------------------------|----------------------------|
| `index.html`        | `/`              | `home()`                  | Landing page               |
| `about.html`        | `/tentang`       | `about()`                 | Tentang aplikasi           |
| `addfriends.html`   | `/tambah-teman`  | `addFriends()`            | Tambah peserta             |
| `orderinput.html`   | `/order-input`   | `orderInput()`            | Input pesanan              |
| `result.html`       | `/hasil`         | `result()`                | Hasil perhitungan          |
| `splithistory.html` | `/riwayat`       | `history()`               | Riwayat split bill         |

## Catatan

- File di `templates/` TIDAK bisa diakses langsung lewat URL (harus lewat Controller).
- Semua file HTML di sini adalah plain HTML (tanpa framework CSS) agar fokus belajar Spring Boot MVC.
