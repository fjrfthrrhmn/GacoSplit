/*
 * =========================================================================
 *  GacoSplit — api.js
 *  HTTP client untuk REST API backend.
 *
 *  Semua fungsi API mengembalikan Promise.
 *  Error handling: setiap fungsi akan melempar Error dengan pesan
 *  yang sudah diformat untuk ditampilkan ke user (bahasa Indonesia).
 *
 *  Strategy: "thin wrapper" — setiap fungsi hanya:
 *  1. Format URL dan body request
 *  2. Panggil fetch()
 *  3. Parse response JSON
 *  4. Lempar Error dengan pesan yang user-friendly
 *
 *  Tidak ada business logic di sini — hanya HTTP communication.
 *  Business logic tetap di app.js.
 * =========================================================================
 */

const API_BASE = '/api';

const api = {

    /*
     * ------------------------------------------------------------------
     *  _request(url, options) — internal request helper
     *
     *  Semua fungsi API lewat sini agar:
     *  - Konsisten: semua pake JSON content-type
     *  - Error handling terpusat: HTTP errors → Error dengan pesan ID
     *  - Network error: catch TypeError → pesan koneksi
     *
     *  @param {string} url    — endpoint path (relatif ke API_BASE)
     *  @param {object} options — fetch options (method, body, headers)
     *  @returns {Promise<object>} — parsed JSON response
     * ------------------------------------------------------------------
     */
    async _request(url, options = {}) {
        let response;
        try {
            response = await fetch(url, {
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                ...options
            });
        } catch (err) {
            // Network error (server down, DNS failure, CORS, dll)
            // TypeError: Failed to fetch → koneksi bermasalah
            if (err instanceof TypeError && err.message === 'Failed to fetch') {
                throw new Error('Tidak dapat terhubung ke server. Periksa koneksi atau coba lagi.');
            }
            throw new Error('Gagal terhubung ke server: ' + err.message);
        }

        // Parse response body (mungkin JSON, mungkin kosong)
        let data = null;
        const contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
            try {
                data = await response.json();
            } catch (parseErr) {
                // Response bukan JSON valid — fallback ke text
                const text = await response.text().catch(() => '');
                data = { error: text || 'Respon server tidak valid' };
            }
        }

        // HTTP error handling (4xx, 5xx)
        if (!response.ok) {
            const errorMsg = data?.error || getStatusMessage(response.status);
            throw new Error(errorMsg);
        }

        return data;
    },

    // ==================================================================
    //  SESSION API
    // ==================================================================

    /*
     * createSession(name)
     * Membuat sesi split bill baru.
     *
     * @param {string} name — Nama sesi (opsional, default "Session")
     * @returns {Promise<{id: string, name: string, createdAt: string}>}
     *
     * Response: 201 Created
     *   { "id": "uuid", "name": "Split Bill", "createdAt": "2026-05-24T..." }
     */
    async createSession(name) {
        return api._request(`${API_BASE}/sessions`, {
            method: 'POST',
            body: JSON.stringify({ name: name || 'Split Bill Gacoan' })
        });
    },

    /*
     * fetchSession(sessionId)
     * Mengambil data session lengkap (people + items).
     *
     * @param {string} sessionId — UUID session
     * @returns {Promise<SessionResponse>} — DTO session lengkap
     *
     * Response: 200 OK — SessionResponse dengan people[] dan items[]
     */
    async fetchSession(sessionId) {
        return api._request(`${API_BASE}/sessions/${sessionId}`);
    },

    /*
     * resetSession(sessionId)
     * Menghapus semua data (people + items) dalam session.
     * Session-nya tetap ada, tinggal diisi ulang.
     *
     * @param {string} sessionId — UUID session
     * @returns {Promise<{message: string}>}
     */
    async resetSession(sessionId) {
        return api._request(`${API_BASE}/sessions/${sessionId}/reset`, {
            method: 'DELETE'
        });
    },

    // ==================================================================
    //  PEOPLE API
    // ==================================================================

    /*
     * addPerson(sessionId, name)
     * Menambahkan peserta baru ke dalam session.
     *
     * @param {string} sessionId — UUID session
     * @param {string} name      — Nama peserta
     * @returns {Promise<{id: string, name: string}>} — 201 Created
     */
    async addPerson(sessionId, name) {
        return api._request(`${API_BASE}/sessions/${sessionId}/people`, {
            method: 'POST',
            body: JSON.stringify({ name })
        });
    },

    /*
     * removePerson(sessionId, personId)
     * Menghapus peserta beserta semua item personal-nya.
     *
     * @param {string} sessionId — UUID session
     * @param {string} personId  — UUID peserta
     * @returns {Promise<{message: string}>}
     */
    async removePerson(sessionId, personId) {
        return api._request(`${API_BASE}/sessions/${sessionId}/people/${personId}`, {
            method: 'DELETE'
        });
    },

    // ==================================================================
    //  ITEMS API
    // ==================================================================

    /*
     * addItem(sessionId, itemData)
     * Menambahkan item (personal atau shared) ke session.
     *
     * @param {string} sessionId — UUID session
     * @param {object} itemData  — Data item:
     *   {
     *     "name": "Mie Gacoan Level 3",
     *     "price": 18000,
     *     "quantity": 1,
     *     "isShared": false,
     *     "orderedBy": "person-uuid"  // null jika shared
     *   }
     * @returns {Promise<object>} — 201 Created, data item baru
     */
    async addItem(sessionId, itemData) {
        return api._request(`${API_BASE}/sessions/${sessionId}/items`, {
            method: 'POST',
            body: JSON.stringify(itemData)
        });
    },

    /*
     * removeItem(sessionId, itemId)
     * Menghapus item (personal atau shared) dari session.
     *
     * @param {string} sessionId — UUID session
     * @param {string} itemId    — UUID item
     * @returns {Promise<{message: string}>}
     */
    async removeItem(sessionId, itemId) {
        return api._request(`${API_BASE}/sessions/${sessionId}/items/${itemId}`, {
            method: 'DELETE'
        });
    },

    // ==================================================================
    //  CALCULATION API
    // ==================================================================

    /*
     * calculate(sessionId)
     * Menghitung rincian tagihan split bill.
     * Semua logic kalkulasi ada di CalculationService (backend).
     *
     * @param {string} sessionId — UUID session
     * @returns {Promise<object>} — Hasil perhitungan:
     *   {
     *     "sessionId": "...",
     *     "sessionName": "...",
     *     "peopleCount": 3,
     *     "sharedTotal": 18000,
     *     "sharedPerPerson": 6000,
     *     "grandTotal": 76000,
     *     "perPerson": [ ... ],
     *     "sharedItems": [ ... ]
     *   }
     */
    async calculate(sessionId) {
        return api._request(`${API_BASE}/sessions/${sessionId}/calculate`);
    },

    // ==================================================================
    //  MENU API
    // ==================================================================

    /*
     * fetchMenu()
     * Mengambil daftar menu Gacoan dari server.
     *
     * @returns {Promise<Array<{name: string, price: number}>>}
     *
     * Response: 200 OK
     *   [
     *     { "name": "Mie Gacoan Level 1", "price": 16000 },
     *     ...
     *   ]
     */
    async fetchMenu() {
        return api._request(`${API_BASE}/menu`);
    }
};

/*
 * getStatusMessage(statusCode)
 * Helper: map HTTP status code ke pesan error dalam Bahasa Indonesia.
 * Digunakan kalau response body tidak mengandung field "error".
 */
function getStatusMessage(status) {
    const messages = {
        400: 'Data yang dikirim tidak valid. Periksa kembali input Anda.',
        404: 'Data tidak ditemukan. Mungkin sudah dihapus.',
        405: 'Metode request tidak diizinkan.',
        409: 'Data sudah ada. Gunakan nama yang berbeda.',
        500: 'Terjadi kesalahan di server. Coba lagi beberapa saat.',
        502: 'Server sedang sibuk. Coba lagi.',
        503: 'Layanan sedang tidak tersedia. Coba lagi nanti.'
    };
    return messages[status] || 'Terjadi kesalahan (HTTP ' + status + '). Coba lagi.';
}
