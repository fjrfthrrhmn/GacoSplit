# JavaScript — Otak Frontend Aplikasi

Folder ini berisi semua file JavaScript yang jalan di browser.
Kalau backend (Java) ngurusin data dan perhitungan di server, JavaScript di sini ngurusin **interaksi user**, **tampilan halaman**, dan **komunikasi ke server**.

## 📌 Tujuan

- **Interaktivitas** — ngerespon klik, input form, dan aksi user lainnya
- **State management** — nyimpen data sementara (siapa aja pesertanya, item apa yang dipesan) selama user pake aplikasi
- **Render dinamis** — nampilin data ke HTML tanpa perlu reload halaman
- **Komunikasi API** — ngirim request ke backend dan nerima response

## 📂 Isi Folder

| File        | Fungsi                                                                                                                                                                                                                                                        |
| ----------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `app.js`    | File utama. Isinya semua logic aplikasi split bill: state management (`state` object), CRUD orang/item, render HTML (tampilin list orang, item, summary), kalkulasi tagihan di frontend, dan format hasil buat di-copy. Ini file terbesar dan paling penting. |
| `api.js`    | HTTP client. Isinya fungsi-fungsi buat panggil REST API backend: `createSession()`, `addPerson()`, `addItem()`, `calculate()`, dll. Semua pake `fetch()` dengan error handling yang udah diterjemahin ke Bahasa Indonesia.                                    |
| `github.js` | Utility buat nampilin kartu tim developer dari GitHub. Dipanggil di halaman utama (`index.html`) render section "Yang Dibikin Oleh". Pake GitHub API buat ambil avatar dan nama.                                                                              |

## Tips

- Baca `api.js` dulu kalo mau paham gimana frontend ngobrol sama backend — isinya tipis dan fokus ke HTTP doang
- Kalo mau ngubah cara perhitungan atau tampilan, `app.js` tempatnya — itu file paling krusial
- Urutan load di HTML penting: `api.js` harus duluan (kalo dipake), baru `app.js`
- Semua file pake JavaScript vanilla — gak pake framework kayak React atau Vue. Jadi cocok buat belajar JS murni

## 📎 Related

- `/resources/templates` — HTML yang panggil script dari sini
- `/gacosplit/controller` — API endpoint yang dipanggil `api.js`
- `/gacosplit/model/dto` — format data JSON yang dikirim/diterima dari API
