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

Diagram kelas adalah **spesifikasi utama** kalian: semua kelas, field, konstruktor, dan signature method yang dibutuhkan sudah didefinisikan di sana. Bacalah dengan teliti.

![Class Diagram](docs/class-diagram.png)

Kelas yang bertanda **«create this class»** belum ada — kalian harus membuatnya sendiri.

### 4 Relasi Kelas dalam Proyek Ini

| Relasi | Di mana | Maknanya di sini |
|---|---|---|
| **Association** | `OrderItem → MenuItem`, `Order → Customer` | Objek menyimpan referensi ke objek lain yang hidup mandiri. |
| **Aggregation** | `Menu ◇→ MenuItem` | `Menu` mengumpulkan objek `MenuItem` yang **dibuat di luar** lalu dimasukkan; mereka tetap bisa ada tanpa menu. |
| **Composition** | `Order ◆→ OrderItem` | `Order` **membuat sendiri** objek `OrderItem` di dalam `addItem(...)`; objek itu tidak bisa ada tanpa pesanannya. |
| **Dependency** | `Cashier ⇢ Order` | `Cashier` hanya **memakai** `Order` sebagai parameter method; tidak pernah menyimpannya di field. |

## Memulai

Repositori ini adalah **template**:

1. Klik tombol hijau **Use this template** (kanan atas) → **Create a new repository**, atur visibilitas ke **Private**, beri nama misalnya `oop-quiz-<nama-kalian>`.
2. Clone repositori **milik kalian**:
   ```bash
   git clone git@github.com:<username-kalian>/oop-quiz-<nama-kalian>.git
   ```
3. Buka proyek di editor (lihat di bawah), implementasikan kelas-kelasnya, lalu commit dan push.
4. Setiap push menjalankan autograder. Buka tab **Actions** di repositori kalian untuk melihat skor. Jika GitHub meminta mengaktifkan workflow saat pertama kali, klik **I understand my workflows, enable them**.

## Membuka Proyek

Ini proyek Maven standar — tanpa konfigurasi tambahan.

**NetBeans**
1. **File → Open Project…**
2. Pilih folder `oop-quiz-...` hasil clone (NetBeans otomatis mengenalinya sebagai proyek Maven) → **Open Project**.
3. Klik kanan proyek → **Test** untuk menjalankan semua tes, atau jalankan satu file tes dari node `Test Packages`.

**Visual Studio Code**
1. Pasang **Extension Pack for Java** (Microsoft) dari panel Extensions.
2. **File → Open Folder…** lalu pilih folder hasil clone.
3. Jalankan tes dari side bar **Testing** (ikon labu), atau lewat terminal:
   ```bash
   mvn test
   ```

## Bagian 1 — Lengkapi Kelas Kerangka (Skeleton)

`MenuItem`, `Customer`, dan `Menu` sudah ada di `src/main/java/id/ac/polinema/oop/`. Ganti setiap `throw new UnsupportedOperationException(...)` dengan implementasi yang benar. Struktur (field, konstruktor, signature) diambil dari diagram kelas; aturan perilakunya:

- **`MenuItem`** — `setPrice` **mengabaikan** harga **negatif** dan mempertahankan harga lama.
- **`Customer`** — `setName` **mengabaikan** nama **null atau kosong (blank)** dan mempertahankan nama lama.
- **`Menu`** *(aggregation)* — array berkapasitas **10**. `addMenuItem` menyimpan item pada indeks `itemCount` lalu menaikkan counter; jika menu penuh, tidak melakukan apa-apa. `findItem` mencocokkan nama secara persis dan mengembalikan `null` jika tidak ditemukan.

**Gunakan array biasa — JANGAN gunakan `List`/`ArrayList`** (Collections adalah materi pertemuan berikutnya).

## Bagian 2 — Buat Kelas Baru

`OrderItem`, `Order`, dan `Cashier` **belum ada**. Buatlah di `src/main/java/id/ac/polinema/oop/` persis seperti yang digambarkan pada diagram kelas. Aturan perilakunya:

- **`OrderItem`** *(association)* — `getSubtotal()` mengembalikan harga item menu × jumlah.
- **`Order`** *(composition, association)* — array berkapasitas **10**, seperti `Menu`. `addItem(MenuItem, int)` **membuat objek `OrderItem` di dalam method** (inilah composition!); jika pesanan penuh, tidak melakukan apa-apa. `getTotal()` menjumlahkan subtotal semua baris. `getFinalTotal()` menerapkan **diskon 10%** jika total **≥ 100000**, selain itu mengembalikan total apa adanya.
- **`Cashier`** *(dependency)* — tanpa field; `Order` hanya parameter. `calculateChange(Order, double)` mengembalikan uang tunai dikurangi total akhir.

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

Jalankan dari NetBeans (**Run Project**), VS Code (**Run** di atas `main`), atau terminal:

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

Total **100 poin**, terbagi ke 12 grup tes kecil (dijalankan lewat GitHub Actions pada setiap push). Uji dulu secara lokal:

```bash
mvn test                              # semuanya
mvn test -Dtest=MenuItemConstructorTest   # satu grup saja
```

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
- Ikuti diagram kelas dengan tepat — nama kelas, nama field, nama method, dan signature dipakai autograder persis seperti yang digambar.
- Semua field harus `private` (ini diperiksa oleh tes).
- Gunakan array biasa saja — tanpa `List`, `ArrayList`, atau Collection lainnya.
