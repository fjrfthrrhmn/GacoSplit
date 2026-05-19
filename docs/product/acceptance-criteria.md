> **Tujuan File:** Mendefinisikan kriteria penerimaan fungsional dan non-fungsional yang harus dipenuhi oleh aplikasi sebelum dinyatakan layak rilis.

# Acceptance Criteria

## Functional Acceptance

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

## Non-Functional Acceptance

| ID    | Criteria          | Target                         |
| ----- | ----------------- | ------------------------------ |
| NF-01 | API Response Time | < 500ms untuk setiap request   |
| NF-02 | Page Load Time    | < 3 detik                      |
| NF-03 | Mobile usability  | Semua fitur bisa dipakai di HP |
