# Repository — Jembatan ke Database

Repository adalah **layer akses data** yang ngurusin komunikasi antara aplikasi Java dan database.
Tinggal panggil method, gak perlu nulis SQL manual — Spring Data JPA yang urus sisanya.

## 📌 Tujuan

- Nyediain **method siap pakai** buat simpen, ambil, ubah, dan hapus data dari database
- **Abstraksi SQL** — kita gak perlu nulis `SELECT * FROM sessions WHERE id = ?`, cukup panggil `sessionRepository.findById(id)`
- Bisa nambah **query kustom** kalo butuh pencarian yang lebih spesifik
- Terintegrasi sama **entity** — repository tahu bentuk data dari entity class

## 📂 Isi Folder

| File                     | Fungsi                                                                                                                                |
| ------------------------ | ------------------------------------------------------------------------------------------------------------------------------------- |
| `SessionRepository.java` | Akses data tabel `sessions`. Method bawaan: `findById()`, `findAll()`, `save()`, `delete()`.                                          |
| `PersonRepository.java`  | Akses data tabel `persons`. Method bawaan plus bisa nambah query kustom kayak cari orang berdasarkan session.                         |
| `ItemRepository.java`    | Akses data tabel `items`. Method bawaan buat CRUD item. Berguna pas mau hapus item tertentu atau ambil semua item dalam satu session. |

## Tips

- Repository pake **Spring Data JPA** — cukup bikin interface doang, implementasinya otomatis
- Semua repository di folder ini extends `JpaRepository<EntityType, IdType>` biar dapet method lengkap
- Mau nyari data pake kriteria tertentu? Tinggal tambah method kayak `findByName(String name)` — Spring otomatis bikin query-nya

## 📎 Related

- `/gacosplit/model` — entity class yang jadi "tabel" buat repository
- `/gacosplit/service` — service yang manggil method repository buat akses data
