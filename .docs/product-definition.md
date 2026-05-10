# GacoSplit - Product Definition Document

## 1. Product Overview

**Nama Produk:** GacoSplit

**Deskripsi Singkat:**
GacoSplit adalah aplikasi web sederhana untuk membagi tagihan pembayaran di restoran Gacoan.
Dirancang untuk grup yang makan bersama dan perlu menghitung siapa harus bayar berapa secara cepat dan adil.

**Target Pengguna:**

- Kelompok mahasiswa/pekerja yang sering makan bersama di Gacoan
- Pengguna yang ingin kemudahan split bill tanpa aplikasi perbankan
- Usia: 18-35 tahun, familiar dengan smartphone

---

## 2. Problem Breakdown

### 2.1 Pain Points Saat Ini

| Masalah                                | Dampak                                          |
| -------------------------------------- | ----------------------------------------------- |
| Satu orang bayar duluan                | Yang lain lupa/pengen pura-pura lupa            |
| Hitung manual                          | Sering salah hitung, waktu lama                 |
| Malu nagih teman                       | "Eh btw... kamu harusnya Rp15.000"              |
| Gacoan punya banyak varian level pedas | Perlu tracking per orang karena preferensi beda |
| Item dimakan bareng (shared)           | Bingung bagaimana bagi tagihannya               |

### 2.2 Root Cause

- **Proses manual** - Tidak ada alat bantu yang fokus untuk use case ini
- **Social pressure** - Tidak ada sistem netral untuk penagihan
- **Kompleksitas menu** - Harga varian Mi Gacoan bervariasi
- **Shared item ambiguity** - Tidak ada kejelasan kontribusi tiap orang

---

## 3. Solution Overview

### 3.1 Konsep Solusi

GacoSplit memberikan pengalaman split bill yang:

- **Cepat** - Input menu, bagi tagihan dalam < 60 detik
- **Transparan** - Setiap orang bisa lihat apa yang dipesan & harus bayar
- **Netral** - Aplikasi jadi "penengah" agar gak awkward saat nagih
- **Jelas** - Kontribusi tiap orang untuk shared item diperjelas

### 3.2 Design Principles

1. **Minimalis** - Tampilan fokus hanya untuk task ini, tidak banyak distraksi
2. **Mobile-first** - Dipakai di HP saat di restoran, desain touchscreen-friendly
3. **Responsif** - Desain adaptif untuk berbagai ukuran layar
4. **No login required** - Langsung pakai, tidak perlu buat akun
5. **Simple shared model** - Shared item dibagi rata, tidak ada flexibel split

### 3.3 Tech Stack

- **Backend:** Java dengan Spring Boot
  - Framework: Spring Boot 3.x
  - Database: SQLite (untuk simplicity) atau H2 (embedded)
  - API: RESTful JSON API
- **Frontend:** HTML + CSS + Vanilla JavaScript
  - Styling: TailwindCSS
  - Build: Standalone (no bundler required untuk MVP)
- **Hosting:** Single deployment (JAR + static files)

---

## 4. Core Features by MoSCoW

### 4.1 Must Have (Wajib)

| Fitur                     | Deskripsi                                     | Priority |
| ------------------------- | --------------------------------------------- | -------- |
| Input pesanan per orang   | Tambah item menu + harga untuk setiap orang   | P0       |
| Shared item (dibagi rata) | Item dimakan bareng, bagi rata ke semua orang | P0       |
| Tampilkan total tagihan   | Hitung total semua pesanan                    | P0       |
| Tampilkan per orang       | Berapa yang harus dibayar masing-masing       | P0       |
| Reset/clear session       | Mulai ulang kalkulasi                         | P0       |

### 4.2 Should Have (Sangat Direkomendasikan)

| Fitur                      | Deskripsi                             | Priority |
| -------------------------- | ------------------------------------- | -------- |
| Copy hasil                 | Copy ringkasan tagihan ke clipboard   | P1       |
| Tampilan ringkas per orang | Detail apa yang dipesen masing-masing | P1       |
| Pre-loaded menu Gacoan     | Template menu populer Gacoan          | P1       |

### 4.3 Could Have (NICE to Have)

| Fitur             | Deskripsi                                 | Priority |
| ----------------- | ----------------------------------------- | -------- |
| QR share session  | Bagikan link ke teman tanpa harus satu HP | P2       |
| History transaksi | Simpan riwayat split bill sebelumnya      | P2       |

### 4.4 Won't Have (Tidak di Scope)

- Integrasi payment gateway (GoPay, OVO, dll)
- OCR (Optical Character Recognition) struk
- Custom split ratio untuk shared item (misal: orang A 70%, orang B 30%)
- Split berdasarkan porsi yang tidak rata

---

## 5. User Flow

### 5.1 Happy Path

```
[Mulai] --> [Tambah Orang] --> [Input Pesanan Personal] --> [Input Shared Item] --> [Cek Total] --> [Lihat Hasil]
```

### 5.2 Step-by-Step Flow

#### Step 1: Buat Session Baru

- User membuka aplikasi
- Klik "Mulai Session Baru"
- Opsional: Masukkan nama grup (misal "Makan bareng")

#### Step 2: Tambah Orang

- Klik "Tambah Orang"
- Masukkan nama (misal: "Fajar", "Rudi", dll)
- Minimum 2 orang

#### Step 3: Input Pesanan Personal

- Pilih orang dari dropdown
- Pilih menu dari template atau masukkan manual
- Tentukan quantity
- Klik "Tambah sebagai Personal"

#### Step 4: Input Shared Item (Opsional)

- Klik "Tambah Shared Item"
- Pilih menu
- Tentukan quantity
- Shared item akan otomatis dibagi rata ke semua orang di session
- Klik "Tambah sebagai Shared"

#### Step 5: Review & Edit

- Lihat list semua pesanan (personal & shared terpisah)
- Edit/hapus jika ada kesalahan
- Lihat total per orang (personal + bagian shared)

#### Step 6: Hitung & Share

- Sistem otomatis menghitung:
  - Total personal per orang
  - Total shared item (dibagi rata)
  - Total yang harus dibayar masing-masing
- User bisa copy hasil atau screenshot

### 5.3 User Flow Diagram

```
                    ┌─────────────────┐
                    │  Buka Aplikasi  │
                    └────────┬────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │ Buat Session    │
                    │ Baru            │
                    └────────┬────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │ Tambah Orang    │◄─────────────────┐
                    │ (min. 2 orang)  │                  │
                    └────────┬────────┘                  │
                             │                           │
                             ▼                           │
              ┌──────────────────────────────────────┐   │
              │         Input Pesanan                │   │
              └──────────────┬───────────────────────┘   │
                             │                           │
              ┌──────────────┴──────────────┐            │
              ▼                              ▼            │
     ┌─────────────────┐            ┌─────────────────┐   │
     │  Pesanan        │            │  Shared Item    │   │
     │  Personal       │            │  (Opsional)     │   │
     │  (Wajib)        │            │  Dibagi rata     │   │
     └────────┬────────┘            └────────┬────────┘   │
              │                              │             │
              └──────────────┬───────────────┘             │
                             │                             │
                             ▼                             │
                    ┌─────────────────┐                    │
                    │ Review Total    │                    │
                    │ Tagihan         │                    │
                    └────────┬────────┘                    │
                             │                             │
                             ▼                             │
                    ┌─────────────────┐                    │
                    │ Hitung Hasil    │                    │
                    │ Per Orang       │                    │
                    │ (Personal +     │                    │
                    │  Shared ÷ n)   │                    │
                    └────────┬────────┘                    │
                             │                             │
                             ▼                             │
                    ┌─────────────────┐                    │
                    │ Copy/Share      │────────────────────┘
                    │ Hasil           │
                    └─────────────────┘
```

---

## 6. Data Model

### 6.1 Entity Definitions

```java
// Session
public class Session {
    String id;
    String name;
    LocalDateTime createdAt;
    List<Person> people;
    BigDecimal totalAmount;
    BigDecimal sharedAmount;
}

// Person
public class Person {
    String id;
    String name;
    List<Item> personalItems;  // item yang dipesan sendiri
    BigDecimal personalTotal; // subtotal item personal
    BigDecimal sharedPortion;  // bagian dari shared item (dihitung otomatis)
    BigDecimal amountOwed;     // personalTotal + sharedPortion
}

// Item
public class Item {
    String id;
    String name;
    BigDecimal price;
    Integer quantity;
    Boolean isShared;          // true = shared item, false = personal
    String orderedBy;          // person-id (null jika isShared = true)
}
```

### 6.2 Menu Gacoan Template

| Menu               | Harga     |
| ------------------ | --------- |
| Mie Gacoan Level 1 | Rp 16.000 |
| Mie Gacoan Level 2 | Rp 17.000 |
| Mie Gacoan Level 3 | Rp 18.000 |
| Mie Gacoan Level 4 | Rp 19.000 |
| Mie Gacoan Level 5 | Rp 20.000 |
| Mie Katsu          | Rp 20.000 |
| Mie Pangsit        | Rp 20.000 |
| Dimsum             | Rp 15.000 |
| Es Teh Manis       | Rp 5.000  |
| Es Jeruk           | Rp 6.000  |
| Tahu Crispy        | Rp 8.000  |

---

## 7. UI/UX Specification

### 7.1 Layout Structure

```
┌──────────────────────────────────────┐
│  🍜 GacoSplit              [Baru]    │  Header
├──────────────────────────────────────┤
│  [Nama 1] [Nama 2] [Nama 3]          │  Tabs orang
├──────────────────────────────────────┤
│  ┌────────────────────────────────┐  │
│  │ + Tambah Pesanan Personal      │  │  Form input
│  │  [Pilih Orang ▼]               │  │
│  │  [Pilih Menu ▼] [Qty: 1]       │  │
│  │  [Tambah Personal]             │  │
│  └────────────────────────────────┘  │
│                                      │
│  ┌────────────────────────────────┐  │
│  │ + Tambah Shared Item           │  │
│  │  [Pilih Menu ▼] [Qty: 1]       │  │
│  │  (Akan dibagi rata ke n orang) │  │
│  │  [Tambah Shared]               │  │
│  └────────────────────────────────┘  │
│                                      │
│  ── Personal ────────────────────    │
│  📋 Fajar                            │
│    - Mi Gacoan L3 (1x) = 18.000      │
│    - Es Teh Manis (1x) = 5.000       │
│    Subtotal: 23.000                  │
│                                      │
│  ── Shared ──────────────────────    │
│  📋 Tahu Crispy (2x) = 16.000        │
│     Dibagi 3 orang = 5.333/orang     │
│                                      │
│  ── Summary ─────────────────────    │
│  Fajar: 23.000 + 5.333 = 28.333      │
│  Rudi: 20.000 + 5.333 = 25.333       │
│  Toni: 15.000 + 5.333 = 20.333       │
│                                      │
│  Total: 74.000                       │
│  [Salin] [Mulai Baru]                │
└──────────────────────────────────────┘
```

### 7.2 Responsive Breakpoints

| Breakpoint | Width          | Layout                            |
| ---------- | -------------- | --------------------------------- |
| Mobile     | < 640px        | Single column, full-width buttons |
| Tablet     | 640px - 1024px | Side-by-side input dan list       |
| Desktop    | > 1024px       | Max-width 800px centered          |

### 7.3 TailwindCSS Configuration

| Utility Class   | Usage                              |
| --------------- | ---------------------------------- |
| `bg-red-600`    | Primary button (warna khas Gacoan) |
| `bg-amber-50`   | Card background (creamy)           |
| `bg-blue-50`    | Shared item card (dibedakan biru)  |
| `text-gray-800` | Primary text                       |
| `text-gray-500` | Secondary text                     |
| `rounded-lg`    | Card dan button corners            |

---

## 8. Functionality Specification

### 8.1 Core Features Detail

#### F1: Tambah Session

- Generate UUID untuk session
- Simpan timestamp creation
- Initialize empty lists untuk people dan items
- Return session ID untuk tracking

#### F2: Tambah/Hapus Orang

- Minimum: 2 orang
- Maksimum: 10 orang
- Prevent duplicate nama
- Hapus orang akan:
  - Hapus semua item personal orang tersebut
  - Redistribusi ulang bagian shared item ke orang yang tersisa

#### F3: Input Pesanan Personal

- Pilih orang dari dropdown
- Pilih menu dari template Gacoan
- Quantity: 1-99
- Harga auto-fill dari template
- Support custom item dengan harga manual
- Item ditandai sebagai personal (isShared = false)

#### F4: Input Shared Item

- Pilih menu dari template
- Tentukan quantity
- Item ditandai sebagai shared (isShared = true)
- Shared item TIDAK punya orderedBy
- Shared item akan otomatis dibagi rata ke semua orang di session
- User TIDAK bisa pilih siapa saja yang mau bagi shared item (selalu semua orang)

#### F5: Calculation Logic

**Personal Items:**

```
PersonalTotal(orang) = Σ(item.price × item.quantity) untuk semua item personal orang tersebut
```

**Shared Items:**

```
SharedTotal = Σ(item.price × item.quantity) untuk semua item isShared = true
SharedPortionPerPerson = SharedTotal ÷ TotalOrang
```

**Final Amount per Person:**

```
AmountOwed(orang) = PersonalTotal(orang) + SharedPortionPerPerson
```

**Contoh Perhitungan:**

| Orang     | Personal  | Shared Portion | Total         |
| --------- | --------- | -------------- | ------------- |
| Fajar     | Rp 23.000 | Rp 6.000       | Rp 29.000     |
| Rudi      | Rp 20.000 | Rp 6.000       | Rp 26.000     |
| Toni      | Rp 15.000 | Rp 6.000       | Rp 21.000     |
| **Total** | Rp 58.000 | Rp 18.000      | **Rp 76.000** |

#### F6: Copy Hasil ke Clipboard

```
📋 Rincian Tagihan Gacoan
━━━━━━━━━━━━━━━━━━━━━━
Fajar: Rp 29.000
  - Mi Gacoan L3 (1x) = 18.000
  - Es Teh Manis (1x) = 5.000
  - Shared (Tahu Crispy) = 6.000
━━━━━━━━━━━━━━━━━━━━━━
Rudi: Rp 26.000
  - Mie Gacoan L1 (1x) = 20.000
  - Shared (Tahu Crispy) = 6.000
━━━━━━━━━━━━━━━━━━━━━━
Toni: Rp 21.000
  - Dimsum (1x) = 15.000
  - Shared (Tahu Crispy) = 6.000
━━━━━━━━━━━━━━━━━━━━━━
Total: Rp 76.000
```

### 8.2 State Management

**Frontend State (JavaScript):**

```javascript
const initialState = {
  session: null,
  people: [],
  personalItems: [], // item dengan isShared = false
  sharedItems: [], // item dengan isShared = true
  activePerson: null,
  calculationResult: {
    grandTotal: 0,
    sharedTotal: 0,
    sharedPerPerson: 0,
    perPersonAmounts: [],
  },
  error: null,
};
```

**Backend State (Spring Boot):**

- Session dan data disimpan di database
- API call untuk sync state antara frontend dan backend

### 8.3 Validation Rules

| Field        | Rule                                        |
| ------------ | ------------------------------------------- |
| Nama orang   | Required, min 2 chars, max 30 chars         |
| Nama item    | Required jika custom item                   |
| Harga        | Required, numeric, min 0, max 999.999       |
| Quantity     | Required, integer, min 1, max 99            |
| Jumlah orang | Min 2, max 10                               |
| Shared item  | Hanya bisa ditambahkan jika minimal 2 orang |

### 8.4 Error Handling

| Error Case                    | Handling                               |
| ----------------------------- | -------------------------------------- |
| Nama kosong                   | Tampilkan inline error, disable submit |
| Shared item tanpa orang       | Disable tombol shared item             |
| API call gagal                | Show toast notification, retry option  |
| Minimum orang tidak terpenuhi | Disable tombol hitung                  |

---

## 9. Acceptance Criteria

### 9.1 Functional Acceptance

| ID    | Criteria                         | Test Scenario                                                      |
| ----- | -------------------------------- | ------------------------------------------------------------------ |
| AC-01 | User bisa menambah orang         | Input nama "Fajar", klik tambah, nama muncul di list               |
| AC-02 | User bisa input pesanan personal | Pilih orang, pilih menu, quantity 2, item muncul di list personal  |
| AC-03 | User bisa input shared item      | Tambah Tahu Crispy, muncul di list shared dengan notasi "dibagi 3" |
| AC-04 | Shared item dibagi rata          | Shared Rp 18.000, 3 orang = Rp 6.000/orang                         |
| AC-05 | Total tagihan akurat             | Personal 58.000 + Shared 18.000 = Total 76.000                     |
| AC-06 | Per orang benar                  | Fajar: 23.000 + 6.000 = 29.000                                     |
| AC-07 | Copy hasil berfungsi             | Klik copy, paste di chat, semua data ter-copy                      |
| AC-08 | Reset session berfungsi          | Klik baru, semua data clear                                        |
| AC-09 | Hapus orang update shared        | Hapus 1 orang, shared item otomatis bagi ulang ke 2 orang          |

### 9.2 Non-Functional Acceptance

| ID    | Criteria          | Target                         |
| ----- | ----------------- | ------------------------------ |
| NF-01 | API Response Time | < 500ms untuk setiap request   |
| NF-02 | Page Load Time    | < 3 detik                      |
| NF-03 | Mobile usability  | Semua fitur bisa dipakai di HP |

---

## 10. Technical Implementation Notes

### 10.1 File Structure

Struktur monolith - semua dalam satu project Spring Boot dengan frontend sebagai static resources.

```
gacosplit/
├── src/main/java/com/example/gacosplit/
│   ├── GacosplitApplication.java              # Main entry point
│   ├── controller/
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
│       ├── index.html                         # Single page app
│       ├── css/
│       │   └── styles.css                     # Custom styles
│       └── js/
│           ├── app.js                         # Main application logic
│           └── api.js                         # API client
├── src/test/java/                             # Unit tests
├── pom.xml                                    # Maven dependencies
└── README.md
```

**Catatan Struktur:**

| Lokasi                          | Deskripsi                                    |
| ------------------------------- | -------------------------------------------- |
| `controller/`                   | REST API endpoints                            |
| `model/`                        | JPA entities dan DTOs                         |
| `repository/`                   | Data access layer                             |
| `service/`                      | Business logic dan calculation                |
| `static/`                       | Frontend assets (HTML, CSS, JS)               |
| `static/index.html`             | SPA entry point - semua UI dalam satu file    |

### 10.2 API Endpoints

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

### 10.3 Browser Support

- Chrome 90+
- Firefox 88+
- Safari 14+
- Edge 90+
- Mobile browsers (Chrome, Safari)

### 10.4 Accessibility

- Semantic HTML (button, input, label)
- ARIA labels untuk interactive elements
- Color contrast ratio minimal 4.5:1
- Keyboard navigable
- Focus visible states

---

## 11. Future Improvements

### 11.1 Short-term (v1.1 - v1.2)

| Feature               | Effort | Impact |
| --------------------- | ------ | ------ |
| QR Code share session | Medium | High   |
| Dark mode             | Low    | Medium |
| Edit/update pesanan   | Low    | High   |

### 11.2 Medium-term (v2.0)

| Feature                   | Effort | Impact    |
| ------------------------- | ------ | --------- |
| OCR struk dari foto       | High   | Very High |
| Simpan history transaksi  | Medium | Medium    |
| Share link session        | Medium | High      |
| Progressive Web App (PWA) | Medium | Medium    |

### 11.3 Long-term (v3.0+)

| Feature                              | Effort | Impact |
| ------------------------------------ | ------ | ------ |
| Custom split ratio untuk shared item | High   | High   |
| Integrasi payment (Splitwise export) | High   | Medium |
| Multi-restoran dengan harga real     | High   | High   |
| Group chat integration               | Medium | Medium |

---

## 12. Assumptions & Trade-offs

### 12.1 Explicit Assumptions

1. **Pengguna punya HP smartphone** - Aplikasi didesain mobile-first
2. **Tidak butuh login** - Instant use, no friction
3. **Data tersimpan di server** - Backend Spring Boot dengan database
4. **Indonesian language only** - Tidak perlu i18n
5. **Gacoan only for now** - Scope dibatasi untuk MVP
6. **Shared item = bagi rata** - Selalu dibagi ke semua orang, tidak ada pilihan lain
7. **Minimum 2 orang** - Shared item tidak masuk akal kalau cuma 1 orang

### 12.2 Trade-off Decisions

| Decision            | Trade-off                                                      |
| ------------------- | -------------------------------------------------------------- |
| Shared = bagi rata  | Simple, tidak bingung, tapi tidak fleksibel untuk kasus khusus |
| Spring Boot backend | Structured, scalable, tapi butuh deployment lebih kompleks     |
| TailwindCSS         | Rapid development, tapi perlu network untuk load CSS           |
| SQLite/H2 DB        | Simple setup, tapi tidak cocok untuk production scale          |
| REST API            | Standard, familiar, tapi overhead dibanding WebSocket          |
| No auth             | Instant use, tapi tidak ada user history atau personalization  |

### 12.3 Edge Cases

| Scenario                           | Handling                                                |
| ---------------------------------- | ------------------------------------------------------- |
| Shared item ditambahkan, lalu ada  | Shared item tetap ada, bagian per orang diupdate        |
| orang dihapus                      |                                                         |
| Hapus orang terakhir               | Session tidak bisa jalan, tampilkan pesan "Min 2 orang" |
| Shared item tapi ada 1 orang       | Warning: "Shared item tidak bisa dengan 1 orang"        |
| Semua orang hapus pesanan personal | Shared item tetap dihitung (karena sudah dipesan)       |

---

_Document Version: 1.2_
_Last Updated: Mei 2026_
_Author: Product Team_
