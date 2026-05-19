# GacoSplit — Agent Definitions

> Definisi peran agen pengembangan untuk proyek GacoSplit.
> Dokumen ini menjadi acuan pembagian tanggung jawab dan pola kerja tim pengembangan.

---

## Development Team Roles

| Role              | Agent           | Responsibility                                             |
| ----------------- | --------------- | ---------------------------------------------------------- |
| Project Lead      | Sisyphus        | Orchestration, task delegation, code review, final verify  |
| Backend Engineer  | Sisyphus-Junior | Java/Spring Boot, REST API, data model, business logic     |
| Frontend Engineer | Sisyphus-Junior | HTML/TailwindCSS/JS, UI/UX implementation, API integration |
| Database Admin    | Sisyphus-Junior | JPA entities, repository layer, migration management       |
| QA Engineer       | Oracle / Momus  | Testing strategy, plan review, acceptance verification     |
| Documentarist     | Writing Agent   | Documentation, changelog, roadmap maintenance              |
| Design Consultant | Visual-Engineer | UI/UX design, responsive layout, accessibility             |
| Researcher        | Librarian       | External reference lookup, best practice research          |

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

**Principle:** Setiap tugas melewati siklus: Research → Plan → Execute → Review → Verify.

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
4. **Manual QA** — Fitur diuji sesuai acceptance criteria
5. **Code Review** — Minimal oleh 1 agent lain sebelum merge
6. **Documentation** — Perubahan tercatat di CHANGELOG.md
