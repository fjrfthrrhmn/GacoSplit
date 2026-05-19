> **Tujuan File:** Mengilustrasikan alur pengguna (user flow) dari langkah awal membuka aplikasi hingga mendapatkan hasil perhitungan split bill.

# User Flow

## Happy Path

```
[Mulai] --> [Tambah Orang] --> [Input Pesanan Personal] --> [Input Shared Item] --> [Cek Total] --> [Lihat Hasil]
```

## Step-by-Step Flow

### Step 1: Buat Session Baru

- User membuka aplikasi
- Klik "Mulai Session Baru"
- Opsional: Masukkan nama grup (misal "Makan bareng")

### Step 2: Tambah Orang

- Klik "Tambah Orang"
- Masukkan nama (misal: "Fajar", "Rudi", dll)
- Minimum 2 orang

### Step 3: Input Pesanan Personal

- Pilih orang dari dropdown
- Pilih menu dari template atau masukkan manual
- Tentukan quantity
- Klik "Tambah sebagai Personal"

### Step 4: Input Shared Item (Opsional)

- Klik "Tambah Shared Item"
- Pilih menu
- Tentukan quantity
- Shared item akan otomatis dibagi rata ke semua orang di session
- Klik "Tambah sebagai Shared"

### Step 5: Review & Edit

- Lihat list semua pesanan (personal & shared terpisah)
- Edit/hapus jika ada kesalahan
- Lihat total per orang (personal + bagian shared)

### Step 6: Hitung & Share

- Sistem otomatis menghitung:
  - Total personal per orang
  - Total shared item (dibagi rata)
  - Total yang harus dibayar masing-masing
- User bisa copy hasil atau screenshot

## User Flow Diagram

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
