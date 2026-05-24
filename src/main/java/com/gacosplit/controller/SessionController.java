package com.gacosplit.controller;

import com.gacosplit.model.Session;
import com.gacosplit.model.dto.SessionResponse;
import com.gacosplit.service.SessionService;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * =========================================================================
 *  SessionController — REST API untuk CRUD session, people, items, calculate
 * =========================================================================
 *
 *  BASE URL: /api/sessions
 *
 *  Controller ini adalah lapisan HTTP tipis:
 *  1. Menerima request dari client (parse path, body, headers)
 *  2. Memanggil SessionService untuk business logic
 *  3. Menangkap exception dari service
 *  4. Mengembalikan response JSON yang konsisten
 *
 *  Kenapa controller harus "tipis"?
 *  - Controller hanya urus HTTP: method, path, status code, headers
 *  - Business logic (validasi, kalkulasi, koordinasi repository)
 *    ada di SessionService dan CalculationService
 *  - Lebih mudah di-test: unit test cukup test service, tidak perlu
 *    mock HTTP request
 *
 *  Error handling:
 *  - Service method lempar IllegalArgumentException untuk error bisnis
 *    (session not found, validation error, dll)
 *  - Controller tangkap exception → return HTTP 400 Bad Request
 *    atau 404 Not Found sesuai konteks
 *
 *  Flow data:
 *  HTTP Request → Controller (parse, validasi HTTP)
 *      → SessionService (business logic, validasi data)
 *          → Repository (database)
 *      ← Response DTO / Map
 *  ← HTTP Response (JSON)
 * =========================================================================
 */

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    /*
     * Sekarang cuma SATU dependency: SessionService.
     * Sebelumnya ada 3 repository — itu terlalu detail untuk controller.
     * Controller tidak perlu tahu soal repository.
     */
    private final SessionService sessionService;

    /*
     * Constructor injection — Spring inject SessionService otomatis.
     * SessionService sudah @Service, jadi Spring tahu cara membuatnya.
     */
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    // ==================================================================
    //  1. POST /api/sessions — Buat session baru
    // ==================================================================
    /*
     * Input:
     *   { "name": "Makan Siang Kantor" }
     *   name opsional → default "Session"
     *
     * Output (201 Created):
     *   { "id": "uuid", "name": "...", "createdAt": "..." }
     *
     * Kenapa POST?
     *   POST = membuat resource BARU. HTTP 201 = "Created".
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createSession(
            @RequestBody Map<String, Object> body) {

        String name = body != null && body.containsKey("name")
                ? body.get("name").toString().trim()
                : null;

        Session session = sessionService.createSession(name);

        Map<String, Object> response = new HashMap<>();
        response.put("id", session.getId());
        response.put("name", session.getName());
        response.put("createdAt", session.getCreatedAt().toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ==================================================================
    //  2. GET /api/sessions/{id} — Ambil data session lengkap
    // ==================================================================
    /*
     * Output: SessionResponse DTO (200 OK) atau 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getSession(@PathVariable String id) {
        try {
            SessionResponse response = sessionService.toSessionResponse(id);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Service throws IllegalArgumentException jika session tidak ditemukan
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    // ==================================================================
    //  3. DELETE /api/sessions/{id}/reset — Reset session (hapus data)
    // ==================================================================
    /*
     * Fungsi: Menghapus semua orang dan item dalam session,
     * tapi session-nya tetap ada.
     */
    @DeleteMapping("/{id}/reset")
    public ResponseEntity<Map<String, String>> resetSession(@PathVariable String id) {
        try {
            sessionService.resetSession(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Session berhasil direset");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    // ==================================================================
    //  4. POST /api/sessions/{id}/people — Tambah orang
    // ==================================================================
    /*
     * Input:
     *   { "name": "Fajar" }
     * Output (201 Created):
     *   { "id": "uuid", "name": "Fajar" }
     */
    @PostMapping("/{id}/people")
    public ResponseEntity<?> addPerson(@PathVariable String id,
                                       @RequestBody Map<String, Object> body) {
        try {
            String name = body != null && body.containsKey("name")
                    ? body.get("name").toString().trim()
                    : null;

            com.gacosplit.model.Person person = sessionService.addPerson(id, name);

            Map<String, String> response = new HashMap<>();
            response.put("id", person.getId());
            response.put("name", person.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            // Kalau "tidak ditemukan" → 404, selain itu → 400
            HttpStatus status = e.getMessage().contains("tidak ditemukan")
                    ? HttpStatus.NOT_FOUND
                    : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(error);
        }
    }

    // ==================================================================
    //  5. DELETE /api/sessions/{id}/people/{personId} — Hapus orang
    // ==================================================================
    @DeleteMapping("/{id}/people/{personId}")
    public ResponseEntity<Map<String, String>> removePerson(
            @PathVariable String id, @PathVariable String personId) {
        try {
            sessionService.removePerson(id, personId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Person berhasil dihapus");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    // ==================================================================
    //  6. POST /api/sessions/{id}/items — Tambah item
    // ==================================================================
    /*
     * Input:
     *   {
     *     "name": "Mie Gacoan Level 3",
     *     "price": 18000,
     *     "quantity": 1,
     *     "isShared": false,
     *     "orderedBy": "person-uuid"
     *   }
     *
     * Output (201 Created): data item baru
     */
    @PostMapping("/{id}/items")
    public ResponseEntity<?> addItem(@PathVariable String id,
                                     @RequestBody Map<String, Object> body) {
        try {
            String name = body != null ? (String) body.get("name") : null;
            BigDecimal price = body != null && body.containsKey("price")
                    ? new BigDecimal(body.get("price").toString())
                    : null;
            Integer quantity = body != null && body.containsKey("quantity")
                    ? Integer.valueOf(body.get("quantity").toString())
                    : null;
            Boolean isShared = body != null && body.containsKey("isShared")
                    ? Boolean.valueOf(body.get("isShared").toString())
                    : false;
            String orderedBy = body != null ? (String) body.get("orderedBy") : null;

            com.gacosplit.model.Item item = sessionService.addItem(
                    id, name, price, quantity, isShared, orderedBy);

            Map<String, Object> response = new HashMap<>();
            response.put("id", item.getId());
            response.put("name", item.getName());
            response.put("price", item.getPrice());
            response.put("quantity", item.getQuantity());
            response.put("isShared", item.getIsShared());
            response.put("orderedBy", item.getOrderedBy());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    // ==================================================================
    //  7. PUT /api/sessions/{id}/items/{itemId} — Update item
    // ==================================================================
    /*
     * Input: field yang ingin diubah (partial update)
     *   { "quantity": 3 }
     */
    @PutMapping("/{id}/items/{itemId}")
    public ResponseEntity<?> updateItem(@PathVariable String id,
                                        @PathVariable String itemId,
                                        @RequestBody Map<String, Object> body) {
        try {
            com.gacosplit.model.Item item = sessionService.updateItem(id, itemId, body);

            Map<String, Object> response = new HashMap<>();
            response.put("id", item.getId());
            response.put("name", item.getName());
            response.put("price", item.getPrice());
            response.put("quantity", item.getQuantity());
            response.put("isShared", item.getIsShared());
            response.put("orderedBy", item.getOrderedBy());

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            HttpStatus status = e.getMessage().contains("tidak ditemukan")
                    ? HttpStatus.NOT_FOUND
                    : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(error);
        }
    }

    // ==================================================================
    //  8. DELETE /api/sessions/{id}/items/{itemId} — Hapus item
    // ==================================================================
    @DeleteMapping("/{id}/items/{itemId}")
    public ResponseEntity<Map<String, String>> removeItem(
            @PathVariable String id, @PathVariable String itemId) {
        try {
            sessionService.removeItem(id, itemId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Item berhasil dihapus");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    // ==================================================================
    //  9. GET /api/sessions/{id}/calculate — Hitung tagihan
    // ==================================================================
    /*
     * Fungsi: Menghitung rincian tagihan split bill.
     * Semua logic kalkulasi sudah dipindah ke CalculationService.
     * Controller tinggal panggil sessionService.calculateSession(id).
     *
     * Rumus (dari docs/architecture/functionality.md#f5):
     *   PersonalTotal(orang) = Σ(item.price × item.quantity)
     *   SharedTotal = Σ(sharedItem.price × sharedItem.quantity)
     *   SharedPerOrang = SharedTotal ÷ JumlahOrang
     *   TotalPerOrang = PersonalTotal + SharedPerOrang
     *
     * Output:
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
    @GetMapping("/{id}/calculate")
    public ResponseEntity<?> calculate(@PathVariable String id) {
        try {
            Map<String, Object> result = sessionService.calculateSession(id);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            HttpStatus status = e.getMessage().contains("tidak ditemukan")
                    ? HttpStatus.NOT_FOUND
                    : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(error);
        }
    }
}
