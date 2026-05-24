# Changelog

All notable changes to the GacoSplit project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [0.3.0] - 2026-05-24

### Added

- **Task 3.1: Data Model & JPA Entities**
  - `Session.java` — entity sesi split bill (UUID, name, createdAt, @OneToMany ke Person & Item, cascade ALL + orphanRemoval)
  - `Person.java` — entity peserta (UUID, name, @ManyToOne ke Session, @Transient field kalkulasi)
  - `Item.java` — entity item pesanan (UUID, name, price, quantity, isShared, orderedBy sebagai String, @ManyToOne ke Session)
  - `SessionRequest.java` — DTO untuk request buat session baru
  - `SessionResponse.java` — DTO dengan inner class PersonSummary & ItemSummary untuk response API
  - `SessionRepository.java` — JPA repository Session
  - `PersonRepository.java` — JPA repository Person
  - `ItemRepository.java` — JPA repository Item dengan query methods (findBySessionId, findByOrderedBy, findBySessionIdAndIsSharedTrue)
- Dependencies: `spring-boot-starter-data-jpa` + `h2` di `pom.xml`
- Database config: H2 in-memory, `ddl-auto=create-drop`, SQL logging di `application.properties`
- `@SpringBootApplication(scanBasePackages = {"com.example.demo", "com.gacosplit"})` untuk otomatis scan entity & repository

- **Task 3.2: REST API Endpoints**
  - `SessionController.java` — 9 endpoint REST untuk CRUD session, people, items, dan calculate
  - `MenuController.java` — endpoint GET `/api/menu` dengan daftar menu Gacoan (hardcoded)
  - Setiap endpoint menggunakan ResponseEntity dengan HTTP status yang tepat (200, 201, 400, 404)
  - Validasi input sederhana (null, empty, invalid number) dengan pesan error JSON
  - Kalkulasi split bill inline di controller (rumus Personal + Shared, pembulatan HALF_UP)
  - Response JSON konsisten menggunakan Map untuk data sederhana

---

## [0.2.0] - 2026-05-24

### Added
- Implementasi state management client-side (`app.js`) dengan struktur `state` sesuai `docs/architecture/functionality.md`.
- Fungsi CRUD: `addPerson`, `removePerson`, `addPersonalItem`, `addSharedItem`, `removeItem`.
- Implementasi rumus kalkulasi split bill (Personal + Shared / TotalPeople).
- **Task 2.1: State Management** — file `api.js` (placeholder) dan `app.js` (state + CRUD + kalkulasi).
- **Task 2.2: Data Binding & DOM Rendering**
- **Task 2.3: Copy to Clipboard**
- Placeholder API layer (`api.js`) sebagai plain script.
- Integrasi script `api.js` dan `app.js` pada `templates/index.html`.

---

## [0.1.1] - 2026-05-19

### Added

- **Documentation Restructure**: Modular documentation system with dedicated directories:
  - `docs/product/` — Product overview, problem breakdown, solution, features, user flow, acceptance criteria, future improvements
  - `docs/architecture/` — Data model, functionality specification, technical notes, assumptions & trade-offs
  - `docs/design/` — UI/UX specification, layout structure, responsive breakpoints, TailwindCSS configuration
- `CHANGELOG.md` — Change tracking file for iteration recording
- `AGENTS.md` — Agent role definitions with development team structure and skills table
- `ROADMAP.md` — Development milestones from initiation to deployment
- `README.md` — Enhanced with updated project structure and documentation links

### Changed

- Documentation extracted from monolithic `.docs/product-definition.md` into modular, maintainable files under `docs/`

---

## [0.1.0] - 2026-05-18

### Added

- Initial project scaffold with Spring Boot 4.0.6 and Maven
- Project skeleton: controller, service, model, repository layers
- `README.md` with project overview and setup instructions
- `.docs/product-definition.md` — Initial product definition document
- `HELP.md` — Spring Boot reference documentation
- Git repository initialization with `.gitattributes` and `.gitignore`

---

## Types of Changes

| Type     | Description                                  |
| -------- | -------------------------------------------- |
| `Added`  | New features, files, or capabilities         |
| `Changed`| Changes to existing functionality            |
| `Fixed`  | Bug fixes                                    |
| `Docs`   | Documentation improvements                   |
| `Removed`| Removed features or files                    |
