<p align="center">
  <img src="https://img.shields.io/badge/Java-17%2B-ED8B00?logo=openjdk&logoColor=white" alt="Java 17+"/>
  <img src="https://img.shields.io/badge/Spring_Boot-4.0.6-6DB33F?logo=springboot&logoColor=white" alt="Spring Boot 4.0.6"/>
  <img src="https://img.shields.io/badge/TailwindCSS-v3-06B6D4?logo=tailwindcss&logoColor=white" alt="TailwindCSS v3"/>
  <img src="https://img.shields.io/badge/H2-Database-004088?logo=h2&logoColor=white" alt="H2 Database"/>
  <img src="https://img.shields.io/badge/license-MIT-blue" alt="License MIT"/>
  <img src="https://img.shields.io/github/last-commit/fjrfthrrhmn/GacoSplit" alt="Last Commit"/>
</p>

<h1 align="center">🍜 GacoSplit</h1>
<p align="center">
  <strong>Split bill Gacoan — gak pusing lagi urusan bayar-bayaran!</strong><br/>
  Aplikasi web buat ngitung tagihan makan bareng, auto include PPN 11%.
</p>

<p align="center">
  <a href="#-project-overview">Overview</a> •
  <a href="#-fitur">Fitur</a> •
  <a href="#-menu-gacoan">Menu</a> •
  <a href="#-arsitektur">Arsitektur</a> •
  <a href="#-memulai">Memulai</a> •
  <a href="#-teams">Teams</a>
</p>

---

## 📋 Project Overview

GacoSplit lahir dari masalah klasik: abis makan bareng, pada sibuk ngitung pake kalkulator HP, ujung-ujungnya ada yang ngerasa dibayarin kurang atau kebanyakan. Aplikasi ini nyelesain itu semua — tinggal masukin pesanan, beres.

### Cara Kerja

| Tahap | Kegiatan |
|-------|----------|
| **1** | Buat sesi dan masukin nama temen-temen yang ikut makan |
| **2** | Pilih menu Gacoan — ada yang pesen sendiri, ada yang dimakan bareng |
| **3** | Sistem otomatis hitung total per orang + PPN 11% |
| **4** | Tinggal salin hasilnya dan kirim ke grup WhatsApp |

### Fitur Utama

| Fitur | Deskripsi |
|-------|-----------|
| **👥 Kelola Peserta** | Tambah & hapus peserta (2–10 orang) |
| **🍝 Input Personal** | Masing-masing pesen menu sendiri-sendiri |
| **🤝 Menu Bersama** | Item bareng dibagi rata ke semua peserta |
| **🧮 Auto Calculate** | Total per orang + PPN langsung muncul |
| **📋 Salin Hasil** | Copy rincian tagihan ke clipboard |
| **🔄 Reset Sesi** | Mulai dari awal tanpa reload halaman |
| **💸 PPN 11%** | Pajak dihitung otomatis dari DPP per orang |

---

## 🧮 Logika Perhitungan

```
DPP = TotalPersonal + (TotalShared / JumlahPeserta)
PPN = DPP × 11% (dibulatkan ke rupiah penuh)
TotalTagihan = DPP + PPN
```

Setiap orang liat rinciannya: total pesanan sendiri + bagian menu bareng + PPN 11%. Hasil akhir tinggal di-copy ke WhatsApp.

---

## 🥟 Menu Gacoan

> Data menu bersumber dari [Tokopedia Blog](https://www.tokopedia.com/blog/menu-mie-gacoan-tvl/).

| Menu | Kategori | Harga |
|------|----------|-------|
| Mie Gacoan | Mie | Rp 10.000 |
| Mie Gacoan lvl 6 – 8 | Mie | Rp 10.900 |
| Mie Hompimpa | Mie | Rp 10.000 |
| Mie Hompimpa lvl 6 – 8 | Mie | Rp 10.900 |
| Mie Suit | Mie | Rp 10.000 |
| Udang Keju | Cemilan | Rp 9.100 |
| Udang Rambutan | Cemilan | Rp 9.100 |
| Lumpia Udang | Cemilan | Rp 9.100 |
| Siomay | Cemilan | Rp 9.100 |
| Pangsit Goreng | Cemilan | Rp 10.000 |
| Es Gobak Sodor | Es | Rp 9.100 |
| Es Teklek | Es | Rp 5.900 |
| Es Sluku Bathok | Es | Rp 5.900 |
| Es Petak Umpet | Es | Rp 9.100 |
| Air Mineral | Minuman | Rp 4.100 |
| Lemon Tea | Minuman | Rp 5.900 |
| Milo | Minuman | Rp 8.200 |
| Orange | Minuman | Rp 5.000 |
| Es Teh | Minuman | Rp 4.100 |
| Tea Tarik | Minuman | Rp 6.400 |
| Vanila Latte | Minuman | Rp 8.200 |
| Thai Tea | Minuman | Rp 8.200 |
| Thai Green Tea | Minuman | Rp 8.200 |
| Es Coklat | Minuman | Rp 8.200 |

---

## 🏗️ Arsitektur

Aplikasi ini **monolitik** — backend dan frontend jalan dalam satu server Spring Boot. User buka halaman di browser, JavaScript ngomong sama API backend, backend ngurus data dan perhitungan.

### Tech Stack

| Layer | Teknologi |
|-------|-----------|
| **Backend** | Java 17+, Spring Boot 4.0.6, Maven |
| **Frontend** | Thymeleaf, Vanilla JS, TailwindCSS v3 (CDN) |
| **Database** | H2 (file-based: `~/test`) |
| **ORM** | Spring Data JPA + Hibernate 7 |

### 📂 Isi Folder `src/com/`

| Folder | File | Fungsi |
|--------|------|--------|
| `example/demo/` | `DemoApplication.java` | Entry point aplikasi — yang pertama dijalankan pas server start |
| `example/demo/controller/` | `PageController.java` | Routing halaman web: URL `/` → `index.html`, `/start` → `start.html` |
| `gacosplit/config/` | `H2ConsoleConfig.java` | Mengaktifkan H2 Console biar bisa liat database lewat browser |
| `gacosplit/controller/` | `SessionController.java` | REST API untuk CRUD session, orang, item, dan kalkulasi |
| `gacosplit/controller/` | `MenuController.java` | REST API daftar menu Gacoan — 24 item lengkap dengan harga |
| `gacosplit/model/` | `Session.java`, `Person.java`, `Item.java` | Entity JPA — struktur tabel database dalam bentuk Java object |
| `gacosplit/model/dto/` | `SessionRequest.java`, `SessionResponse.java` | DTO — format data yang dikirim ke frontend (lebih ringan dari entity) |
| `gacosplit/repository/` | `SessionRepository.java` dkk | Jembatan ke database — panggil method, gak perlu nulis SQL |
| `gacosplit/service/` | `CalculationService.java` | Logic utama: hitung DPP, PPN 11%, total per orang |
| `gacosplit/service/` | `SessionService.java` | Logic bisnis: validasi, session management, CRUD |

### 🔑 Function Penting

| Function | Lokasi | Penjelasan |
|----------|--------|------------|
| `hitungTagihan()` | `app.js` | Otak perhitungan di frontend — ngitung total + PPN tiap orang |
| `renderSummary()` | `app.js` | Nampilin hasil perhitungan ke layar biar user bisa liat |
| `formatHasil()` | `app.js` | Ubah hasil jadi teks rapi buat di-copy ke WhatsApp |
| `calculate()` | `SessionController.java` | Endpoint API yang panggil `CalculationService` buat hitung tagihan |
| `findBySessionIdAndIsSharedTrue()` | `ItemRepository.java` | Query otomatis buat ambil semua item bersama dalam satu session |

---

## 🛠️ Memulai

### Prasyarat

- **Java 17+** — [Download JDK](https://adoptium.net/)
- **Maven** (opsional — pakai `mvnw.cmd` bawaan project)

### Instalasi

```bash
git clone https://github.com/fjrfthrrhmn/GacoSplit.git
cd GacoSplit
./mvnw.cmd spring-boot:run
```

Buka `http://localhost:8080` di browser dan klik **Mulai Hitung!**.

> Frontend pake TailwindCSS via CDN — gak perlu `npm install` atau build CSS.

---

## 👥 Teams

| Name | Role | GitHub | Instagram |
|------|------|--------|-----------|
| Fajar Fathurrahman | Frontend Developer & UI/UX | [@fjrfthrrhmn](https://github.com/fjrfthrrhmn) | [@fjrfthrrhmn](https://instagram.com/fjrfthrrhmn) |
| Fajar Fathurrahman | Backend Developer & Data Model | [@fjrfthrrhmn](https://github.com/fjrfthrrhmn) | [@fjrfthrrhmn](https://instagram.com/fjrfthrrhmn) |
| Shmmas | Backend Developer | [@shmmas](https://github.com/shmmas) | — |

---

## 📚 Dokumentasi

| Dokumen | Deskripsi |
|---------|-----------|
| [`DESIGN.md`](DESIGN.md) | SSOT — Apple-inspired design system (warna, font, layout) |
| [`AGENTS.md`](AGENTS.md) | Role definitions untuk AI agents dan tim pengembangan |
| [`CHANGELOG.md`](CHANGELOG.md) | Riwayat rilis dan catatan perubahan tiap versi |
| [`docs/PRODUCT.md`](docs/PRODUCT.md) | Gambaran produk, problem yang dipecahkan, dan solusi |
| [`docs/FEATURES.md`](docs/FEATURES.md) | Prioritas MoSCoW, acceptance criteria, roadmap fitur |
| [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md) | Data model, endpoint API, dan arsitektur sistem |
| [`docs/ROADMAP.md`](docs/ROADMAP.md) | Milestone pengembangan dan rencana ke depan |
| [`docs/UI-UX.md`](docs/UI-UX.md) | Token desain: palet warna, tipografi, shadow, spacing |
| [`docs/UX-FLOW.md`](docs/UX-FLOW.md) | Alur pengguna dari awal buka aplikasi sampai hasil |
| [`docs/UX-WRITING.md`](docs/UX-WRITING.md) | Panduan copywriting dan tone of voice aplikasi |

---

## ⚠️ Keterbatasan & Rencana

| Keterbatasan Saat Ini | Rencana ke Depan |
|-----------------------|------------------|
| Item bersama dibagi rata (belum ada rasio kustom) | Berbagi sesi via tautan / QR code |
| Belum ada autentikasi / akun pengguna | Riwayat transaksi antar sesi |
| Data disimpan di H2 lokal | Input struk via OCR |
| Lingkup MVP — satu sesi sekali pakai | Progressive Web App (PWA) & Dark mode |

---

<p align="center">
  Dibikin dengan ❤️ — biar gak ada lagi drama split bill di circle pertemanan 😭
</p>
