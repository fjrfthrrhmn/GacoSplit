# GacoSplit

## Ringkasan

GacoSplit adalah aplikasi berbasis web yang dirancang untuk menyederhanakan proses pembagian tagihan makan bersama, khususnya untuk kasus penggunaan di restoran Gacoan.

Dalam skenario makan bersama, biasanya satu orang membayar seluruh tagihan terlebih dahulu, yang kemudian memerlukan perhitungan manual untuk menentukan jumlah yang harus dibayar setiap peserta. Hal ini sering menimbulkan ketidakakuratan dan ketidaknyamanan sosial. GacoSplit menyelesaikan masalah ini dengan menyediakan sistem perhitungan yang terstruktur dan otomatis.

---

## Latar Belakang Masalah

Makan bersama sering menghadirkan beberapa tantangan:

- Perhitungan biaya secara manual memakan waktu dan rentan kesalahan
- Item yang dimakan bersama (shared) menimbulkan ketidakjelasan dalam pembagian biaya
- Terdapat potensi gesekan sosial saat meminta pembayaran dari peserta lain

---

## Solusi

GacoSplit menyediakan:

- Sistem input terstruktur untuk pesanan pribadi dan pesanan bersama
- Perhitungan otomatis jumlah yang harus dibayar setiap peserta
- Rincian transparan seluruh pengeluaran per peserta

---

## Fitur Utama

- Menambah dan mengelola peserta dalam sesi
- Input pesanan pribadi untuk setiap individu
- Dukungan untuk item bersama yang dibagi rata ke seluruh peserta
- Perhitungan otomatis total tagihan dan jumlah per orang
- Reset sesi dan ekspor hasil (salin ke clipboard)

---

## Arsitektur Sistem

Proyek ini mengikuti **arsitektur monolitik** menggunakan satu aplikasi Spring Boot yang melayani logika backend dan sumber daya frontend secara bersamaan.

### Backend

- Java dengan Spring Boot
- RESTful API untuk penanganan sesi dan perhitungan
- Basis data embedded

### Frontend

- HTML yang dirender dari server (Thymeleaf)
- Vanilla JavaScript untuk interaktivitas
- **TailwindCSS v3** untuk styling — build via PostCSS

---

## Struktur Proyek

```
gacosplit/
├── package.json              # Node dependencies
├── tailwind.config.js        # Tailwind v3 config (referensi docs/UI-UX.md)
├── postcss.config.js         # PostCSS plugin config
├── pom.xml                   # Maven build
├── src/
│   ├── main/
│   │   ├── java/com/gacosplit/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── model/
│   │   │   └── repository/
│   │   └── resources/
│   │       ├── templates/          # HTML Thymeleaf
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   ├── input.css   # Entry Tailwind + CSS variables
│   │       │   │   └── output.css  # Build output
│   │       │   └── js/
│   │       └── application.properties
│   └── test/
├── docs/                    # Dokumentasi (CAPITALIZE, flat)
│   ├── ARCHITECTURE.md
│   ├── FEATURES.md
│   ├── PRODUCT.md
│   ├── UI-UX.md
│   ├── UX-FLOW.md
│   └── UX-WRITING.md
├── DESIGN.md                # SSOT (Single Source of Truth)
├── AGENTS.md
├── CHANGELOG.md
└── README.md
```

---

## Logika Perhitungan Inti

Perhitungan terdiri dari dua komponen:

1. **Total Personal** — Jumlah seluruh item yang dipesan secara individu.
2. **Bagian Shared** — Total item bersama dibagi rata ke seluruh peserta.

Rumus akhir:

```
JumlahTagihan = TotalPersonal + (TotalShared / JumlahPeserta)
```

---

## Memulai

### Prasyarat

- Java 17+
- Maven
- Node.js 18+ (untuk build CSS)

### Instalasi

```bash
git clone https://github.com/fjrfthrrhmn/GacoSplit.git
cd GacoSplit
```

Install dependencies dan build CSS:

```bash
npm install
npm run build:css
```

Jalankan aplikasi:

```bash
./mvnw spring-boot:run
```

Akses aplikasi di `http://localhost:8080`

---

## Prinsip Desain

- Kesederhanaan di atas fleksibilitas
- Interaksi cepat dengan langkah minimal
- Penggunaan utama dari perangkat seluler (mobile-first)
- Logika perhitungan yang jelas dan transparan

---

## Keterbatasan

- Item bersama dibagi rata (tidak ada rasio kustom)
- Tidak ada autentikasi atau akun pengguna persisten
- Dirancang untuk penggunaan sesi tunggal (lingkup MVP)

---

## Pengembangan ke Depan

- Berbagi sesi melalui tautan atau kode QR
- Riwayat transaksi
- Input struk berbasis OCR
- Dukungan Progressive Web App (PWA)

---

## Dokumentasi

| Dokumen | Deskripsi |
|---|---|
| `DESIGN.md` | SSOT — Apple-inspired design system |
| `AGENTS.md` | Definisi peran agen dan keahlian pengembangan |
| `CHANGELOG.md` | Catatan perubahan dan iterasi pengembangan |
| `ROADMAP.md` | Milestone pengembangan |
| `docs/PRODUCT.md` | Gambaran produk, problem, solusi, tech stack |
| `docs/FEATURES.md` | Prioritas MoSCoW, acceptance criteria, roadmap fitur |
| `docs/ARCHITECTURE.md` | Data model, fungsionalitas, API endpoints |
| `docs/UX-FLOW.md` | Alur pengguna dari awal hingga hasil |
| `docs/UI-UX.md` | Token desain: warna, tipografi, shadow, spacing |
| `docs/UX-WRITING.md` | Panduan UX writing dan copy |

---

## Lisensi

Proyek ini dikembangkan untuk tujuan edukasi dan pembuatan prototipe.
