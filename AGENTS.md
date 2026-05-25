# GacoSplit — Agent Definitions

> Definisi peran agen pengembangan untuk proyek GacoSplit.
> Dokumen ini menjadi acuan pembagian tanggung jawab dan pola kerja tim pengembangan.

---

## Development Team Roles

| Role | Agent | Responsibility |
| :--- | :---- | :------------- |
| Project Lead | Sisyphus | Orchestration, delegasi, review, verifikasi akhir — validasi output terhadap `DESIGN.md` & `docs/` |
| Backend Engineer | Sisyphus-Junior | Java/Spring Boot, REST API, data model, business logic — acuan: `DESIGN.md` (SSOT) & `docs/ARCHITECTURE.md` |
| Frontend Engineer | Sisyphus-Junior | HTML/TailwindCSS/JS, UI/UX, integrasi API — acuan: `DESIGN.md` (SSOT) & `docs/UI-UX.md` |
| Database Admin | Sisyphus-Junior | JPA entities, repository, migrasi — acuan: `docs/ARCHITECTURE.md` |
| QA Engineer | Oracle / Momus | Testing, plan review, acceptance verification — validasi terhadap `docs/FEATURES.md`, `DESIGN.md`, & `docs/ARCHITECTURE.md` |
| UX Writer | Writing Agent | Copywriting, tone of voice, microcopy — acuan: `docs/UX-WRITING.md` |
| Design Consultant | Visual-Engineer | UI/UX design, layout responsif, aksesibilitas — wajib gunakan `DESIGN.md` (SSOT) & validasi terhadap `docs/UI-UX.md` |
| Researcher | Librarian | Pencarian referensi eksternal, best practice — selaraskan rekomendasi dengan constraint di `DESIGN.md` & `docs/` |

---

**Principle:** Setiap tugas melewati siklus: Research → Plan → Execute → Review → Verify.
Setiap agen wajib memvalidasi output terhadap `DESIGN.md` (SSOT) dan constraint di direktori `docs/` sebelum memberikan hasil akhir.

---

## Knowledge Base

> **`DESIGN.md`** adalah **Single Source of Truth (SSOT)** untuk seluruh agen.
> Setiap agen wajib membaca dan memvalidasi output pengembangannya terhadap constraint yang didefinisikan di `DESIGN.md` dan direktori `docs/` sebelum memberikan hasil akhir.

### Dokumentasi Proyek

| Dokumen | Lokasi | Deskripsi |
| :------ | :----- | :-------- |
| **DESIGN.md** (SSOT) | Root | Apple-inspired design system: colors, typography, komponen, layout |
| Product & Problem & Solution | `docs/PRODUCT.md` | Gambaran produk, target pengguna, pain points, solusi, tech stack |
| Features & Acceptance & Roadmap | `docs/FEATURES.md` | Prioritas MoSCoW, acceptance criteria, rencana pengembangan |
| Architecture & Data Model & API | `docs/ARCHITECTURE.md` | Definisi entitas, fungsionalitas, endpoint API, validasi |
| User Flow | `docs/UX-FLOW.md` | Alur pengguna dari awal hingga hasil perhitungan |
| UI/UX Specification | `docs/UI-UX.md` | Token desain: warna, tipografi, shadow, spacing (Light Theme) |
| UX Writing | `docs/UX-WRITING.md` | Panduan copy, tone of voice, microcopy per section |

---

## Skills Registry

Skills yang tersedia di lingkungan pengembangan, diadaptasi dari konfigurasi lokal `~/.config/opencode/skills/`.

### Authentication & Security

| Skill Name | Domain | Description |
| :--------- | :----- | :---------- |
| `better-auth-best-practices` | Auth & Security | Configure Better Auth server/client, DB adapters, sessions |
| `create-auth-skill` | Auth & Security | Scaffold authentication in TS/JS apps using Better Auth |
| `email-and-password-best-practices` | Auth | Email verification, password reset, credential auth |
| `two-factor-authentication-best-practices` | Auth / MFA | TOTP, OTP, backup codes, trusted devices, 2FA sign-in |

### Frontend & UI

| Skill Name | Domain | Description |
| :--------- | :----- | :---------- |
| `frontend-design` | UI/UX Design | Production-grade frontend with distinctive visual identity |
| `shadcn-ui` | Component Library | Accessible React components with Radix UI + Tailwind |
| `tailwind-design-system` | CSS Framework | Scalable design systems with Tailwind CSS v3/v4 |
| `next-best-practices` | Next.js | File conventions, RSC, data patterns, metadata, error handling |

### Architecture & Methodology

| Skill Name | Domain | Description |
| :--------- | :----- | :---------- |
| `feature-sliced-design` | Frontend Arch | FSD v2.1 methodology for organizing frontend project structure |
| `context7-mcp` | Documentation | Fetch current library docs and code examples via Context7 |

### Validation & Type Safety

| Skill Name | Domain | Description |
| :--------- | :----- | :---------- |
| `typescript-advanced-types` | TypeScript | Generics, conditional types, mapped types, utility types |
| `zod-validation-expert` | Validation | Schema validation, parsing, refinements, type inference |

### Productivity

| Skill Name | Domain | Description |
| :--------- | :----- | :---------- |
| `writing-plans` | Planning | Create comprehensive implementation plans for multi-step tasks |

### Built-in Skills

| Skill Name | Domain | Description |
| :--------- | :----- | :---------- |
| `playwright` | Browser Testing | Browser automation, verification, web scraping |
| `git-master` | Version Control | Atomic commits, rebase/squash, history search (blame/log) |
| `review-work` | Code Review | Post-implementation review orchestrator (5 parallel checks) |
| `ai-slop-remover` | Code Quality | Removes AI-generated code smells from single files |

---

## Agent Communication Protocol

| Event | Channel | Response Time |
| :---- | :------ | :------------ |
| Task assignment | `task()` | Immediate |
| Bug / Issue report | Issue | < 24 jam |
| Code review request | PR Review | < 12 jam |
| Documentation update | PR | < 24 jam |
| Urgent production issue | Direct | < 2 jam |

---

## Quality Gates

Setiap rilis harus melewati quality gates berikut:

1. **LSP Diagnostics** — Zero errors di semua file yang diubah
2. **Build Pass** — `mvn clean compile` exit code 0
3. **Unit Tests** — Semua test pass (JUnit untuk Java)
4. **Manual QA** — Fitur diuji sesuai acceptance criteria (`docs/FEATURES.md`)
5. **Code Review** — Minimal oleh 1 agent lain sebelum merge
6. **Documentation** — Perubahan tercatat di CHANGELOG.md; dokumentasi di `docs/` diperbarui jika ada perubahan constraint
7. **Docs Validation** — Output pengembangan divalidasi terhadap `DESIGN.md` (SSOT) dan constraint di direktori `docs/` sebelum hasil akhir diberikan
