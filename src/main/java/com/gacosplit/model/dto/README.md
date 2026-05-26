# DTO — Data yang Dikirim ke Frontend

DTO (Data Transfer Object) adalah **versi ringkas** dari data yang dikirim dari backend ke frontend (atau sebaliknya) lewat API.
Bedanya sama Entity: kalau Entity isinya lengkap (termasuk relasi database), DTO isinya cuma field yang diperlukan doang.

## 📌 Tujuan

- **Ngebatasin data** yang dikirim — gak semua field entity perlu dikasih liat ke frontend
- **Format API** yang konsisten — response JSON bentuknya udah pasti dan gak berubah kalau entity berubah
- **Mencegah error** — entity punya relasi yang bisa nyebabin infinite loop pas diubah jadi JSON

## 📂 Isi Folder

| File                   | Fungsi                                                                                                                                               |
| ---------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------- |
| `SessionRequest.java`  | Data yang dikirim dari frontend pas bikin session baru. Biasanya cuma `name` doang.                                                                  |
| `SessionResponse.java` | Data yang dikirim ke frontend pas user minta liat detail session. Isinya lengkap: informasi session, daftar orang, daftar item, dan hasil kalkulasi. |

## 📎 Related

- `/gacosplit/model` — entity asli yang jadi sumber data DTO
- `/gacosplit/controller` — controller yang pake DTO buat kirim response
