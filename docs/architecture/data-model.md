> **Tujuan File:** Mendefinisikan struktur data inti aplikasi (entitas Session, Person, dan Item) beserta template menu Gacoan yang digunakan untuk input cepat.

# Data Model

## Entity Definitions

```java
// Session
public class Session {
    String id;
    String name;
    LocalDateTime createdAt;
    List<Person> people;
    BigDecimal totalAmount;
    BigDecimal sharedAmount;
}

// Person
public class Person {
    String id;
    String name;
    List<Item> personalItems;  // item yang dipesan sendiri
    BigDecimal personalTotal; // subtotal item personal
    BigDecimal sharedPortion;  // bagian dari shared item (dihitung otomatis)
    BigDecimal amountOwed;     // personalTotal + sharedPortion
}

// Item
public class Item {
    String id;
    String name;
    BigDecimal price;
    Integer quantity;
    Boolean isShared;          // true = shared item, false = personal
    String orderedBy;          // person-id (null jika isShared = true)
}
```

## Menu Gacoan Template

| Menu               | Harga     |
| ------------------ | --------- |
| Mie Gacoan Level 1 | Rp 16.000 |
| Mie Gacoan Level 2 | Rp 17.000 |
| Mie Gacoan Level 3 | Rp 18.000 |
| Mie Gacoan Level 4 | Rp 19.000 |
| Mie Gacoan Level 5 | Rp 20.000 |
| Mie Katsu          | Rp 20.000 |
| Mie Pangsit        | Rp 20.000 |
| Dimsum             | Rp 15.000 |
| Es Teh Manis       | Rp 5.000  |
| Es Jeruk           | Rp 6.000  |
| Tahu Crispy        | Rp 8.000  |
