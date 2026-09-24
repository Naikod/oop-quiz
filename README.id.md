# Kuis Latihan OOP — Kasir Kafe (POS)

🇬🇧 English version: [README.md](README.md)

## Studi Kasus

Kalian akan membangun inti dari sebuah **aplikasi kasir kafe (point-of-sale)** sederhana:

- Kafe memiliki **menu** yang berisi makanan dan minuman.
- Seorang **pelanggan** membuat **pesanan** yang berisi satu atau lebih item menu beserta jumlahnya.
- Pesanan senilai **Rp 100.000 atau lebih** mendapat **diskon 10%**.
- **Kasir** menerima uang tunai dan menghitung kembaliannya.

Kuis ini mencakup empat konsep OOP: **class**, **object**, **encapsulation**, dan **relasi antar kelas** (association, aggregation, composition, dependency).

## Diagram Kelas

![Class Diagram](docs/class-diagram.png)

Kelas yang bertanda **«create this class»** belum ada — kalian harus membuatnya sendiri.

### 4 Relasi Kelas dalam Proyek Ini

| Relasi | Di mana | Maknanya di sini |
|---|---|---|
| **Association** | `OrderItem → MenuItem`, `Order → Customer` | Objek menyimpan referensi ke objek lain yang hidup mandiri. |
| **Aggregation** | `Menu ◇→ MenuItem` | `Menu` mengumpulkan objek `MenuItem` yang **dibuat di luar** lalu dimasukkan; mereka tetap bisa ada tanpa menu. |
| **Composition** | `Order ◆→ OrderItem` | `Order` **membuat sendiri** objek `OrderItem` di dalam `addItem(...)`; objek itu tidak bisa ada tanpa pesanannya. |
| **Dependency** | `Cashier ⇢ Order` | `Cashier` hanya **memakai** `Order` sebagai parameter method; tidak pernah menyimpannya di field. |

## Bagian 1 — Lengkapi Kelas Kerangka (Skeleton)

File-file ini sudah ada di `src/main/java/id/ac/polinema/oop/`. Ganti setiap `throw new UnsupportedOperationException(...)` dengan implementasi yang benar. **Gunakan array biasa — JANGAN gunakan `List`/`ArrayList`** (Collections adalah materi pertemuan berikutnya).

### `MenuItem`

| Field | Tipe | Visibilitas |
|---|---|---|
| name | String | private |
| price | double | private |

| Anggota | Deskripsi |
|---|---|
| `MenuItem(String name, double price)` | Menyimpan kedua parameter ke dalam field. |
| `getName()` / `getPrice()` | Mengembalikan nilai field. |
| `setPrice(double price)` | Memperbarui harga. Harga **negatif** melempar `IllegalArgumentException` dan harga lama tidak berubah. |

### `Customer`

| Field | Tipe | Visibilitas |
|---|---|---|
| customerId | String | private |
| name | String | private |

| Anggota | Deskripsi |
|---|---|
| `Customer(String customerId, String name)` | Menyimpan kedua parameter ke dalam field. |
| `getCustomerId()` / `getName()` | Mengembalikan nilai field. |
| `setName(String name)` | Memperbarui nama. Nama **null atau kosong (blank)** melempar `IllegalArgumentException` dan nama lama tidak berubah. |

### `Menu` *(aggregation terhadap MenuItem)*

| Field | Tipe | Visibilitas |
|---|---|---|
| items | MenuItem[] (kapasitas **10**) | private |
| itemCount | int | private |

| Anggota | Deskripsi |
|---|---|
| `Menu()` | Menginisialisasi array (kapasitas 10) dan counter (0). |
| `addMenuItem(MenuItem item)` | Menyimpan item pada indeks `itemCount`, lalu menaikkan counter. Jika menu sudah penuh (10 item), tidak melakukan apa-apa. |
| `findItem(String name)` | Mengembalikan `MenuItem` dengan nama yang sama persis, atau `null` jika tidak ditemukan. |
| `getItemCount()` | Mengembalikan jumlah item yang tersimpan. |

## Bagian 2 — Buat Kelas Baru

File-file ini **belum ada**. Buatlah di `src/main/java/id/ac/polinema/oop/` persis seperti spesifikasi pada diagram kelas.

### `OrderItem` *(association ke MenuItem)*

Satu baris pesanan: sebuah item menu beserta jumlahnya.

| Field | Tipe | Visibilitas |
|---|---|---|
| menuItem | MenuItem | private |
| quantity | int | private |

| Anggota | Deskripsi |
|---|---|
| `OrderItem(MenuItem menuItem, int quantity)` | Menyimpan kedua parameter ke dalam field. |
| `getMenuItem()` / `getQuantity()` | Mengembalikan nilai field. |
| `getSubtotal()` | Mengembalikan `harga × jumlah` (tipe `double`). |

### `Order` *(composition terhadap OrderItem, association ke Customer)*

| Field | Tipe | Visibilitas |
|---|---|---|
| customer | Customer | private |
| items | OrderItem[] (kapasitas **10**) | private |
| itemCount | int | private |

| Anggota | Deskripsi |
|---|---|
| `Order(Customer customer)` | Menyimpan customer, menginisialisasi array (kapasitas 10) dan counter (0). |
| `getCustomer()` | Mengembalikan customer. |
| `addItem(MenuItem item, int quantity)` | **Membuat objek `OrderItem` baru di dalam method ini** (inilah composition!), menyimpannya pada indeks `itemCount`, lalu menaikkan counter. Jika pesanan sudah penuh (10 baris), tidak melakukan apa-apa. |
| `getItemCount()` | Mengembalikan jumlah baris pesanan yang tersimpan. |
| `getTotal()` | Mengembalikan jumlah subtotal semua baris pesanan (`double`). |
| `getFinalTotal()` | Mengembalikan jumlah yang harus dibayar: jika `getTotal() >= 100000`, terapkan **diskon 10%** (`total × 0.9`); selain itu kembalikan total apa adanya. |

### `Cashier` *(dependency terhadap Order)*

Tanpa field. `Order` hanya dipakai sebagai **parameter** — jangan pernah disimpan di field.

| Anggota | Deskripsi |
|---|---|
| `calculateChange(Order order, double cash)` | Mengembalikan `cash - order.getFinalTotal()`. Jika uang tunai **kurang dari** total akhir, melempar `IllegalArgumentException`. |

## Cara Kerja

1. Terima assignment dan clone repositori kalian dari GitHub Classroom.
2. Lengkapi kelas kerangka (Bagian 1) dan buat kelas baru (Bagian 2).
3. Uji secara lokal:
   ```bash
   mvn test
   ```
   Menjalankan satu grup tes saja, misalnya:
   ```bash
   mvn test -Dtest=MenuItemConstructorTest
   ```
4. Commit dan push. Setiap push otomatis memicu autograding; skor muncul di tab **Actions** repositori dan di dashboard GitHub Classroom.

Tips: kerjakan sesuai urutan tabel penilaian di bawah — poinnya kecil dan bertahap, jadi setiap langkah yang selesai langsung menambah skor kalian.

## Coba Aplikasinya Secara Manual

`Main.java` adalah area bebas untuk mencoba (tidak dinilai). Setelah semua kelas selesai, tulis demo kalian di sana, contohnya:

```java
public static void main(String[] args) {
    Menu menu = new Menu();
    menu.addMenuItem(new MenuItem("Es Kopi Susu", 18000));
    menu.addMenuItem(new MenuItem("Roti Bakar", 12000));

    Customer budi = new Customer("C001", "Budi Santoso");
    Order order = new Order(budi);
    order.addItem(menu.findItem("Es Kopi Susu"), 2);
    order.addItem(menu.findItem("Roti Bakar"), 1);

    Cashier cashier = new Cashier();
    double cash = 50000;

    System.out.println("Customer : " + order.getCustomer().getName());
    System.out.println("Total    : " + order.getTotal());
    System.out.println("Payable  : " + order.getFinalTotal());
    System.out.println("Cash     : " + cash);
    System.out.println("Change   : " + cashier.calculateChange(order, cash));
}
```

Jalankan dengan:

```bash
mvn -q compile exec:java
```

Keluaran yang diharapkan:

```
Customer : Budi Santoso
Total    : 48000.0
Payable  : 48000.0
Cash     : 50000.0
Change   : 2000.0
```

## Penilaian

Total **100 poin**, terbagi ke 12 grup tes kecil (dijalankan lewat GitHub Actions):

| # | Grup Tes | Konsep | Poin |
|---|---|---|---|
| 1 | MenuItem Constructor Test | class & object | 5 |
| 2 | MenuItem Getter Test | class & object | 5 |
| 3 | MenuItem Encapsulation Test | encapsulation | 10 |
| 4 | Customer Test | encapsulation | 10 |
| 5 | Menu Aggregation Test | aggregation | 10 |
| 6 | OrderItem Class Structure Test | kelas baru, association | 10 |
| 7 | OrderItem Subtotal Test | perilaku objek | 5 |
| 8 | Order Class Structure Test | kelas baru, association | 10 |
| 9 | Order Add Item Test | composition | 10 |
| 10 | Order Total Test | penelusuran relasi | 10 |
| 11 | Order Discount Test | logika bisnis | 5 |
| 12 | Cashier Test | dependency | 10 |
| | **Total** | | **100** |

## Aturan

- **Jangan mengubah** file apa pun di dalam `src/test/**` atau `.github/**`.
- Jangan mengubah nama kelas, nama field, nama method, maupun signature method — autograder memakainya persis seperti spesifikasi.
- Semua field harus `private` (ini diperiksa oleh tes).
- Gunakan array biasa saja — tanpa `List`, `ArrayList`, atau Collection lainnya.
