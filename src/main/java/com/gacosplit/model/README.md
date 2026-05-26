# Model — Struktur Data (Entity)

Folder ini berisi **entity class**, yaitu representasi data dari database dalam bentuk object Java.
Setiap file di sini = satu tabel di database.

## 📌 Tujuan

- Mendefinisikan **struktur tabel** di database lewat kode Java (tanpa SQL manual)
- Nyimpen data sementara pas aplikasi jalan — object entity diisi dari database atau dari input user
- Jadi **acuan** buat repository dan service soal bentuk data
- Pake JPA (Java Persistence API) biar kita tinggal panggil method, gak perlu nulis query SQL

## 📂 Isi Folder

| File           | Fungsi                                                                                                                                                                                                                                         |
| -------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `Session.java` | Representasi sesi split bill. Nyimpen `id` (UUID), `name` (nama sesi), `createdAt` (waktu dibuat). Satu session punya banyak orang (`Person`) dan banyak item (`Item`).                                                                        |
| `Person.java`  | Representasi peserta. Nyimpen `id`, `name`, dan `session` (sesi tempat dia ikut). Juga punya field sementara (`@Transient`) kayak `personalTotal`, `sharedPortion`, `amountOwed`, dan `ppn` — ini dihitung otomatis, gak disimpan di database. |
| `Item.java`    | Representasi pesanan makanan/minuman. Nyimpen `name`, `price`, `quantity`, `isShared` (true kalo dimakan bareng), `orderedBy` (siapa yang pesan — null kalo item bersama), dan `session` (sesi tempat item ini dipesan).                       |

## Tips

- Relasi antar tabel (Session → Person, Session → Item) diatur pake anotasi `@OneToMany` atau `@ManyToOne`

## 📎 Related

- `/gacosplit/model/dto` — versi sederhana dari entity buat dikirim via API
- `/gacosplit/repository` — interface buat akses data entity ke database
