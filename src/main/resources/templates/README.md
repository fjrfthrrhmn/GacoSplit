# templates/

Folder ini berisi file **HTML template** yang dirender oleh Thymeleaf.

## Cara Kerja

- Saat Controller me-return `"index"`, Thymeleaf akan mencari `templates/index.html`
- File di sini bisa berisi template Thymeleaf (dengan atribut `th:*`) atau HTML biasa
- Untuk project ini, file HTML masih murni statis (pure HTML + Tailwind CDN)

## Daftar Halaman

| File                | Route            | Halaman                    |
|---------------------|------------------|----------------------------|
| `index.html`        | `/`              | Landing page               |
| `about.html`        | `/tentang`       | Tentang aplikasi           |
| `addfriends.html`   | `/tambah-teman`  | Tambah peserta             |
| `orderinput.html`   | `/order-input`   | Input pesanan              |
| `result.html`       | `/hasil`         | Hasil perhitungan          |
| `splithistory.html` | `/riwayat`       | Riwayat split bill         |

## Catatan

- File di `templates/` TIDAK bisa diakses langsung lewat URL (harus lewat Controller).
- File statis seperti CSS/JS tetap di `static/` dan bisa diakses langsung.
