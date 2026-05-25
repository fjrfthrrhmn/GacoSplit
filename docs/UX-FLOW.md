# User Flow

## Steps

1. **Buat Session** — Buka app, klik "Mulai Session Baru"
2. **Tambah Orang** — Min 2 orang, max 10
3. **Input Personal** — Pilih orang → pilih menu (template/custom) → quantity → submit
4. **Input Shared** — Pilih menu → quantity → otomatis dibagi rata
5. **Review** — Lihat list pesanan, edit/hapus jika perlu
6. **Hitung & Share** — Sistem hitung otomatis, copy hasil ke clipboard

## Flow Diagram

```
Buka App → Buat Session → Tambah Orang (min 2) → Input Pesanan 
                                                ├── Personal (wajib)
                                                └── Shared (opsional, bagi rata)
                                                → Review Total → Hitung Per Orang → Copy/Share
```
