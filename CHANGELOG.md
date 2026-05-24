# Changelog

All notable changes to the GacoSplit project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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
