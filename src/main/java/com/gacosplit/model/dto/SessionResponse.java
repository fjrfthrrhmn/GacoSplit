package com.gacosplit.model.dto;

import com.gacosplit.model.Session;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/*
 * =========================================================================
 *  SessionResponse — DTO untuk response data session
 * =========================================================================
 *
 *  Digunakan saat client request GET /api/sessions/{id}.
 *  Isinya data session lengkap: peserta, item, hasil perhitungan.
 *
 *  Kenapa pakai DTO khusus, bukan langsung entity Session?
 *  1. Entity punya relasi JPA → bisa menyebabkan loop tak terbatas
 *     (Session punya Person, Person punya Session, dst.)
 *  2. Entity mungkin punya field yang tidak perlu diekspos ke client
 *  3. DTO bisa diformat sesuai kebutuhan frontend
 *
 *  Best practice: Jangan pernah mengembalikan Entity langsung ke API!
 *  Selalu gunakan DTO.
 * =========================================================================
 */
public class SessionResponse {

    private String id;
    private String name;
    private LocalDateTime createdAt;
    private List<PersonSummary> people;
    private List<ItemSummary> items;
    private BigDecimal totalAmount;
    private BigDecimal sharedAmount;

    /*
     * Constructor dari Entity Session.
     * Method ini mengonversi entity ke DTO — memisahkan
     * data internal (entity) dari data API (DTO).
     *
     * @param session Entity Session dari database
     */
    public SessionResponse(Session session) {
        this.id = session.getId();
        this.name = session.getName();
        this.createdAt = session.getCreatedAt();
        this.totalAmount = session.getTotalAmount();
        this.sharedAmount = session.getSharedAmount();
    }

    /*
     * Getter & Setter
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<PersonSummary> getPeople() {
        return people;
    }

    public void setPeople(List<PersonSummary> people) {
        this.people = people;
    }

    public List<ItemSummary> getItems() {
        return items;
    }

    public void setItems(List<ItemSummary> items) {
        this.items = items;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getSharedAmount() {
        return sharedAmount;
    }

    public void setSharedAmount(BigDecimal sharedAmount) {
        this.sharedAmount = sharedAmount;
    }

    /*
     * =============================================================
     *  Inner class: PersonSummary
     *  Ringkasan data peserta untuk response API.
     *  Tidak membawa relasi Session (biar tidak loop).
     * =============================================================
     */
    public static class PersonSummary {
        private String id;
        private String name;
        private BigDecimal personalTotal;
        private BigDecimal sharedPortion;
        private BigDecimal amountOwed;

        public PersonSummary(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public BigDecimal getPersonalTotal() { return personalTotal; }
        public void setPersonalTotal(BigDecimal personalTotal) { this.personalTotal = personalTotal; }
        public BigDecimal getSharedPortion() { return sharedPortion; }
        public void setSharedPortion(BigDecimal sharedPortion) { this.sharedPortion = sharedPortion; }
        public BigDecimal getAmountOwed() { return amountOwed; }
        public void setAmountOwed(BigDecimal amountOwed) { this.amountOwed = amountOwed; }
    }

    /*
     * =============================================================
     *  Inner class: ItemSummary
     *  Ringkasan data item untuk response API.
     * =============================================================
     */
    public static class ItemSummary {
        private String id;
        private String name;
        private BigDecimal price;
        private Integer quantity;
        private Boolean isShared;
        private String orderedBy;

        public ItemSummary(String id, String name, BigDecimal price,
                          Integer quantity, Boolean isShared, String orderedBy) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.isShared = isShared;
            this.orderedBy = orderedBy;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public BigDecimal getPrice() { return price; }
        public Integer getQuantity() { return quantity; }
        public Boolean getIsShared() { return isShared; }
        public String getOrderedBy() { return orderedBy; }
    }
}
