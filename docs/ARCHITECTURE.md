# Architecture

## Data Model

```java
Session { String id, name; LocalDateTime createdAt; List<Person> people; BigDecimal totalAmount, sharedAmount; }
Person  { String id, name; List<Item> personalItems; BigDecimal personalTotal, sharedPortion, amountOwed; }
Item    { String id, name; BigDecimal price; Integer quantity; Boolean isShared; String orderedBy; }
```

**Menu Gacoan Template:** Mie Level 1–5 (16k–20k), Mie Katsu (20k), Mie Pangsit (20k), Dimsum (15k), Es Teh (5k), Es Jeruk (6k), Tahu Crispy (8k).

## Functionality

| ID  | Feature                                                                       |
| :-- | :---------------------------------------------------------------------------- |
| F1  | Buat session (UUID + timestamp)                                               |
| F2  | Tambah/hapus orang (min 2, max 10)                                            |
| F3  | Input pesanan personal per orang (template/custom)                            |
| F4  | Input shared item — dibagi rata ke semua orang                                |
| F5  | Hitung: `AmountOwed = (PersonalTotal + (SharedTotal ÷ n)) × (1 + 11%)`       |
| F6  | Copy hasil ke clipboard                                                       |

**Calculation Formula:**
```
PersonalTotal(orang) = Σ(item.price × item.quantity)
SharedTotal = Σ(sharedItem.price × sharedItem.quantity)
SharedPerOrang = SharedTotal ÷ n
DPP = PersonalTotal + SharedPerOrang
PPN = DPP × 11% (dibulatkan ke rupiah penuh)
TotalPerOrang = DPP + PPN
```

**Calculation Example:** Personal (Fajar 23k, Rudi 20k, Toni 15k) + Shared 18k ÷ 3 = DPP Fajar 29k, Rudi 26k, Toni 21k; PPN 11% = Fajar 3.190, Rudi 2.860, Toni 2.310 → Total Fajar 32.190, Rudi 28.860, Toni 23.310.

**Validation:** Nama required (2–30 chars), harga max 999.999, quantity 1–99, min 2 orang.

**Error:** Empty name → inline error. Shared without people → disabled. API fail → toast + retry.

## Technical Notes

**Structure:** Monolith Spring Boot + Thymeleaf. REST API + static frontend.

| Method | Endpoint                          | Description   |
| :----- | :-------------------------------- | :------------ |
| POST   | `/api/sessions`                   | Buat session  |
| GET    | `/api/sessions/{id}`              | Ambil session |
| POST   | `/api/sessions/{id}/people`       | Tambah orang  |
| DELETE | `/api/sessions/{id}/people/{pid}` | Hapus orang   |
| POST   | `/api/sessions/{id}/items`        | Tambah item   |
| DELETE | `/api/sessions/{id}/items/{iid}`  | Hapus item    |
| GET    | `/api/sessions/{id}/calculate`    | Hitung hasil  |
| DELETE | `/api/sessions/{id}/reset`        | Reset session |
