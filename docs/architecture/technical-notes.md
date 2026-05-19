> **Tujuan File:** Mendokumentasikan struktur navigasi halaman, struktur file proyek, arsitektur MPA (Multi-Page Application), daftar endpoint API REST, dukungan browser, dan panduan aksesibilitas.

# Technical Implementation Notes

## Arsitektur Aplikasi

GacoSplit menggunakan arsitektur **Multi-Page Application (MPA)** — setiap halaman adalah dokumen HTML terpisah yang dimuat dari server. Pendekatan ini dipilih karena:

- **Kesederhanaan** — Tidak memerlukan client-side router atau bundler
- **SEO-friendly** — Setiap halaman memiliki URL dan metadata sendiri
- **Performa** — Hanya memuat sumber daya yang dibutuhkan per halaman
- **Spring Boot native** — Cocok dengan model penyajian templat statis atau server-side rendering

### Struktur Halaman

| Halaman  | URL              | Deskripsi                                     |
| -------- | ---------------- | --------------------------------------------- |
| Home     | `/` atau `index.html` | Halaman utama: aplikasi split bill calculator |
| About    | `about.html`     | Informasi tentang aplikasi, cara pakai, tim   |

### Navigasi Global

Setiap halaman MPA memiliki navigasi bersama (shared navigation) yang konsisten:

```
[🍜 GacoSplit]  [Beranda]  [Tentang]
```

Navigasi dirender secara independen di setiap halaman HTML (tanpa template engine pada MVP). Untuk pengembangan selanjutnya, navigasi dapat diekstrak menjadi partial/fragment menggunakan Server-Side Includes (SSI) atau templat server-side.

---

## File Structure

Struktur monolith - semua dalam satu project Spring Boot dengan frontend sebagai static resources multi-halaman.

```
gacosplit/
├── src/main/java/com/example/gacosplit/
│   ├── GacosplitApplication.java              # Main entry point
│   ├── controller/
│   │   ├── PageController.java                # Route handler untuk halaman MPA
│   │   ├── SessionController.java             # REST API untuk session
│   │   └── MenuController.java                # API untuk menu template
│   ├── model/
│   │   ├── Session.java                       # Entity session
│   │   ├── Person.java                        # Entity person
│   │   ├── Item.java                          # Entity item
│   │   └── dto/
│   │       ├── SessionRequest.java           # Request DTOs
│   │       └── SessionResponse.java          # Response DTOs
│   ├── repository/
│   │   ├── SessionRepository.java            # JPA Repository
│   │   ├── PersonRepository.java
│   │   └── ItemRepository.java
│   └── service/
│       ├── SessionService.java                # Business logic
│       └── CalculationService.java            # Logic split bill
├── src/main/resources/
│   ├── application.properties                 # Konfigurasi database
│   └── static/
│       ├── index.html                         # Halaman Home — Split Bill Calculator
│       ├── about.html                         # Halaman About
│       ├── css/
│       │   ├── tailwind.css                   # Source Tailwind CSS
│       │   └── styles.css                     # Compiled CSS output
│       └── js/
│           ├── app.js                         # Main application logic (Home)
│           └── api.js                         # API client
├── src/test/java/                             # Unit tests
├── package.json                               # Tailwind CSS dependencies
├── tailwind.config.js                         # Tailwind color palette config
├── pom.xml                                    # Maven dependencies
└── README.md
```

### Catatan Struktur MPA

| Lokasi                          | Deskripsi                                       |
| ------------------------------- | ----------------------------------------------- |
| `controller/`                   | REST API endpoints + halaman route handler      |
| `static/index.html`             | Halaman Home — aplikasi split bill calculator   |
| `static/about.html`             | Halaman About — info aplikasi                   |
| `static/css/`                   | File CSS Tailwind (source + compiled)            |
| `static/js/`                    | JavaScript untuk fungsionalitas Home page        |

---

## API Endpoints

| Method | Endpoint                             | Deskripsi                     |
| ------ | ------------------------------------ | ----------------------------- |
| POST   | /api/sessions                        | Buat session baru             |
| GET    | /api/sessions/{id}                   | Ambil session by ID           |
| POST   | /api/sessions/{id}/people            | Tambah orang                  |
| DELETE | /api/sessions/{id}/people/{personId} | Hapus orang                   |
| POST   | /api/sessions/{id}/items             | Tambah item (personal/shared) |
| PUT    | /api/sessions/{id}/items/{itemId}    | Update item type              |
| DELETE | /api/sessions/{id}/items/{itemId}    | Hapus item                    |
| GET    | /api/sessions/{id}/calculate         | Hitung hasil per orang        |
| DELETE | /api/sessions/{id}/reset             | Reset session                 |

---

## Browser Support

- Chrome 90+
- Firefox 88+
- Safari 14+
- Edge 90+
- Mobile browsers (Chrome, Safari)

## Accessibility

- Semantic HTML (header, nav, main, section, footer)
- Navigasi keyboard dengan skip-to-content link
- ARIA labels untuk interactive elements
- Color contrast ratio minimal 4.5:1
- Focus visible states
- Navbar dengan `aria-current="page"` untuk halaman aktif
