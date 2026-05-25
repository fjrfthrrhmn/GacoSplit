# Product

## Overview

**GacoSplit** — Aplikasi web untuk membagi tagihan di restoran Gacoan. Cepat, transparan, dan tanpa login.

**Target:** Kelompok mahasiswa/pekerja 18–35 tahun yang makan bersama.

**Design Principles:** Minimalis, mobile-first, responsif, tanpa login, shared item dibagi rata.

## Problem

| Pain Point              | Dampak                   |
| :---------------------- | :----------------------- |
| Satu orang bayar duluan | Yang lain lupa bayar     |
| Hitung manual           | Sering salah, lama       |
| Malu nagih teman        | Enggak enakan            |
| Banyak varian menu      | Perlu tracking per orang |
| Item dimakan bareng     | Bingung bagi tagihan     |

**Root Cause:** Tidak ada alat bantu fokus untuk use case ini. Tidak ada sistem netral untuk penagihan.

## Solution

| Prinsip    | Makna                             |
| :--------- | :-------------------------------- |
| Cepat      | Input & hitung < 60 detik         |
| Transparan | Semua lihat rincian               |
| Netral     | Aplikasi sebagai penengah         |
| Jelas      | Kontribusi shared item diperjelas |

**Tech Stack:** Java Spring Boot 3.x, H2 DB, REST API, HTML + TailwindCSS + Vanilla JS.
