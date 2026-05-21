# GacoSplit — Agent Definitions

> Definisi peran agen pengembangan untuk proyek GacoSplit.
> Dokumen ini menjadi acuan pembagian tanggung jawab dan pola kerja tim pengembangan.

---

## Development Team Roles

| Role              | Agent           | Responsibility                                                                                                                                               |
| ----------------- | --------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| Project Lead      | Sisyphus        | Orchestration, delegasi, review, verifikasi akhir — validasi output terhadap `DESIGN.md` & `docs/`                                                           |
| Backend Engineer  | Sisyphus-Junior | Java/Spring Boot, REST API, data model, business logic — acuan: `DESIGN.md` (SSOT) & `docs/architecture/`                                                    |
| Frontend Engineer | Sisyphus-Junior | HTML/TailwindCSS/JS, UI/UX, integrasi API — acuan: `DESIGN.md` (SSOT) & `docs/design/`                                                                       |
| Database Admin    | Sisyphus-Junior | JPA entities, repository, migrasi — acuan: `docs/architecture/data-model.md` & `docs/architecture/assumptions.md`                                            |
| QA Engineer       | Oracle / Momus  | Testing, plan review, acceptance verification — validasi terhadap `docs/product/acceptance-criteria.md`, `DESIGN.md`, & `docs/architecture/functionality.md` |
| Documentarist     | Writing Agent   | Dokumentasi, changelog, roadmap — pastikan konsistensi dengan `DESIGN.md` (SSOT) & seluruh `docs/`                                                           |
| Design Consultant | Visual-Engineer | UI/UX design, layout responsif, aksesibilitas — wajib gunakan `DESIGN.md` (SSOT) & validasi terhadap `docs/design/ui-ux.md`                                  |
| Researcher        | Librarian       | Pencarian referensi eksternal, best practice — selaraskan rekomendasi dengan constraint di `DESIGN.md` & `docs/`                                             |

---

## Agent Workflow

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐
│  Sisyphus   │────▶│  Explore     │────▶│  Sisyphus-   │
│  (Orch.)    │     │  (Research)  │     │  Junior      │
└──────┬──────┘     └──────────────┘     └──────┬──────┘
       │                                         │
       ▼                                         ▼
┌─────────────┐                          ┌──────────────┐
│  Oracle /   │◀─────────────────────────│  Verify      │
│  Momus      │                          │  (LSP/Test)  │
└─────────────┘                          └──────────────┘
```

**Principle:** Setiap tugas melewati siklus: Research → Plan → Execute → Review → Verify. Setiap agen wajib memvalidasi output terhadap `DESIGN.md` (SSOT) dan constraint di direktori `docs/` sebelum memberikan hasil akhir.

---

## Knowledge Base

> **`DESIGN.md`** adalah **Single Source of Truth (SSOT)** untuk seluruh agen. Setiap agen wajib membaca dan memvalidasi output pengembangannya terhadap constraint yang didefinisikan di `DESIGN.md` dan direktori `docs/` sebelum memberikan hasil akhir. Seluruh dokumentasi tersedia di direktori `docs/` di root proyek.

### Dokumentasi Proyek

| Dokumen                  | Lokasi                                 | Deskripsi                                                                                    |
| ------------------------ | -------------------------------------- | -------------------------------------------------------------------------------------------- |
| **DESIGN.md** (SSOT)     | Root                                   | Apple-inspired design system: colors, typography, komponen, layout, responsif, do's & don'ts |
| Product Overview         | `docs/product/overview.md`             | Gambaran produk, target pengguna, prinsip desain                                             |
| Problem Breakdown        | `docs/product/problem.md`              | Pain points dan root cause masalah split bill                                                |
| Solution Overview        | `docs/product/solution.md`             | Konsep solusi dan tech stack                                                                 |
| Core Features            | `docs/product/features.md`             | Prioritas fitur MoSCoW (Must Have / Should Have / Could Have)                                |
| User Flow                | `docs/product/user-flow.md`            | Alur pengguna dari awal hingga hasil perhitungan                                             |
| Acceptance Criteria      | `docs/product/acceptance-criteria.md`  | Kriteria penerimaan fungsional dan non-fungsional                                            |
| Future Improvements      | `docs/product/future-improvements.md`  | Rencana pengembangan jangka pendek-menengah-panjang                                          |
| Data Model               | `docs/architecture/data-model.md`      | Definisi entitas Session, Person, Item dan template menu Gacoan                              |
| Functionality Spec       | `docs/architecture/functionality.md`   | Spesifikasi fitur, logika perhitungan, validasi, error handling                              |
| Technical Notes          | `docs/architecture/technical-notes.md` | Struktur file, endpoint API, browser support, aksesibilitas                                  |
| Assumptions & Trade-offs | `docs/architecture/assumptions.md`     | Asumsi eksplisit, trade-off decision, edge case handling                                     |
| UI/UX Specification      | `docs/design/ui-ux.md`                 | Layout structure, responsive breakpoints, konfigurasi TailwindCSS                            |

### Matriks Ketergantungan Agen — Dokumen

| Agent                          | Dokumen Wajib                                                                                | Peran Validasi                                                                                 |
| ------------------------------ | -------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------- |
| **Sisyphus** (Project Lead)    | `DESIGN.md` + seluruh `docs/`                                                                | Memastikan semua output agen konsisten dengan SSOT dan constraint dokumentasi                  |
| **Sisyphus-Junior** (Backend)  | `DESIGN.md`, `docs/architecture/*`, `docs/product/acceptance-criteria.md`                    | Validasi implementasi backend terhadap data model, functionality spec, dan acceptance criteria |
| **Sisyphus-Junior** (Frontend) | `DESIGN.md`, `docs/design/ui-ux.md`, `docs/product/user-flow.md`, `docs/product/features.md` | Validasi UI terhadap design system (DESIGN.md) dan layout spec (ui-ux.md)                      |
| **Sisyphus-Junior** (Database) | `docs/architecture/data-model.md`, `docs/architecture/assumptions.md`                        | Validasi struktur database terhadap entity definitions dan asumsi teknis                       |
| **Oracle / Momus** (QA)        | `docs/product/acceptance-criteria.md`, `docs/architecture/functionality.md`, `DESIGN.md`     | Verifikasi acceptance criteria dan functional correctness                                      |
| **Writing Agent** (Docs)       | `DESIGN.md` + seluruh `docs/`                                                                | Memastikan dokumentasi sinkron dengan SSOT dan semua keputusan desain tercatat                 |
| **Visual-Engineer** (Design)   | `DESIGN.md`, `docs/design/ui-ux.md`                                                          | Validasi setiap komponen UI terhadap design tokens (DESIGN.md) dan layout spec (ui-ux.md)      |
| **Librarian** (Research)       | `DESIGN.md`, `docs/architecture/assumptions.md`                                              | Menyelaraskan rekomendasi eksternal dengan constraint yang sudah ditetapkan                    |

---

## Skills Registry

Skills yang tersedia di lingkungan pengembangan, diadaptasi dari konfigurasi lokal `~/.config/opencode/skills/`.

### Authentication & Security

| Skill Name                                 | Domain          | Description                                                |
| ------------------------------------------ | --------------- | ---------------------------------------------------------- |
| `better-auth-best-practices`               | Auth & Security | Configure Better Auth server/client, DB adapters, sessions |
| `create-auth-skill`                        | Auth & Security | Scaffold authentication in TS/JS apps using Better Auth    |
| `email-and-password-best-practices`        | Auth            | Email verification, password reset, credential auth        |
| `two-factor-authentication-best-practices` | Auth / MFA      | TOTP, OTP, backup codes, trusted devices, 2FA sign-in      |

### Frontend & UI

| Skill Name               | Domain            | Description                                                    |
| ------------------------ | ----------------- | -------------------------------------------------------------- |
| `frontend-design`        | UI/UX Design      | Production-grade frontend with distinctive visual identity     |
| `shadcn-ui`              | Component Library | Accessible React components with Radix UI + Tailwind           |
| `tailwind-design-system` | CSS Framework     | Scalable design systems with Tailwind CSS v4                   |
| `next-best-practices`    | Next.js           | File conventions, RSC, data patterns, metadata, error handling |

### Architecture & Methodology

| Skill Name              | Domain        | Description                                                    |
| ----------------------- | ------------- | -------------------------------------------------------------- |
| `feature-sliced-design` | Frontend Arch | FSD v2.1 methodology for organizing frontend project structure |
| `context7-mcp`          | Documentation | Fetch current library docs and code examples via Context7      |

### Validation & Type Safety

| Skill Name                  | Domain     | Description                                              |
| --------------------------- | ---------- | -------------------------------------------------------- |
| `typescript-advanced-types` | TypeScript | Generics, conditional types, mapped types, utility types |
| `zod-validation-expert`     | Validation | Schema validation, parsing, refinements, type inference  |

### Productivity

| Skill Name      | Domain   | Description                                                    |
| --------------- | -------- | -------------------------------------------------------------- |
| `writing-plans` | Planning | Create comprehensive implementation plans for multi-step tasks |

### Built-in Skills

| Skill Name        | Domain          | Description                                                 |
| ----------------- | --------------- | ----------------------------------------------------------- |
| `playwright`      | Browser Testing | Browser automation, verification, web scraping              |
| `git-master`      | Version Control | Atomic commits, rebase/squash, history search (blame/log)   |
| `review-work`     | Code Review     | Post-implementation review orchestrator (5 parallel checks) |
| `ai-slop-remover` | Code Quality    | Removes AI-generated code smells from single files          |

---

## Agent Communication Protocol

| Event                   | Channel   | Response Time |
| ----------------------- | --------- | ------------- |
| Task assignment         | `task()`  | Immediate     |
| Bug / Issue report      | Issue     | < 24 jam      |
| Code review request     | PR Review | < 12 jam      |
| Documentation update    | PR        | < 24 jam      |
| Urgent production issue | Direct    | < 2 jam       |

---

## Quality Gates

Setiap rilis harus melewati quality gates berikut:

1. **LSP Diagnostics** — Zero errors di semua file yang diubah
2. **Build Pass** — `mvn clean compile` exit code 0
3. **Unit Tests** — Semua test pass (JUnit untuk Java)
4. **Manual QA** — Fitur diuji sesuai acceptance criteria (`docs/product/acceptance-criteria.md`)
5. **Code Review** — Minimal oleh 1 agent lain sebelum merge
6. **Documentation** — Perubahan tercatat di CHANGELOG.md; dokumentasi di `docs/` diperbarui jika ada perubahan constraint
7. **Docs Validation** — Output pengembangan divalidasi terhadap `DESIGN.md` (SSOT) dan constraint di direktori `docs/` sebelum hasil akhir diberikan
