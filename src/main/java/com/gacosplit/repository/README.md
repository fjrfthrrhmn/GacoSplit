# repository/

Package ini berisi **JPA Repository** — jembatan antara kode Java dengan database.

## Daftar Repository

| File                      | Entity  | Method Kustom                           |
| ------------------------- | ------- | --------------------------------------- |
| `SessionRepository.java`  | Session | (CRUD standar dari JpaRepository)       |
| `PersonRepository.java`   | Person  | (CRUD standar — method kustom nanti)    |
| `ItemRepository.java`     | Item    | `findBySessionId`, `findByOrderedBy`, `findBySessionIdAndIsSharedTrue` |

> Repository menyediakan method CRUD otomatis tanpa perlu menulis implementasi.
> Cukup extends `JpaRepository<T, ID>`.
