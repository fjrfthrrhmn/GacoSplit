> **Tujuan File:** Mencatat asumsi-asumsi eksplisit dalam pengembangan, trade-off dari setiap keputusan teknis, serta strategi penanganan skenario edge case.

# Assumptions & Trade-offs

## Explicit Assumptions

1. **Pengguna punya HP smartphone** - Aplikasi didesain mobile-first
2. **Tidak butuh login** - Instant use, no friction
3. **Data tersimpan di server** - Backend Spring Boot dengan database
4. **Indonesian language only** - Tidak perlu i18n
5. **Gacoan only for now** - Scope dibatasi untuk MVP
6. **Shared item = bagi rata** - Selalu dibagi ke semua orang, tidak ada pilihan lain
7. **Minimum 2 orang** - Shared item tidak masuk akal kalau cuma 1 orang

## Trade-off Decisions

| Decision            | Trade-off                                                      |
| ------------------- | -------------------------------------------------------------- |
| Shared = bagi rata  | Simple, tidak bingung, tapi tidak fleksibel untuk kasus khusus |
| Spring Boot backend | Structured, scalable, tapi butuh deployment lebih kompleks     |
| MPA over SPA        | Simpler dev, SEO-friendly, lebih ringan; kurang dinamis       |
| TailwindCSS         | Rapid development, tapi perlu network untuk load CSS           |
| SQLite/H2 DB        | Simple setup, tapi tidak cocok untuk production scale          |
| REST API            | Standard, familiar, tapi overhead dibanding WebSocket          |
| No auth             | Instant use, tapi tidak ada user history atau personalization  |

## Edge Cases

| Scenario                           | Handling                                                |
| ---------------------------------- | ------------------------------------------------------- |
| Shared item ditambahkan, lalu ada  | Shared item tetap ada, bagian per orang diupdate        |
| orang dihapus                      |                                                         |
| Hapus orang terakhir               | Session tidak bisa jalan, tampilkan pesan "Min 2 orang" |
| Shared item tapi ada 1 orang       | Warning: "Shared item tidak bisa dengan 1 orang"        |
| Semua orang hapus pesanan personal | Shared item tetap dihitung (karena sudah dipesan)       |
