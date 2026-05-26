# Project Overview — GacoSplit

**GacoSplit** adalah aplikasi web untuk menghitung pembagian tagihan makan bareng secara otomatis. Khusus dirancang buat menu **Mie Gacoan**, tapi logic-nya bisa dipake buat restoran lain juga.

## Masalah yang Diselesaikan

Abis makan bareng, biasanya ada satu orang yang bayar dulu — terus repot ngitung manual siapa harus bayar berapa. Apalagi kalo ada yang pesen menu sendiri (personal) dan ada juga yang dimakan bareng (shared). Belum lagi PPN 11%. Ujung-ujungnya ada temen yang ngerasa dibayarin kurang atau kebanyakan.

**GacoSplit nyelesain itu semua:** tinggal masukin nama, pilih menu, beres.

---

## 📌 Main Features

| Fitur                 | Deskripsi                                                                |
| --------------------- | ------------------------------------------------------------------------ |
| **Manajemen Peserta** | Tambah/hapus peserta dalam sesi (min. 2, maks. 10 orang)                 |
| **Pesanan Personal**  | Setiap peserta milih menu masing-masing dengan quantity                  |
| **Menu Bersama**      | Item yang dimakan bareng otomatis dibagi rata ke semua peserta           |
| **Hitung Otomatis**   | Total per orang + PPN 11% dihitung langsung tanpa klik tambahan          |
| **Copy ke Clipboard** | Hasil rincian bisa langsung di-copy ke WhatsApp                          |
| **PPN 11%**           | Pajak dihitung dari Dasar Pengenaan Pajak per orang (pembulatan HALF_UP) |
| **Reset Sesi**        | Mulai sesi baru tanpa perlu reload halaman                               |

---

## 🧱 Tech Stack

| Teknologi                         | Fungsi                                                                  |
| --------------------------------- | ----------------------------------------------------------------------- |
| **Java 17+**                      | Bahasa pemrograman utama untuk backend                                  |
| **Spring Boot 4.0.6**             | Framework backend — nyediain server, API, database, dan template engine |
| **Maven**                         | Build tool — ngatur dependency dan compile project                      |
| **H2 Database**                   | Database file-based — data disimpan di file lokal (`~/test`)            |
| **Spring Data JPA + Hibernate 7** | Layer database — tinggal panggil method, gak perlu nulis SQL            |
| **Thymeleaf**                     | Template engine — nge-render HTML dari server                           |
| **Vanilla JavaScript**            | Logic frontend — interaksi user, render halaman, panggil API            |
| **TailwindCSS v3 (CDN)**          | Utility CSS — styling cepat tanpa perlu build step                      |

---

## 📂 Folder Structure

### Backend — `src/main/java/com/`

| Folder                     | File                                          | Fungsi                                                        |
| -------------------------- | --------------------------------------------- | ------------------------------------------------------------- |
| `example/demo/`            | `DemoApplication.java`                        | Entry point — yang pertama dijalankan pas server start        |
| `example/demo/controller/` | `PageController.java`                         | Routing halaman web: `/` → landing, `/start` → aplikasi utama |
| `gacosplit/config/`        | `H2ConsoleConfig.java`                        | Enable H2 Console buat debugging database via browser         |
| `gacosplit/controller/`    | `SessionController.java`                      | REST API untuk CRUD session, orang, dan item                  |
| `gacosplit/controller/`    | `MenuController.java`                         | REST API daftar menu Gacoan (24 item)                         |
| `gacosplit/model/`         | `Session.java`, `Person.java`, `Item.java`    | Entity JPA — struktur tabel database dalam bentuk Java object |
| `gacosplit/model/dto/`     | `SessionRequest.java`, `SessionResponse.java` | DTO — format data API (lebih ringan dari entity)              |
| `gacosplit/repository/`    | `SessionRepository.java` dkk                  | Jembatan ke database — method CRUD otomatis                   |
| `gacosplit/service/`       | `CalculationService.java`                     | Logic hitung tagihan: DPP, PPN 11%, total per orang           |
| `gacosplit/service/`       | `SessionService.java`                         | Logic bisnis: validasi, CRUD session, koordinasi data         |

### Frontend — `src/main/resources/`

| Folder        | File         | Fungsi                                                             |
| ------------- | ------------ | ------------------------------------------------------------------ |
| `static/css/` | `styles.css` | Design tokens (warna, font) + komponen reusable (btn, card, badge) |
| `static/js/`  | `api.js`     | HTTP client — semua panggilan ke REST API backend                  |
| `static/js/`  | `app.js`     | Logic utama frontend — state management, render, kalkulasi         |
| `static/js/`  | `github.js`  | Utility buat nampilin kartu developer dari GitHub                  |
| `templates/`  | `index.html` | Landing page — hero, cara kerja, about section                     |
| `templates/`  | `start.html` | Halaman utama aplikasi — SPA split bill                            |

---

## 🔄 Application Flow

```
User buka browser → http://localhost:8080
         │
         ▼
    index.html (Landing page)
    - Lihat penjelasan cara kerja
    - Klik "Mulai Hitung!"
         │
         ▼
    start.html (Aplikasi utama — SPA)
    - Klik "Buat Sesi Baru"
    - Masukin nama-nama peserta
         │
         ▼
    Pilih menu buat setiap orang
    - Pilih menu dari dropdown
    - Tentukan quantity
    - Tambah ke daftar pesanan
         │
         ▼
    Tambah menu bersama (opsional)
    - Item yang dimakan bareng
    - Otomatis dibagi rata
         │
         ▼
    Klik "Hitung Tagihan"
    - Frontend (app.js) kalkulasi sementara
    - Backend (CalculationService) kalkulasi final
         │
         ▼
    Lihat rincian per orang (renderSummary)
    - Personal total, shared portion, PPN 11%, grand total
    - Bisa copy ke clipboard atau reset sesi
```

---

## 🧠 Important Components

### Backend (Java)

| Component           | File                      | Fungsi Utama                                                                      |
| ------------------- | ------------------------- | --------------------------------------------------------------------------------- |
| **Calculator**      | `CalculationService.java` | Otak perhitungan: misahin personal/shared items, hitung DPP, PPN 11%, grand total |
| **Session Manager** | `SessionService.java`     | Validasi dan koordinasi data: buat session, tambah/hapus orang & item             |
| **Session API**     | `SessionController.java`  | 9 endpoint REST: CRUD session, people, items, calculate                           |
| **Menu API**        | `MenuController.java`     | 1 endpoint: `GET /api/menu` — 24 menu Gacoan                                      |
| **Entities**        | `model/*.java`            | 3 tabel database: sessions, persons, items                                        |

### Frontend (JavaScript)

| Component         | File                              | Fungsi Utama                                                    |
| ----------------- | --------------------------------- | --------------------------------------------------------------- |
| **State Manager** | `app.js` — `state` object         | Nyimpen data sementara: session, people, items, hasil kalkulasi |
| **Calculator**    | `app.js` — `hitungTagihan()`      | Hitung total personal, shared, PPN, grand total di frontend     |
| **Renderer**      | `app.js` — `renderSummary()`      | Nampilin hasil perhitungan ke layar                             |
| **HTTP Client**   | `api.js` — `api` object           | 9 fungsi fetch ke REST API backend                              |
| **Team Cards**    | `github.js` — `renderTeamCards()` | Ambil data GitHub dan render kartu tim                          |

### Rumus Perhitungan

```
DPP = TotalPersonal + (TotalShared / JumlahPeserta)
PPN = DPP × 11% (dibulatkan ke rupiah penuh — HALF_UP)
TotalTagihan = DPP + PPN
```

- **DPP** = Dasar Pengenaan Pajak (total pesanan sendiri + bagian menu bareng)
- **PPN** = Pajak Pertambahan Nilai 11% (pembulatan standar akuntansi Indonesia)
- Hasil ditampilkan per orang dan bisa langsung di-copy

---

## 🧭 Cara Memulai Development

```bash
# Clone project
git clone https://github.com/fjrfthrrhmn/GacoSplit.git
cd GacoSplit

# Jalankan server (Maven Wrapper — tanpa perlu install Maven)
./mvnw.cmd spring-boot:run

# Buka di browser
# http://localhost:8080
```

Frontend pake TailwindCSS via CDN — **gak perlu npm install atau build CSS**.

---

## 📚 Dokumen Terkait

| Dokumen                | Isi                                                |
| ---------------------- | -------------------------------------------------- |
| `README.md`            | Informasi utama project, cara install, fitur, menu |
| `DESIGN.md`            | Design system Apple-inspired (SSOT)                |
| `docs/ARCHITECTURE.md` | Detail arsitektur, data model, endpoint API        |
| `docs/FEATURES.md`     | Prioritas MoSCoW dan acceptance criteria           |
| `docs/PRODUCT.md`      | Gambaran produk, problem, dan solusi               |
| `docs/ROADMAP.md`      | Milestone dan rencana pengembangan                 |
| `docs/UI-UX.md`        | Token desain: warna, tipografi, shadow             |
| `docs/UX-FLOW.md`      | Alur pengguna dari awal sampai hasil               |
| `docs/UX-WRITING.md`   | Panduan copy dan tone of voice                     |
