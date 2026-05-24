package com.gacosplit.model.dto;

/*
 * =========================================================================
 *  SessionRequest — DTO untuk request buat session baru
 * =========================================================================
 *
 *  DTO (Data Transfer Object):
 *  - Objek yang membawa data antara client dan server
 *  - Beda dengan Entity: DTO hanya untuk komunikasi, bukan untuk database
 *  - Entity = struktur database; DTO = struktur API
 *
 *  SessionRequest digunakan saat client mengirim POST /api/sessions.
 *  Saat ini hanya berisi field "name" (nama sesi).
 *  Nanti bisa ditambah field lain sesuai kebutuhan.
 *
 *  Contoh request body (JSON):
 *  {
 *    "name": "Makan Siang Kantor"
 *  }
 * =========================================================================
 */
public class SessionRequest {

    /*
     * Nama sesi yang dikirim dari frontend.
     * Optional: bisa kosong, nanti diisi default "Session" oleh service.
     */
    private String name;

    /*
     * Constructor default (dibutuhkan untuk JSON deserialization).
     */
    public SessionRequest() {
    }

    /*
     * Constructor dengan nama.
     */
    public SessionRequest(String name) {
        this.name = name;
    }

    /*
     * Getter & Setter
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
