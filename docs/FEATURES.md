# Features

## MoSCoW Priority

| Priority   | Fitur                                                                                     |
| :--------- | :---------------------------------------------------------------------------------------- |
| **Must**   | Input pesanan per orang, shared item (bagi rata), total tagihan, per orang, reset session |
| **Should** | Copy hasil, tampilan ringkas per orang, pre-loaded menu Gacoan                            |
| **Could**  | QR share session, history transaksi                                                       |
| **Won't**  | Payment gateway, OCR struk, custom split ratio                                            |

## Acceptance Criteria

| ID    | Criteria              | Target                          |
| :---- | :-------------------- | :------------------------------ |
| AC-01 | Tambah orang sukses   | Nama muncul di list             |
| AC-02 | Input personal sukses | Item muncul di list personal    |
| AC-03 | Input shared sukses   | Muncul notasi "dibagi n"        |
| AC-04 | Shared dibagi rata    | Rp 18k ÷ 3 = Rp 6k/orang        |
| AC-05 | Total akurat          | Personal + Shared = Grand Total |
| AC-06 | Per orang benar       | Personal + Bagian Shared        |
| AC-07 | Copy hasil            | Semua data ter-copy             |
| AC-08 | Reset session         | Semua data clear                |
| AC-09 | Hapus update shared   | Otomatis bagi ulang             |
| NF-01 | API Response          | < 500ms                         |
| NF-02 | Page Load             | < 3 detik                       |
| NF-03 | Mobile                | Semua fitur di HP               |

## Future Plans

| Timeline           | Feature                                 |
| :----------------- | :-------------------------------------- |
| Short-term (v1.1)  | QR share, dark mode, edit pesanan       |
| Medium-term (v2.0) | OCR struk, history, PWA                 |
| Long-term (v3.0+)  | Custom split ratio, payment integration |
