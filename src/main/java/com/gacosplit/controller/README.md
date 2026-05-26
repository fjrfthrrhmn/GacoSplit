# Controller — REST API (Backend)

Folder ini berisi REST controller yang jadi **jembatan antara frontend (JavaScript) dan backend (Java)**.
Kalau user nambah orang, milih menu, atau minta hitung tagihan — semuanya lewat API dari folder ini.

## 📌 Tujuan

- Menyediakan **endpoint REST** yang bisa dipanggil dari JavaScript di browser
- Handle request HTTP (GET, POST, DELETE) dan balikin data dalam format **JSON**
- Jembatin data dari user ke **service layer** (tempat logic bisnis)
- Gak nyentuh database langsung — controller cuma nerusin data ke service

## 📂 Isi Folder

| File                     | Fungsi                                                                                                                                                           |
| ------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `SessionController.java` | Endpoint buat CRUD sesi split bill: bikin session, tambah/hapus orang, tambah/hapus item, minta hitung tagihan. Semua endpoint mulai dengan `/api/sessions/...`. |
| `MenuController.java`    | Endpoint buat ambil daftar menu Gacoan: `GET /api/menu`. Ngembaliin 24 item menu lengkap dengan harga.                                                           |

## Tips

- Semua controller di sini pake `@RestController` — artinya data langsung dibalikin sebagai JSON, bukan di-render ke HTML
- Bedanya sama `PageController` di `com.example.demo.controller`: kalau itu pake `@Controller` buat nampilin halaman
- Response endpoint bisa dicek langsung lewat browser: buka `http://localhost:8080/api/menu`

## 📎 Related

- `/gacosplit/service` — tempat logic bisnis, controller manggil service di sini
- `/gacosplit/model/dto` — format data yang dikirim dan diterima dari API
- `/example/demo/controller` — controller buat halaman web (bukan API)
- `/resources/static/js` — file `api.js` yang panggil endpoint dari sini
