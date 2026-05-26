# Templates — Halaman HTML Aplikasi

Folder ini berisi file **HTML template** yang di-render oleh Thymeleaf (template engine bawaan Spring Boot).
Setiap file di sini = satu halaman web yang bisa dibuka user.

## 📌 Tujuan

- Nyediain struktur HTML untuk setiap halaman aplikasi
- Pisahin **tampilan** (HTML/CSS) dari **logic** (Java/JavaScript)
- Semua resource (CSS, JS, gambar) di-link dari sini

## 📂 Isi Folder

| File         | Fungsi                                                                                                                                                                                                                 |
| ------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `index.html` | **Landing page** — halaman pertama yang dilihat user pas buka `http://localhost:8080/`. Isinya hero section, penjelasan cara kerja (3 langkah), about section, dan footer. Pake Tailwind CDN + `styles.css`.           |
| `start.html` | **Halaman utama aplikasi** — di sini user bikin sesi split bill, nambah orang, milih menu, liat hasil perhitungan. Ini SPA (Single Page Application): semua interaksi terjadi di satu halaman tanpa pindah-pindah URL. |

## Tips

- File `index.html` dan `start.html` di-load dari `PageController.java` — method `home()` buat `/` dan method `start()` buat `/start`
- Kedua halaman pake **Tailwind via CDN** (`<script src="https://cdn.tailwindcss.com"></script>`) — jadi gak perlu build CSS
- `start.html` pake banyak **inline style** di `<style>` untuk komponen yang di-generate JavaScript (kayak `.person-chip`, `.toast`)
- Kalo mau ganti teks atau layout, langsung edit di sini. Kalo mau ganti warna atau gaya, edit `styles.css`

## 📎 Related

- `/resources/static/css/styles.css` — styling yang dipake di halaman-halaman ini
- `/resources/static/js` — JavaScript yang ngerender dan ngatur interaksi di `start.html`
- `/example/demo/controller/PageController.java` — controller yang nentuin halaman mana yang ditampilkan
