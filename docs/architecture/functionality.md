> **Tujuan File:** Menjelaskan spesifikasi fungsional setiap fitur secara detail, termasuk logika perhitungan split bill, state management, aturan validasi, dan penanganan error.

# Functionality Specification

## Core Features Detail

### F1: Tambah Session

- Generate UUID untuk session
- Simpan timestamp creation
- Initialize empty lists untuk people dan items
- Return session ID untuk tracking

### F2: Tambah/Hapus Orang

- Minimum: 2 orang
- Maksimum: 10 orang
- Prevent duplicate nama
- Hapus orang akan:
  - Hapus semua item personal orang tersebut
  - Redistribusi ulang bagian shared item ke orang yang tersisa

### F3: Input Pesanan Personal

- Pilih orang dari dropdown
- Pilih menu dari template Gacoan
- Quantity: 1-99
- Harga auto-fill dari template
- Support custom item dengan harga manual
- Item ditandai sebagai personal (isShared = false)

### F4: Input Shared Item

- Pilih menu dari template
- Tentukan quantity
- Item ditandai sebagai shared (isShared = true)
- Shared item TIDAK punya orderedBy
- Shared item akan otomatis dibagi rata ke semua orang di session
- User TIDAK bisa pilih siapa saja yang mau bagi shared item (selalu semua orang)

### F5: Calculation Logic

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

### F6: Copy Hasil ke Clipboard

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

## State Management

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

## Validation Rules

| Field        | Rule                                        |
| ------------ | ------------------------------------------- |
| Nama orang   | Required, min 2 chars, max 30 chars         |
| Nama item    | Required jika custom item                   |
| Harga        | Required, numeric, min 0, max 999.999       |
| Quantity     | Required, integer, min 1, max 99            |
| Jumlah orang | Min 2, max 10                               |
| Shared item  | Hanya bisa ditambahkan jika minimal 2 orang |

## Error Handling

| Error Case                    | Handling                               |
| ----------------------------- | -------------------------------------- |
| Nama kosong                   | Tampilkan inline error, disable submit |
| Shared item tanpa orang       | Disable tombol shared item             |
| API call gagal                | Show toast notification, retry option  |
| Minimum orang tidak terpenuhi | Disable tombol hitung                  |
