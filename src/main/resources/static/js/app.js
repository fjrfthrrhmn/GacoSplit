/*
 * =========================================================================
 *  GacoSplit — app.js
 *  State management, DOM rendering, event binding, dan copy to clipboard.
 *
 *  ALUR PROGRAM:
 *  1. State (data) berubah → fungsi CRUD dipanggil
 *  2. Setiap perubahan state → renderAll() dipanggil
 *  3. renderAll() membersihkan area DOM lalu membangun ulang dari state
 *  4. Event listener tetap (event delegation) → tidak perlu di-render ulang
 *
 *  Kenapa pakai "clear + rebuild"?
 *  - Sederhana dan mudah dipahami pemula
 *  - Tidak perlu tracking elemen mana yang berubah
 *  - Aman dari memory leak karena event listener di parent container
 * =========================================================================
 */

// =========================================================================
//  DATA MENU GACOAN
//  Hardcoded dari docs/architecture/data-model.md
//  Nanti di Fase 3 akan diambil dari API (/api/menu)
// =========================================================================
const menuItems = [
    { name: "Mie Gacoan Level 1", price: 16000 },
    { name: "Mie Gacoan Level 2", price: 17000 },
    { name: "Mie Gacoan Level 3", price: 18000 },
    { name: "Mie Gacoan Level 4", price: 19000 },
    { name: "Mie Gacoan Level 5", price: 20000 },
    { name: "Mie Katsu",            price: 20000 },
    { name: "Mie Pangsit",          price: 20000 },
    { name: "Dimsum",               price: 15000 },
    { name: "Es Teh Manis",         price:  5000 },
    { name: "Es Jeruk",             price:  6000 },
    { name: "Tahu Crispy",          price:  8000 }
];

// =========================================================================
//  STATE — Data utama aplikasi
//  Semua data orang dan pesanan disimpan di sini.
//  Struktur ini mengikuti docs/architecture/functionality.md
// =========================================================================
const state = {
    people: [],          // Array { id, name }
    personalItems: [],   // Array { id, personId, name, price, quantity }
    sharedItems: [],     // Array { id, name, price, quantity }
    activePersonId: null // String | null — orang yang sedang dipilih di tab
};

// =========================================================================
//  FUNGSI VALIDASI
//  aturan dari docs/architecture/functionality.md#validation-rules
// =========================================================================

/*
 * validasiNama(nama) → string | null
 * Mengembalikan pesan error jika tidak valid, null jika valid.
 *
 * Aturan:
 * - Minimal 2 karakter
 * - Maksimal 30 karakter
 * - Tidak boleh duplikat dengan nama yang sudah ada
 */
function validasiNama(nama) {
    const trimmed = nama.trim();

    // Rule: minimal 2 karakter
    if (trimmed.length < 2) {
        return "Nama harus minimal 2 karakter";
    }

    // Rule: maksimal 30 karakter
    if (trimmed.length > 30) {
        return "Nama maksimal 30 karakter";
    }

    // Rule: tidak boleh duplikat (case-insensitive)
    const duplikat = state.people.some(
        p => p.name.toLowerCase() === trimmed.toLowerCase()
    );
    if (duplikat) {
        return "Nama sudah ada, gunakan nama lain";
    }

    // Rule: maksimal 10 orang (docs: max 10)
    if (state.people.length >= 10) {
        return "Maksimal 10 peserta";
    }

    // Valid: tidak ada error
    return null;
}

/*
 * validasiQuantity(value) → string | null
 * Aturan: integer, min 1, max 99
 */
function validasiQuantity(value) {
    const num = parseInt(value, 10);
    if (isNaN(num) || num < 1) {
        return "Jumlah minimal 1";
    }
    if (num > 99) {
        return "Jumlah maksimal 99";
    }
    return null;
}

// =========================================================================
//  FUNGSI CRUD — Create, Read, Update, Delete untuk state
//  Dipanggil dari event listener form/button.
//  Setiap fungsi CRUD akan memanggil renderAll() untuk update DOM.
// =========================================================================

/*
 * addPerson(name)
 * Menambahkan orang baru ke state.people.
 * Melewati validasi terlebih dahulu.
 * Jika validasi gagal, tampilkan error di UI.
 */
function addPerson(name) {
    // Validasi input
    const error = validasiNama(name);
    if (error) {
        // Tampilkan error di UI
        tampilkanError('personNameError', error);
        return false;
    }

    // Hapus error jika sebelumnya muncul
    sembunyikanError('personNameError');

    // Tambah orang baru ke array state
    // Note: pake Date.now() sebagai ID sementara.
    // Nanti di Fase 3, ID akan dari database.
    state.people.push({
        id: 'person_' + Date.now(),
        name: name.trim()
    });

    // Kalau ini orang pertama, jadikan activePerson
    if (state.people.length === 1) {
        state.activePersonId = state.people[0].id;
    }

    // Update DOM
    renderAll();
    return true;
}

/*
 * removePerson(id)
 * Hapus orang dari state.people.
 * Juga hapus semua personalItems milik orang tersebut.
 */
function removePerson(id) {
    // Hapus orang dari array
    state.people = state.people.filter(p => p.id !== id);

    // Hapus semua item personal milik orang yang dihapus
    state.personalItems = state.personalItems.filter(i => i.personId !== id);

    // Jika activePerson dihapus, pindah ke orang pertama yang tersisa
    if (state.activePersonId === id) {
        state.activePersonId = state.people.length > 0 ? state.people[0].id : null;
    }

    // Update DOM
    renderAll();
}

/*
 * addPersonalItem(personId, menuKey, quantity)
 * Tambah item personal untuk orang tertentu.
 * menuKey adalah nama menu dari dropdown.
 */
function addPersonalItem(personId, menuKey, quantity) {
    // Validasi quantity
    const qtyError = validasiQuantity(quantity);
    if (qtyError) {
        alert(qtyError);
        return false;
    }

    // Cari data menu dari template Gacoan
    const menuItem = menuItems.find(m => m.name === menuKey);
    if (!menuItem) {
        alert("Pilih menu yang valid");
        return false;
    }

    // Tambah item personal ke state
    state.personalItems.push({
        id: 'pitem_' + Date.now(),
        personId: personId,
        name: menuItem.name,
        price: menuItem.price,
        quantity: parseInt(quantity, 10)
    });

    renderAll();
    return true;
}

/*
 * addSharedItem(menuKey, quantity)
 * Tambah item bersama (shared) yang akan dibagi rata.
 */
function addSharedItem(menuKey, quantity) {
    // Validasi: minimal 2 orang untuk shared item
    if (state.people.length < 2) {
        tampilkanError('sharedError', 'Minimal 2 orang untuk menambah menu bersama');
        return false;
    }
    sembunyikanError('sharedError');

    // Validasi quantity
    const qtyError = validasiQuantity(quantity);
    if (qtyError) {
        alert(qtyError);
        return false;
    }

    // Cari data menu
    const menuItem = menuItems.find(m => m.name === menuKey);
    if (!menuItem) {
        alert("Pilih menu yang valid");
        return false;
    }

    // Tambah shared item ke state
    state.sharedItems.push({
        id: 'sitem_' + Date.now(),
        name: menuItem.name,
        price: menuItem.price,
        quantity: parseInt(quantity, 10)
    });

    renderAll();
    return true;
}

/*
 * removeItem(itemId)
 * Hapus item baik personal maupun shared berdasarkan ID.
 * Digunakan oleh tombol hapus di kedua daftar.
 */
function removeItem(itemId) {
    // Coba hapus dari personalItems dulu
    const isPersonal = state.personalItems.some(i => i.id === itemId);
    if (isPersonal) {
        state.personalItems = state.personalItems.filter(i => i.id !== itemId);
    }

    // Coba hapus dari sharedItems
    const isShared = state.sharedItems.some(i => i.id === itemId);
    if (isShared) {
        state.sharedItems = state.sharedItems.filter(i => i.id !== itemId);
    }

    renderAll();
}

/*
 * resetAll()
 * Hapus semua data — kembali ke keadaan awal.
 */
function resetAll() {
    state.people = [];
    state.personalItems = [];
    state.sharedItems = [];
    state.activePersonId = null;

    // Sembunyikan semua error
    sembunyikanError('personNameError');
    sembunyikanError('peopleCountError');
    sembunyikanError('sharedError');

    renderAll();
}

// =========================================================================
//  FUNGSI KALKULASI
//  Rumus dari docs/architecture/functionality.md#f5-calculation-logic
//
//  PersonalTotal(orang) = Σ(item.price × item.quantity)
//  SharedTotal = Σ(sharedItem.price × sharedItem.quantity)
//  SharedPerOrang = SharedTotal ÷ JumlahOrang
//  TotalPerOrang = PersonalTotal + SharedPerOrang
// =========================================================================

/*
 * hitungTagihan() → object
 * Mengembalikan hasil kalkulasi lengkap.
 * Tidak memodifikasi state — hanya baca (pure function).
 */
function hitungTagihan() {
    const jumlahOrang = state.people.length;

    // Hitung total shared: jumlah dari semua (price × quantity) item bersama
    const sharedTotal = state.sharedItems.reduce(
        (total, item) => total + (item.price * item.quantity),
        0
    );

    // Shared portion per orang: dibagi rata
    const sharedPerOrang = jumlahOrang > 0 ? sharedTotal / jumlahOrang : 0;

    // Hitung per orang
    const perOrang = state.people.map(person => {
        // Cari semua item personal milik orang ini
        const itemsPerson = state.personalItems.filter(i => i.personId === person.id);

        // Hitung total personal: Σ(price × quantity)
        const personalTotal = itemsPerson.reduce(
            (total, item) => total + (item.price * item.quantity),
            0
        );

        // Total akhir = personal + bagian shared
        const totalAkhir = personalTotal + sharedPerOrang;

        return {
            personId: person.id,
            nama: person.name,
            items: itemsPerson,
            personalTotal: personalTotal,
            sharedPortion: sharedPerOrang,
            totalAkhir: totalAkhir
        };
    });

    // Grand total = jumlah semua totalAkhir
    const grandTotal = perOrang.reduce((sum, p) => sum + p.totalAkhir, 0);

    return {
        perOrang: perOrang,
        sharedTotal: sharedTotal,
        sharedPerOrang: sharedPerOrang,
        grandTotal: grandTotal,
        jumlahOrang: jumlahOrang
    };
}

// =========================================================================
//  UTILITY: Helper untuk menampilkan/menyembunyikan pesan error
// =========================================================================

/*
 * tampilkanError(elementId, pesan)
 * Set teks error dan tampilkan elemen error.
 */
function tampilkanError(elementId, pesan) {
    const el = document.getElementById(elementId);
    if (!el) return;
    el.textContent = pesan;
    el.classList.add('visible');
}

/*
 * sembunyikanError(elementId)
 * Sembunyikan elemen error.
 */
function sembunyikanError(elementId) {
    const el = document.getElementById(elementId);
    if (!el) return;
    el.classList.remove('visible');
}

// =========================================================================
//  FUNGSI RENDER — Memperbarui DOM berdasarkan state
//
//  Strategi: "Clear & Rebuild"
//  1. Kosongkan container
//  2. Bangun HTML dari state
//  3. Masukkan ke container
//
//  Kenapa bukan "targeted update"?
//  - Lebih sederhana & mudah dipahami
//  - Event listener pakai event delegation → aman dari memory leak
// =========================================================================

/*
 * renderAll()
 * Dipanggil setiap kali state berubah.
 * Memperbarui semua area DOM: orang, item personal, shared item, summary.
 */
function renderAll() {
    renderPeople();
    renderPersonalItems();
    renderSharedItems();
    renderSummary();
    renderMenuDropdowns();
    updateButtonStates();
}

/*
 * renderPeople()
 * Merender daftar orang dalam bentuk chip.
 * Juga mengisi dropdown pemilihan orang di form personal item.
 */
function renderPeople() {
    const container = document.getElementById('peopleList');
    const emptyEl = document.getElementById('peopleEmpty');
    const errorEl = document.getElementById('peopleCountError');

    // Hapus semua konten kecuali empty state placeholder
    // Simpan referensi ke elemen empty state
    container.innerHTML = '';
    container.appendChild(emptyEl);

    if (state.people.length === 0) {
        // Tidak ada orang — tampilkan empty state
        emptyEl.style.display = 'block';

        // Sembunyikan error count
        sembunyikanError('peopleCountError');

        // Kosongkan dropdown pemilihan orang
        isiDropdownOrang([]);
        return;
    }

    // Ada orang — sembunyikan empty state
    emptyEl.style.display = 'none';

    // Tampilkan error jika kurang dari 2 orang
    if (state.people.length < 2) {
        tampilkanError('peopleCountError', 'Minimal 2 orang untuk memulai perhitungan');
    } else {
        sembunyikanError('peopleCountError');
    }

    // Render chip untuk setiap orang
    state.people.forEach(person => {
        const chip = document.createElement('span');
        chip.className = 'person-chip';
        if (state.activePersonId === person.id) {
            chip.classList.add('active');
        }
        chip.dataset.personId = person.id;

        // Nama orang
        const nameSpan = document.createElement('span');
        nameSpan.textContent = person.name;
        chip.appendChild(nameSpan);

        // Tombol hapus (×)
        const btnHapus = document.createElement('button');
        btnHapus.type = 'button';
        btnHapus.className = 'remove-person';
        btnHapus.dataset.personId = person.id;
        btnHapus.textContent = '×';
        btnHapus.title = 'Hapus ' + person.name;
        chip.appendChild(btnHapus);

        container.appendChild(chip);
    });

    // Isi ulang dropdown pemilihan orang di form personal
    isiDropdownOrang(state.people);
}

/*
 * isiDropdownOrang(daftarOrang)
 * Mengisi <select id="personalPersonSelect"> dengan opsi orang.
 */
function isiDropdownOrang(daftarOrang) {
    const select = document.getElementById('personalPersonSelect');
    if (!select) return;

    // Simpan nilai yang sedang dipilih (jika ada)
    const currentValue = select.value;

    // Kosongkan lalu isi ulang
    select.innerHTML = '<option value="">— Pilih orang —</option>';

    daftarOrang.forEach(person => {
        const option = document.createElement('option');
        option.value = person.id;
        option.textContent = person.name;
        select.appendChild(option);
    });

    // Kembalikan pilihan sebelumnya jika masih ada
    if (currentValue && daftarOrang.some(p => p.id === currentValue)) {
        select.value = currentValue;
    }
}

/*
 * renderPersonalItems()
 * Merender daftar item personal per orang.
 */
function renderPersonalItems() {
    const container = document.getElementById('personalItemsList');
    if (!container) return;

    // Filter: hanya item untuk activePerson (atau tampilkan semua jika tidak ada active)
    let itemsToShow = state.personalItems;
    if (state.activePersonId) {
        itemsToShow = state.personalItems.filter(i => i.personId === state.activePersonId);
    }

    container.innerHTML = '';

    if (itemsToShow.length === 0) {
        container.innerHTML = '<div class="empty-state">Belum ada pesanan pribadi.</div>';
        return;
    }

    // Dapatkan nama active person untuk label
    const activePerson = state.people.find(p => p.id === state.activePersonId);
    const label = activePerson ? 'Pesanan ' + activePerson.name : 'Pesanan Pribadi';

    // Header label
    const header = document.createElement('div');
    header.style.cssText = 'font-weight:600; margin-bottom:8px; font-size:0.9rem; color:#555;';
    header.textContent = label;
    container.appendChild(header);

    // Render setiap item personal
    itemsToShow.forEach(item => {
        const row = document.createElement('div');
        row.className = 'item-row';

        // Informasi item: nama dan jumlah
        const info = document.createElement('div');
        info.className = 'item-info';
        info.innerHTML = `${item.name} <strong>× ${item.quantity}</strong>`;

        // Harga
        const harga = document.createElement('div');
        harga.className = 'item-price';
        harga.textContent = 'Rp ' + (item.price * item.quantity).toLocaleString('id-ID');

        // Tombol hapus
        const btnHapus = document.createElement('button');
        btnHapus.type = 'button';
        btnHapus.className = 'danger';
        btnHapus.dataset.itemId = item.id;
        btnHapus.textContent = 'Hapus';
        btnHapus.style.cssText = 'padding:4px 10px; font-size:0.8rem;';

        row.appendChild(info);
        row.appendChild(harga);
        row.appendChild(btnHapus);

        container.appendChild(row);
    });
}

/*
 * renderSharedItems()
 * Merender daftar item bersama (shared).
 */
function renderSharedItems() {
    const container = document.getElementById('sharedItemsList');
    if (!container) return;

    container.innerHTML = '';

    if (state.sharedItems.length === 0) {
        container.innerHTML = '<div class="empty-state">Belum ada menu bersama.</div>';
        return;
    }

    state.sharedItems.forEach(item => {
        const row = document.createElement('div');
        row.className = 'item-row';

        // Informasi item
        const info = document.createElement('div');
        info.className = 'item-info';

        // Label shared badge
        const badge = '<span class="shared-badge">Bersama</span>';
        info.innerHTML = `${item.name} <strong>× ${item.quantity}</strong> ${badge}`;

        // Harga total
        const total = item.price * item.quantity;
        const harga = document.createElement('div');
        harga.className = 'item-price';
        harga.textContent = 'Rp ' + total.toLocaleString('id-ID');

        // Tombol hapus
        const btnHapus = document.createElement('button');
        btnHapus.type = 'button';
        btnHapus.className = 'danger';
        btnHapus.dataset.itemId = item.id;
        btnHapus.textContent = 'Hapus';
        btnHapus.style.cssText = 'padding:4px 10px; font-size:0.8rem;';

        row.appendChild(info);
        row.appendChild(harga);
        row.appendChild(btnHapus);

        container.appendChild(row);
    });

    // Tampilkan info pembagian
    if (state.people.length >= 2) {
        const infoBagi = document.createElement('div');
        infoBagi.style.cssText = 'font-size:0.85rem; color:#666; margin-top:8px; font-style:italic;';
        const sharedTotal = state.sharedItems.reduce((s, i) => s + (i.price * i.quantity), 0);
        const perOrang = sharedTotal / state.people.length;
        infoBagi.textContent = `Total bersama: Rp ${sharedTotal.toLocaleString('id-ID')} — ` +
            `dibagi ${state.people.length} orang = Rp ${perOrang.toLocaleString('id-ID')}/orang`;
        container.appendChild(infoBagi);
    }
}

/*
 * renderSummary()
 * Merender ringkasan tagihan: total per orang + grand total.
 */
function renderSummary() {
    const container = document.getElementById('summaryContent');
    if (!container) return;

    // Validasi: minimal 2 orang untuk kalkulasi
    if (state.people.length < 2) {
        container.innerHTML = '<div class="empty-state">Tambahkan minimal 2 orang untuk melihat hasil perhitungan.</div>';
        return;
    }

    // Hitung tagihan
    const hasil = hitungTagihan();

    // Bangun HTML summary
    let html = '<div class="summary-grid">';

    // Per orang
    hasil.perOrang.forEach(p => {
        html += `
            <div class="summary-row">
                <span><strong>${p.nama}</strong></span>
                <span>Rp ${p.totalAkhir.toLocaleString('id-ID')}</span>
            </div>
        `;

        // Detail item personal (jika ada)
        if (p.items.length > 0) {
            p.items.forEach(item => {
                const subtotal = item.price * item.quantity;
                html += `
                    <div style="display:flex; justify-content:space-between; padding:2px 0 2px 16px; font-size:0.85rem; color:#666;">
                        <span>${item.name} × ${item.quantity}</span>
                        <span>Rp ${subtotal.toLocaleString('id-ID')}</span>
                    </div>
                `;
            });
        }

        // Bagian shared
        if (hasil.sharedPerOrang > 0) {
            html += `
                <div style="display:flex; justify-content:space-between; padding:2px 0 2px 16px; font-size:0.85rem; color:#92400e;">
                    <span>Bagian menu bersama</span>
                    <span>Rp ${hasil.sharedPerOrang.toLocaleString('id-ID')}</span>
                </div>
            `;
        }
    });

    // Grand total
    html += `
        <div class="summary-row total">
            <span>Grand Total</span>
            <span>Rp ${hasil.grandTotal.toLocaleString('id-ID')}</span>
        </div>
    `;

    // Informasi shared
    if (hasil.sharedTotal > 0) {
        html += `
            <div style="font-size:0.85rem; color:#666; margin-top:8px;">
                Total menu bersama: Rp ${hasil.sharedTotal.toLocaleString('id-ID')}
                (Rp ${hasil.sharedPerOrang.toLocaleString('id-ID')}/orang)
            </div>
        `;
    }

    html += '</div>';

    container.innerHTML = html;
}

/*
 * renderMenuDropdowns()
 * Mengisi semua dropdown menu Gacoan dengan data menuItems.
 */
function renderMenuDropdowns() {
    // Daftar selector dropdown yang perlu diisi
    const selectors = ['personalMenuSelect', 'sharedMenuSelect'];

    selectors.forEach(id => {
        const select = document.getElementById(id);
        if (!select) return;

        // Simpan nilai yang sedang dipilih
        const currentValue = select.value;

        // Kosongkan
        select.innerHTML = '<option value="">— Pilih menu —</option>';

        // Isi menu
        menuItems.forEach(item => {
            const option = document.createElement('option');
            option.value = item.name;       // value = nama menu
            option.textContent = item.name + ' — Rp ' + item.price.toLocaleString('id-ID');
            select.appendChild(option);
        });

        // Kembalikan pilihan sebelumnya
        if (currentValue) {
            select.value = currentValue;
        }
    });
}

/*
 * updateButtonStates()
 * Enable/disable tombol berdasarkan kondisi state.
 * Contoh: shared item disabled jika < 2 orang.
 */
function updateButtonStates() {
    const sharedBtn = document.getElementById('addSharedBtn');
    if (sharedBtn) {
        sharedBtn.disabled = state.people.length < 2;
    }
}

// =========================================================================
//  EVENT BINDING — Menghubungkan interaksi user ke fungsi state
//
//  Strategi: Event Delegation
//  - Pasang 1 event listener di parent (bukan di setiap tombol)
//  - Cek target event dengan dataset / className
//  - Aman dari memory leak karena parent tidak di-render ulang
// =========================================================================

/*
 * initEventListeners()
 * Pasang semua event listener saat halaman pertama kali dimuat.
 * Hanya dipanggil SATU KALI.
 */
function initEventListeners() {

    // ----- FORM TAMBAH ORANG -----
    // Event: submit form tambah orang
    const formOrang = document.getElementById('addPersonForm');
    if (formOrang) {
        formOrang.addEventListener('submit', function(e) {
            e.preventDefault(); // Mencegah reload halaman

            const input = document.getElementById('personNameInput');
            const nama = input.value;

            // Panggil fungsi CRUD — validasi di dalamnya
            const berhasil = addPerson(nama);

            // Jika berhasil, kosongkan input
            if (berhasil) {
                input.value = '';
                input.focus();
            }
        });
    }

    // ----- FORM TAMBAH PERSONAL ITEM -----
    // Event: submit form personal item
    const formPersonal = document.getElementById('addPersonalItemForm');
    if (formPersonal) {
        formPersonal.addEventListener('submit', function(e) {
            e.preventDefault();

            const personSelect = document.getElementById('personalPersonSelect');
            const menuSelect = document.getElementById('personalMenuSelect');
            const qtyInput = document.getElementById('personalQuantityInput');

            const personId = personSelect.value;
            const menuKey = menuSelect.value;
            const quantity = qtyInput.value;

            // Validasi: pilih orang dulu
            if (!personId) {
                alert('Pilih orang terlebih dahulu');
                return;
            }

            // Validasi: pilih menu
            if (!menuKey) {
                alert('Pilih menu');
                return;
            }

            // Panggil fungsi CRUD
            const berhasil = addPersonalItem(personId, menuKey, quantity);

            if (berhasil) {
                // Reset form
                menuSelect.value = '';
                qtyInput.value = '1';
            }
        });
    }

    // ----- FORM TAMBAH SHARED ITEM -----
    // Event: submit form shared item
    const formShared = document.getElementById('addSharedItemForm');
    if (formShared) {
        formShared.addEventListener('submit', function(e) {
            e.preventDefault();

            const menuSelect = document.getElementById('sharedMenuSelect');
            const qtyInput = document.getElementById('sharedQuantityInput');

            const menuKey = menuSelect.value;
            const quantity = qtyInput.value;

            // Validasi: pilih menu
            if (!menuKey) {
                alert('Pilih menu');
                return;
            }

            // Panggil fungsi CRUD
            const berhasil = addSharedItem(menuKey, quantity);

            if (berhasil) {
                menuSelect.value = '';
                qtyInput.value = '1';
            }
        });
    }

    // ----- EVENT DELEGATION: HAPUS ORANG & ITEM -----
    // Satu listener untuk semua klik di dalam #peopleList
    const peopleList = document.getElementById('peopleList');
    if (peopleList) {
        peopleList.addEventListener('click', function(e) {
            // Cek apakah yang diklik adalah tombol hapus orang
            const btn = e.target.closest('.remove-person');
            if (btn && btn.dataset.personId) {
                removePerson(btn.dataset.personId);
                return;
            }

            // Cek apakah yang diklik adalah chip orang (untuk pilih activePerson)
            const chip = e.target.closest('.person-chip');
            if (chip && chip.dataset.personId) {
                state.activePersonId = chip.dataset.personId;
                renderAll();
                return;
            }
        });
    }

    // ----- EVENT DELEGATION: HAPUS ITEM (PERSONAL & SHARED) -----
    // Satu listener untuk klik hapus di personalItemsList
    const personalList = document.getElementById('personalItemsList');
    if (personalList) {
        personalList.addEventListener('click', function(e) {
            const btn = e.target.closest('button[data-item-id]');
            if (btn && btn.dataset.itemId) {
                removeItem(btn.dataset.itemId);
            }
        });
    }

    // Satu listener untuk klik hapus di sharedItemsList
    const sharedList = document.getElementById('sharedItemsList');
    if (sharedList) {
        sharedList.addEventListener('click', function(e) {
            const btn = e.target.closest('button[data-item-id]');
            if (btn && btn.dataset.itemId) {
                removeItem(btn.dataset.itemId);
            }
        });
    }

    // ----- TOMBOL COPY HASIL -----
    const copyBtn = document.getElementById('copyButton');
    if (copyBtn) {
        copyBtn.addEventListener('click', copyResult);
    }

    // ----- TOMBOL RESET / MULAI BARU -----
    const resetBtns = document.querySelectorAll('#resetButton, #resetButton2');
    resetBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            if (state.people.length === 0 && state.personalItems.length === 0 && state.sharedItems.length === 0) {
                return; // Tidak ada data, tidak perlu reset
            }
            if (confirm('Mulai baru akan menghapus semua data. Lanjutkan?')) {
                resetAll();
            }
        });
    });
}

// =========================================================================
//  COPY TO CLIPBOARD
//  docs/architecture/functionality.md#f6
// =========================================================================

/*
 * formatHasil() → string
 * Membuat teks hasil split bill yang rapi untuk dibagikan.
 * Format mengikuti template di functionality.md#f6.
 */
function formatHasil() {
    const hasil = hitungTagihan();
    let teks = '';

    // Header
    teks += '📋 Rincian Tagihan Gacoan\n';
    teks += '━━━━━━━━━━━━━━━━━━━━━━\n';
    teks += '\n';

    // Per orang
    hasil.perOrang.forEach(p => {
        teks += p.nama + ': Rp ' + Math.round(p.totalAkhir).toLocaleString('id-ID') + '\n';

        // Detail item personal
        p.items.forEach(item => {
            const subtotal = item.price * item.quantity;
            teks += '  - ' + item.name + ' (' + item.quantity + 'x) = Rp ' +
                subtotal.toLocaleString('id-ID') + '\n';
        });

        // Bagian shared
        if (hasil.sharedPerOrang > 0) {
            teks += '  - Bagian menu bersama = Rp ' +
                Math.round(hasil.sharedPerOrang).toLocaleString('id-ID') + '\n';
        }

        teks += '\n';
    });

    // Shared items detail
    if (hasil.sharedTotal > 0) {
        teks += '━━━ Menu Bersama ━━━\n';
        state.sharedItems.forEach(item => {
            const subtotal = item.price * item.quantity;
            teks += '  ' + item.name + ' (' + item.quantity + 'x) = Rp ' +
                subtotal.toLocaleString('id-ID') + '\n';
        });
        teks += '  Total bersama: Rp ' + hasil.sharedTotal.toLocaleString('id-ID') + '\n';
        teks += '  Per orang: Rp ' + Math.round(hasil.sharedPerOrang).toLocaleString('id-ID') + '\n';
        teks += '\n';
    }

    // Grand total
    teks += '━━━━━━━━━━━━━━━━━━━━━━\n';
    teks += 'Total: Rp ' + Math.round(hasil.grandTotal).toLocaleString('id-ID') + '\n';

    return teks;
}

/*
 * copyResult()
 * Menyalin teks hasil ke clipboard.
 * Fallback: jika navigator.clipboard tidak didukung, pakai textarea tersembunyi.
 * Feedback: tampilkan toast "Tersalin!".
 */
function copyResult() {
    // Validasi: pastikan ada data untuk di-copy
    if (state.people.length < 2) {
        alert('Tambahkan minimal 2 orang dan pesanan terlebih dahulu');
        return;
    }

    const teks = formatHasil();

    // Cek apakah browser mendukung Clipboard API modern
    if (navigator.clipboard && navigator.clipboard.writeText) {
        // Clipboard API (didukung Chrome 66+, Firefox 63+, Safari 13.1+)
        navigator.clipboard.writeText(teks)
            .then(function() {
                tampilkanToast('Tersalin!');
            })
            .catch(function(err) {
                // Jika gagal (misal: izin ditolak), fallback ke metode lama
                console.warn('Clipboard API gagal, pakai fallback:', err);
                fallbackCopy(teks);
            });
    } else {
        // Browser lama — fallback
        fallbackCopy(teks);
    }
}

/*
 * fallbackCopy(teks)
 * Metode fallback: buat textarea tersembunyi, select, copy.
 * Bekerja di hampir semua browser.
 */
function fallbackCopy(teks) {
    // Buat elemen textarea sementara
    const textarea = document.createElement('textarea');
    textarea.value = teks;

    // Sembunyikan secara visual tapi tetap di DOM
    textarea.style.position = 'fixed';
    textarea.style.left = '-9999px';
    textarea.style.top = '-9999px';
    textarea.style.width = '1px';
    textarea.style.height = '1px';
    textarea.style.opacity = '0';
    textarea.readOnly = true;

    document.body.appendChild(textarea);

    try {
        // Select dan copy
        textarea.focus();
        textarea.select();
        const berhasil = document.execCommand('copy');

        if (berhasil) {
            tampilkanToast('Tersalin!');
        } else {
            alert('Gagal menyalin. Silakan salin manual.');
        }
    } catch (err) {
        console.error('Fallback copy gagal:', err);
        alert('Gagal menyalin. Silakan salin manual.');
    } finally {
        // Hapus textarea dari DOM
        document.body.removeChild(textarea);
    }
}

/*
 * tampilkanToast(pesan)
 * Menampilkan notifikasi toast melayang.
 * Toast akan otomatis menghilang setelah 2 detik.
 */
function tampilkanToast(pesan) {
    const toast = document.getElementById('toast');
    if (!toast) return;

    // Set pesan
    toast.textContent = pesan;

    // Tampilkan dengan animasi
    toast.classList.add('visible');

    // Hapus toast setelah 2 detik
    setTimeout(function() {
        toast.classList.remove('visible');
    }, 2000);
}

// =========================================================================
//  INISIALISASI — Saat halaman selesai dimuat
// =========================================================================

/*
 * Perintah di bawah ini jalan saat file JavaScript selesai diparsing.
 * Urutan:
 * 1. Isi dropdown menu
 * 2. Render semua section (kosong — state belum ada data)
 * 3. Pasang event listener
 *
 * Note: event listener dipasang lewat initEventListeners(), bukan
 * inline di HTML. Ini memisahkan struktur (HTML) dari perilaku (JS).
 */
renderMenuDropdowns();
renderAll();
initEventListeners();
