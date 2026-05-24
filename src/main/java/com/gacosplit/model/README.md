# model/

Package ini berisi **Entity JPA** dan **DTO**.

## Entities (Tabel Database)

| File           | Tabel        | Deskripsi                     |
| -------------- | ------------ | ----------------------------- |
| `Session.java` | `sessions`   | Sesi split bill (induk data)  |
| `Person.java`  | `persons`    | Peserta dalam sesi            |
| `Item.java`    | `items`      | Item pesanan (personal/shared)|

## DTO (dto/)

| File                  | Fungsi                         |
| --------------------- | ------------------------------ |
| `SessionRequest.java` | Request body untuk buat sesi   |
| `SessionResponse.java`| Response body data sesi lengkap |

> Entity = struktur tabel database.
> DTO = format komunikasi API (beda dengan entity).
