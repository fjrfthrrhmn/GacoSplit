# static/

Folder ini berisi file **statis** yang bisa diakses langsung oleh browser tanpa melalui Controller.

## Contoh File Statis

- `css/` — file CSS
- Gambar, favicon, dll.

## Cara Akses

File di folder ini bisa diakses langsung lewat URL:

```
http://localhost:8080/css/style.css
http://localhost:8080/favicon.ico
```

## Catatan

- Folder ini saat ini kosong — semua file CSS Tailwind sebelumnya telah dihapus.
- File HTML sekarang berada di `templates/` dan dirender oleh Thymeleaf.
- Folder ini bisa diisi nanti jika ada aset statis yang diperlukan.