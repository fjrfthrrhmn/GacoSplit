> **Tujuan File:** Menyediakan spesifikasi navigasi antar halaman (MPA), tata letak antarmuka (layout structure), breakpoint responsif, dan konfigurasi warna TailwindCSS sebagai panduan implementasi frontend.

# UI/UX Specification

## Navigasi Halaman (MPA)

Aplikasi menggunakan arsitektur **Multi-Page Application (MPA)** dengan navigasi global yang konsisten di semua halaman.

```
┌──────────────────────────────────────────────────┐
│  🍜 GacoSplit     [Beranda]  [Tentang]           │  Navbar
├──────────────────────────────────────────────────┤
│                                                    │
│  (Konten halaman — bervariasi per halaman)        │
│  - Home: Split bill calculator                    │
│  - About: Informasi aplikasi & cara pakai         │
│                                                    │
└──────────────────────────────────────────────────┘
```

**Aturan Navigasi:**

| Elemen               | Deskripsi                                           |
| -------------------- | --------------------------------------------------- |
| Logo "GacoSplit"     | Klik → kembali ke Beranda                           |
| Nav link "Beranda"   | Arahkan ke `/index.html`, tandai `aria-current`     |
| Nav link "Tentang"   | Arahkan ke `/about.html`, tandai `aria-current`     |
| Navigasi responsif   | Navbar menjadi hamburger menu di layar < 640px      |
| Konsistensi visual   | Navbar identik di semua halaman (warna, font, ukuran) |

---

## Layout Structure (Halaman Home)

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

## Halaman About — Layout

Halaman About menyediakan informasi tentang aplikasi, cara penggunaan, dan data tim pengembang.

```
┌──────────────────────────────────────┐
│  🍜 GacoSplit           Navbar       │  Header
├──────────────────────────────────────┤
│  ┌────────────────────────────────┐  │
│  │  Tentang GacoSplit             │  │  Hero section
│  │  Aplikasi split bill cepat &   │  │
│  │  transparan untuk Gacoan.      │  │
│  └────────────────────────────────┘  │
│                                      │
│  ┌────────────────────────────────┐  │
│  │  Cara Pakai                    │  │  Steps
│  │  1. Tambah orang               │  │
│  │  2. Input pesanan              │  │
│  │  3. Tambah shared item         │  │
│  │  4. Lihat hasil & salin        │  │
│  └────────────────────────────────┘  │
│                                      │
│  ┌────────────────────────────────┐  │
│  │  Tentang Proyek                │  │  Info section
│  │  Tech stack, versi, lisensi    │  │
│  └────────────────────────────────┘  │
│                                      │
│  ┌────────────────────────────────┐  │
│  │  Footer                        │  │
│  │  © 2026 GacoSplit              │  │
│  └────────────────────────────────┘  │
└──────────────────────────────────────┘
```

## Responsive Breakpoints

| Breakpoint | Width          | Layout                            |
| ---------- | -------------- | --------------------------------- |
| Mobile     | < 640px        | Single column, full-width buttons |
| Tablet     | 640px - 1024px | Side-by-side input dan list       |
| Desktop    | > 1024px       | Max-width 800px centered          |

## TailwindCSS Configuration

| Utility Class   | Usage                              |
| --------------- | ---------------------------------- |
| `bg-red-600`    | Primary button (warna khas Gacoan) |
| `bg-amber-50`   | Card background (creamy)           |
| `bg-blue-50`    | Shared item card (dibedakan biru)  |
| `text-gray-800` | Primary text                       |
| `text-gray-500` | Secondary text                     |
| `rounded-lg`    | Card dan button corners            |
