> **Tujuan File:** Mendokumentasikan struktur file proyek, daftar endpoint API REST, dukungan browser, dan panduan aksesibilitas untuk referensi teknis pengembangan.

# Technical Implementation Notes

## File Structure

Struktur monolith - semua dalam satu project Spring Boot dengan frontend sebagai static resources.

```
gacosplit/
├── src/main/java/com/example/gacosplit/
│   ├── GacosplitApplication.java              # Main entry point
│   ├── controller/
│   │   ├── SessionController.java             # REST API untuk session
│   │   └── MenuController.java                # API untuk menu template
│   ├── model/
│   │   ├── Session.java                       # Entity session
│   │   ├── Person.java                        # Entity person
│   │   ├── Item.java                          # Entity item
│   │   └── dto/
│   │       ├── SessionRequest.java           # Request DTOs
│   │       └── SessionResponse.java          # Response DTOs
│   ├── repository/
│   │   ├── SessionRepository.java            # JPA Repository
│   │   ├── PersonRepository.java
│   │   └── ItemRepository.java
│   └── service/
│       ├── SessionService.java                # Business logic
│       └── CalculationService.java            # Logic split bill
├── src/main/resources/
│   ├── application.properties                 # Konfigurasi database
│   └── static/
│       ├── index.html                         # Single page app
│       ├── css/
│       │   └── styles.css                     # Custom styles
│       └── js/
│           ├── app.js                         # Main application logic
│           └── api.js                         # API client
├── src/test/java/                             # Unit tests
├── pom.xml                                    # Maven dependencies
└── README.md
```

**Catatan Struktur:**

| Lokasi        | Deskripsi                       |
| ------------- | ------------------------------- |
| `controller/` | REST API endpoints              |
| `model/`      | JPA entities dan DTOs           |
| `repository/` | Data access layer               |
| `service/`    | Business logic dan calculation  |
| `static/*`    | Frontend assets (HTML, CSS, JS) |

## API Endpoints

| Method | Endpoint                             | Deskripsi                     |
| ------ | ------------------------------------ | ----------------------------- |
| POST   | /api/sessions                        | Buat session baru             |
| GET    | /api/sessions/{id}                   | Ambil session by ID           |
| POST   | /api/sessions/{id}/people            | Tambah orang                  |
| DELETE | /api/sessions/{id}/people/{personId} | Hapus orang                   |
| POST   | /api/sessions/{id}/items             | Tambah item (personal/shared) |
| PUT    | /api/sessions/{id}/items/{itemId}    | Update item type              |
| DELETE | /api/sessions/{id}/items/{itemId}    | Hapus item                    |
| GET    | /api/sessions/{id}/calculate         | Hitung hasil per orang        |
| DELETE | /api/sessions/{id}/reset             | Reset session                 |

## Browser Support

- Chrome 90+
- Firefox 88+
- Safari 14+
- Edge 90+
- Mobile browsers (Chrome, Safari)

## Accessibility

- Semantic HTML (button, input, label)
- ARIA labels untuk interactive elements
- Color contrast ratio minimal 4.5:1
- Keyboard navigable
- Focus visible states
