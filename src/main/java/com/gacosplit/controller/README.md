# controller/

Package ini berisi **REST Controller** — lapisan API yang menerima HTTP request dan mengembalikan JSON response.

## Daftar Controller

| File | Base URL | Fungsi |
|------|----------|--------|
| `SessionController.java` | `/api/sessions` | CRUD session, people, items, calculate |
| `MenuController.java` | `/api/menu` | Daftar menu Gacoan |

## Endpoint Summary

| Method | Endpoint | Kode | Fungsi |
|--------|----------|------|--------|
| POST | `/api/sessions` | 201 | Buat session baru |
| GET | `/api/sessions/{id}` | 200 | Ambil data session |
| DELETE | `/api/sessions/{id}/reset` | 200 | Reset session |
| POST | `/api/sessions/{id}/people` | 201 | Tambah orang |
| DELETE | `/api/sessions/{id}/people/{pid}` | 200 | Hapus orang |
| POST | `/api/sessions/{id}/items` | 201 | Tambah item |
| PUT | `/api/sessions/{id}/items/{iid}` | 200 | Update item |
| DELETE | `/api/sessions/{id}/items/{iid}` | 200 | Hapus item |
| GET | `/api/sessions/{id}/calculate` | 200 | Hitung tagihan |
| GET | `/api/menu` | 200 | Daftar menu |

> Controller hanya bertanggung jawab menangani HTTP request/response.
> Business logic akan dipindah ke Service Layer di Task 3.3.
