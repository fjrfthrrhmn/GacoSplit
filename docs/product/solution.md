> **Tujuan File:** Menjabarkan konsep solusi yang ditawarkan GacoSplit serta tech stack yang digunakan untuk membangun aplikasi.

# Solution Overview

## Konsep Solusi

GacoSplit memberikan pengalaman split bill yang:

- **Cepat** - Input menu, bagi tagihan dalam < 60 detik
- **Transparan** - Setiap orang bisa lihat apa yang dipesan & harus bayar
- **Netral** - Aplikasi jadi "penengah" agar gak awkward saat nagih
- **Jelas** - Kontribusi tiap orang untuk shared item diperjelas

## Tech Stack

- **Backend:** Java dengan Spring Boot
  - Framework: Spring Boot 3.x
  - Database: SQLite (untuk simplicity) atau H2 (embedded)
  - API: RESTful JSON API
- **Frontend:** Multi-Page Application (MPA) — HTML + CSS + Vanilla JavaScript
  - Arsitektur: Halaman terpisah (`index.html`, `about.html`) dengan navigasi bersama
  - Styling: TailwindCSS
  - Build: Standalone (no bundler diperlukan untuk MVP)
- **Hosting:** Single deployment (JAR + static files)
