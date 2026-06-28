# AUDIT PROJECT — AsynCode_ThriftHub (Aplikasi Thrift Shop)

> **Catatan penting di awal:** Brief yang kamu kirim menyebut *Hotel Booking System (StayEase)*, tetapi folder yang terhubung berisi project **AsynCode_ThriftHub** — aplikasi toko pakaian bekas/thrift (Java Swing + MySQL). Rubrik penilaian OOP yang kamu lampirkan bersifat umum, jadi audit ini diterapkan ke project ThriftHub yang sebenarnya. Nama-class contoh di brief (User, Hotel, Booking, Payment, UserDAO, dst.) **tidak ada** di project ini — yang ada adalah Produk, Admin, koneksi, dashboard, dll. Semua bukti di bawah merujuk ke file asli kamu.

Peran saya di sini: **dosen penguji OOP Java**. Penilaian disusun apa adanya, dengan bukti baris kode.

---

## 1. Analisis Struktur Project

Project menggunakan struktur NetBeans (Ant), source di `src/`. Package yang ada:

| Package | Isi | Fungsi sebenarnya |
|---------|-----|-------------------|
| `asyncode_thrifthub` | `AsynCode_ThriftHub` (main), `Session`, `Logout`, `IndexPage`, `Keranjang`, `Profile`, `SplashScreen`, `Frame2` | Campuran: titik masuk aplikasi (main), session manager, utilitas logout, dan beberapa View. Tidak fokus pada satu tanggung jawab. |
| `form` | `dashboard` (login), `registrasi` (register), `Beranda`, `AdminPenjual`, `TambahProduk`, `DeskripsiProduk`, `DeskkripsiProduk`, `CardProduct`, `koneksi`, `testKoneksi`, `frame2` | Lapisan View (JFrame Swing) + **logika database tertanam langsung di sini**. Juga berisi duplikat class koneksi. |
| `model` | `Produk`, `ProdukIndividu`, `ProdukBundle`, `Admin` | Lapisan Model (data + OOP murni: inheritance & interface). Sayangnya **hampir tidak dipakai** oleh alur aplikasi. |
| `interfaces` | `KelolaProduk` | Berisi satu interface kontrak kelola produk. |
| `koneksi` | `koneksi` | Kelas koneksi JDBC ke MySQL. **Terduplikasi** dengan `form/koneksi.java`. |
| `img` | resource gambar (.png) | Aset UI (logo, ikon, foto produk contoh). |

**Penilaian struktur:** Niat memisahkan Model–View–Util sudah terlihat (ada package `model`, `interfaces`, `koneksi`). **Namun lapisan DAO yang kamu klaim TIDAK ADA** — tidak ada satu pun class `*DAO`. Semua query SQL ditulis langsung di dalam JFrame (lihat `dashboard`, `registrasi`, `TambahProduk`, `AdminPenjual`, `Beranda`). Ini pelanggaran *Separation of Concerns* yang utama (dibahas di Bagian 6).

---

## 2. Analisis Class (Seluruh Class)

| Class | Package | Fungsi |
|-------|---------|--------|
| `AsynCode_ThriftHub` | asyncode_thrifthub | Class `main`. Menjalankan `dashboard` (login) saat start. |
| `Session` | asyncode_thrifthub | Session manager. Menyimpan `emailLogin` & `roleLogin` sebagai **public static** (lihat catatan encapsulation). |
| `Logout` | asyncode_thrifthub | Utility: konfirmasi logout, reset session, buka `dashboard`. |
| `IndexPage` | asyncode_thrifthub | Landing page ("Mulai Jelajahi"/"Login"). **Tidak dipakai** karena main langsung ke `dashboard`. |
| `Keranjang` | asyncode_thrifthub | View keranjang belanja. **Data dummy**, tidak terhubung DB. |
| `Profile` | asyncode_thrifthub | View profil user. Punya 2 konstruktor (overload). Data dilempar via parameter, tidak query DB. |
| `SplashScreen` | asyncode_thrifthub | Splash screen pembuka (View). |
| `Frame2` / `frame2` | asyncode_thrifthub / form | Frame sisa template NetBeans — kemungkinan besar dead code. |
| `dashboard` | form | **Form LOGIN** (penamaan menyesatkan). Query `SELECT` ke tabel users. |
| `registrasi` | form | **Form REGISTER**. Query `INSERT` ke tabel users. |
| `Beranda` | form | Halaman utama/katalog. Tampil produk, role-aware (admin vs user). |
| `AdminPenjual` | form | Dashboard penjual/admin: list produk + Edit + Hapus (soft delete) + ganti banner toko. |
| `TambahProduk` | form | Form tambah/edit produk. INSERT & UPDATE tabel produk. 2 konstruktor (overload). |
| `DeskripsiProduk` | form | Frame detail produk — **kosong** (hanya panel, tanpa logika). |
| `DeskkripsiProduk` | form | Duplikat/typo dari `DeskripsiProduk` — dead code. |
| `CardProduct` | form | Komponen reusable: kartu produk (extends `JPanel`). Bagus secara desain, tapi tidak terpakai (frame lain membuat card manual). |
| `koneksi` | koneksi | Koneksi JDBC (registerDriver + getConnection). |
| `koneksi` | form | **Duplikat** koneksi JDBC. Frame `form.*` memakai yang ini. |
| `testKoneksi` | form | Class **kosong** — dead code. |
| `Produk` | model | Superclass produk: `idProduk`, `namaProduk`, `harga` (private) + getter + `tampilInfo()`. |
| `ProdukIndividu` | model | Subclass `Produk`. Override `tampilInfo()`, panggil `super(...)`. |
| `ProdukBundle` | model | Subclass `Produk`. Override `tampilInfo()`, panggil `super(...)`. |
| `Admin` | model | `implements KelolaProduk`. Override `tambahProduk()`/`hapusProduk()` (hanya `System.out.println`). |
| `KelolaProduk` | interfaces | Interface kontrak: `tambahProduk()`, `hapusProduk()`. |

---

## 3. Cek Kriteria Penilaian OOP

### 3.1 Interface — ✅ ADA (tapi kurang lengkap)

- **Nama:** `KelolaProduk`
- **Lokasi:** `src/interfaces/KelolaProduk.java`
- **Diimplementasikan oleh:** `model/Admin.java` (`public class Admin implements KelolaProduk`)

```java
public interface KelolaProduk {
      void tambahProduk();
      void hapusProduk();
}
```

Cek sub-kriteria rubrik:

| Komponen interface | Status | Bukti |
|--------------------|--------|-------|
| Abstract method | ✅ | `tambahProduk()`, `hapusProduk()` |
| Default method | ❌ | tidak ada |
| Static method | ❌ | tidak ada |

**Solusi (wajib ditambah agar penuh):** lengkapi interface seperti ini —

```java
package interfaces;

public interface KelolaProduk {
    // abstract method
    void tambahProduk();
    void hapusProduk();
    void updateProduk(int id);   // tambahan agar kontrak CRUD lengkap

    // default method (Java 8+)
    default void tampilkanMenu() {
        System.out.println("=== Menu Kelola Produk ===");
        System.out.println("1. Tambah  2. Update  3. Hapus");
    }

    // static method
    static String role() {
        return "ADMIN_PENGELOLA_PRODUK";
    }
}
```

> Catatan keras: interface ini saat ini **dekoratif**. `Admin.tambahProduk()` hanya `System.out.println("Produk ditambahkan")`, sedangkan penambahan produk yang sebenarnya terjadi di `TambahProduk.TambahActionPerformed()` tanpa menyentuh interface. Agar bernilai, interface harus benar-benar dipakai oleh lapisan yang menjalankan CRUD (idealnya DAO).

---

### 3.2 Implements — ✅ ADA

- `model/Admin.java` → `class Admin implements KelolaProduk`
- Hubungan: `Admin` wajib mengimplementasi `tambahProduk()` & `hapusProduk()` (keduanya ada `@Override`).

**Saran peningkatan (sangat disarankan):** buat DAO yang juga `implements` interface, sehingga implements terhubung ke logika nyata. Contoh ada di Bagian 6 (`ProdukDAO implements KelolaProduk`).

---

### 3.3 Inheritance — ✅ ADA (1 superclass, 2 subclass)

```
Produk  (superclass)
│
├── ProdukIndividu   (extends Produk)
└── ProdukBundle     (extends Produk)
```

Bukti: `src/model/Produk.java`, `ProdukIndividu.java` (`extends Produk`), `ProdukBundle.java` (`extends Produk`). Kriteria rubrik (min 1 super + 2 sub) **terpenuhi**.

Tambahan: seluruh JFrame `extends javax.swing.JFrame` dan `CardProduct extends JPanel` (inheritance dari library) — sah, tapi nilai utama tetap pada hierarki `Produk`.

> Kelemahan: hierarki `Produk` **tidak dipakai** dalam alur data. Saat menampilkan produk (`AdminPenjual.loadProduk`, `TambahProduk.loadHistoryProduk`) data dibaca langsung dari `ResultSet` ke komponen Swing, bukan dipetakan menjadi objek `Produk`/`ProdukIndividu`/`ProdukBundle`. Hubungkan agar inheritance benar-benar berguna (lihat Bagian 4).

---

### 3.4 Method Overloading — ✅ ADA

Constructor overloading ditemukan di beberapa class:

- `TambahProduk` (`src/form/TambahProduk.java`):
  ```java
  public TambahProduk() { ... }            // mode tambah
  public TambahProduk(int idProduk) { ... } // mode edit
  ```
- `Profile` (`src/asyncode_thrifthub/Profile.java`):
  ```java
  public Profile() { ... }
  public Profile(String namaUser, String emailUser, String roleUser) { ... }
  ```

Kriteria **terpenuhi**. Untuk poin tambahan, kamu bisa menambah method overloading bertema domain, mis. di DAO:

```java
List<Produk> cariProduk();                 // semua
List<Produk> cariProduk(String kategori);  // by kategori
Produk      cariProduk(int idProduk);      // by id
```

---

### 3.5 Method Overriding — ✅ ADA (banyak)

| Lokasi | Method di-override |
|--------|--------------------|
| `model/ProdukIndividu.java` | `tampilInfo()` (override `Produk.tampilInfo()`) |
| `model/ProdukBundle.java` | `tampilInfo()` |
| `model/Admin.java` | `tambahProduk()`, `hapusProduk()` (dari interface) |
| `form/AdminPenjual.java` (baris ~270) | `paintComponent(Graphics g)` pada anonymous `JButton` (menggambar titik tiga ⋮) |
| `asyncode_thrifthub/Keranjang.java` (baris ~46) | `isCellEditable(int,int)` pada `DefaultTableModel` |

Kriteria **terpenuhi** dan ini termasuk yang terbaik di project.

---

### 3.6 Constructor buatan sendiri — ✅ ADA

- `Produk(int, String, double)` — `model/Produk.java`
- `TambahProduk()` & `TambahProduk(int)` — `form/TambahProduk.java`
- `Profile()` & `Profile(String,String,String)` — `Profile.java`
- `Keranjang()`, `dashboard()`, `registrasi()`, `AdminPenjual()`, `Beranda()`, dll.

Kriteria **terpenuhi**.

---

### 3.7 Encapsulation — ✅ SEBAGIAN

- **Baik:** `model/Produk.java` → field `private int idProduk; private String namaProduk; private double harga;` + getter (`getIdProduk`, `getNamaProduk`, `getHarga`).
- **Kurang:** belum ada **setter**. Untuk rubrik encapsulation yang utuh, tambahkan setter dengan validasi:

```java
public void setHarga(double harga) {
    if (harga < 0) throw new IllegalArgumentException("Harga tidak boleh negatif");
    this.harga = harga;
}
```

- **Pelanggaran:** `Session` memakai `public static String emailLogin; public static String roleLogin;` — global mutable state, kebalikan dari encapsulation. Sebaiknya private + getter/setter:

```java
public class Session {
    private static String emailLogin;
    private static String roleLogin;
    public static String getEmailLogin() { return emailLogin; }
    public static void setEmailLogin(String e) { emailLogin = e; }
    public static String getRoleLogin() { return roleLogin; }
    public static void setRoleLogin(String r) { roleLogin = r; }
}
```

---

### 3.8 Keyword `super(...)` pada Constructor — ✅ ADA

- `ProdukIndividu`: `super(id, nama, harga);`
- `ProdukBundle`: `super(id, nama, harga);`

Kriteria **terpenuhi** (`src/model/ProdukIndividu.java` & `ProdukBundle.java`).

---

### 3.9 `super.method()` pada Method — ✅ ADA

- `form/AdminPenjual.java`, di dalam override `paintComponent`:
  ```java
  @Override
  protected void paintComponent(java.awt.Graphics g) {
      super.paintComponent(g);   // <-- super.method()
      g.setColor(Color.BLACK);
      g.fillOval(10, 5, 3, 3); ...
  }
  ```

Kriteria **terpenuhi**. Bila ingin contoh di domain Model:

```java
@Override
public String tampilInfo() {
    return "Bundle → " + super.tampilInfo(); // pakai info dari Produk
}
```

---

### 3.10 `instanceof` & Casting — ❌ TIDAK ADA

Tidak ada satu pun `instanceof` atau casting objek di seluruh project. Pengecekan role saat ini memakai perbandingan String (`Beranda`: `"admin".equalsIgnoreCase(Session.roleLogin)`), bukan polimorfisme.

**Solusi sesuai project (gunakan hierarki Produk yang sudah ada):**

```java
Produk p = new ProdukBundle(1, "Paket Hemat", 200000);

if (p instanceof ProdukBundle) {
    ProdukBundle bundle = (ProdukBundle) p;   // casting
    System.out.println("Ini bundle: " + bundle.tampilInfo());
} else if (p instanceof ProdukIndividu) {
    ProdukIndividu ind = (ProdukIndividu) p;  // casting
    System.out.println("Ini satuan: " + ind.tampilInfo());
}
```

Atau membuat hierarki user `User → Admin/Customer` lalu:

```java
if (userLogin instanceof Admin) {
    ((Admin) userLogin).tambahProduk();
}
```

---

### 3.11 CRUD — ⚠️ SEBAGIAN (tidak lengkap)

| Entitas | CREATE | READ | UPDATE | DELETE |
|---------|--------|------|--------|--------|
| **users** | ✅ `registrasi` (INSERT) | ✅ `dashboard` login (SELECT), `AdminPenjual` (SELECT nama/banner) | ✅ `AdminPenjual.EditMouseClicked` (UPDATE banner_toko) | ❌ tidak ada |
| **produk** | ✅ `TambahProduk` (INSERT) | ✅ `AdminPenjual.loadProduk`, `TambahProduk.loadHistoryProduk` (SELECT) | ✅ `TambahProduk` mode edit (UPDATE) | ⚠️ *soft delete* `AdminPenjual.hapusProduk` (UPDATE status='hapus') — **bukan DELETE asli** |
| **kategori** | ❌ | ✅ `TambahProduk.loadKategori` (SELECT) | ❌ | ❌ |
| **keranjang / transaksi / pembayaran** | ❌ | ❌ | ❌ | ❌ — `Keranjang` & checkout hanya **data dummy**, tidak tersimpan DB |

**Kesimpulan CRUD:** Inti CRUD produk (Create/Read/Update) sudah jalan dan memakai `PreparedStatement` (aman dari SQL Injection — ini nilai plus). Namun:

- Tidak ada **DELETE** sungguhan (`DELETE FROM`). Soft delete boleh, tapi jelaskan ini ke penguji; jika rubrik menuntut DELETE, tambahkan satu query DELETE asli.
- **Keranjang & checkout tidak menyentuh database** sama sekali (lihat `Keranjang.java`: `tambahItemKeKeranjang("1","Jaket Vintage Denim",...)` adalah dummy). Untuk toko, transaksi yang tidak tersimpan adalah kelemahan besar.
- CRUD belum dibungkus DAO; tersebar di JFrame.

---

## 4. Analisis Database & Sinkronisasi

**Konfigurasi (di `koneksi/koneksi.java` & `form/koneksi.java`):**
- Driver: `com.mysql.cj.jdbc.Driver` (mysql-connector 8.0.22)
- URL: `jdbc:mysql://localhost:3306/toko_pakaian`
- User: `root`, Password: `""` (kosong)

**Tabel yang terdeteksi dari query:**
- `users` → kolom: `alamat_email`, `password`, `role`, `nama`, `banner_toko`
- `produk` → kolom: `id_produk`, `nama_produk`, `harga`, `kategori`, `deskripsi`, `gambar`, `status`
- `kategori` → kolom: `nama_kategori`

**Cek sinkron Database → Model → DAO → Frame:**

```
tabel produk  →  model/Produk.java  →  (DAO tidak ada)  →  TambahProduk / AdminPenjual / Beranda
```

Temuan **tidak sinkron**:

1. **Model tidak cocok dengan tabel.** `Produk` hanya punya `idProduk, namaProduk, harga`. Tabel `produk` punya `kategori, deskripsi, gambar, status` yang **tidak ada** di model. Field DB tidak terpetakan ke objek.
2. **DAO hilang.** Rantai yang benar `DB → Model → DAO → Frame` putus di DAO. Frame bicara langsung ke DB.
3. **Tipe data harga.** Di DB & kode dibaca/ditulis sebagai String (`rs.getString("harga")`, `pst.setString(2, harga)`), padahal `Produk.harga` bertipe `double`. Tidak konsisten.
4. **Keranjang tanpa tabel.** Tidak ada tabel transaksi/keranjang; data keranjang dibuat di memori.

**Saran:** samakan field model dengan tabel (`Produk { id, nama, harga, kategori, deskripsi, gambar, status }`), buat `ProdukDAO` yang mengembalikan `List<Produk>`, dan frame cukup memanggil DAO.

---

## 5. Analisis GUI (Seluruh JFrame)

| Frame | Fungsi | Status |
|-------|--------|--------|
| `SplashScreen` | Layar pembuka aplikasi | Ada |
| `IndexPage` | Landing page (tombol Jelajahi & Login) | Ada, tapi **orphan** (main tidak melewatinya) |
| `dashboard` | **Login** (email, sandi, tampilkan sandi, link Sign up) | Berfungsi |
| `registrasi` | **Register** (email, sandi, pilih role User/Admin) | Berfungsi |
| `Beranda` | Katalog produk, role-aware (admin lihat tombol toko/Edit banner) | Berfungsi |
| `AdminPenjual` | Kelola toko: grid produk + menu ⋮ (Edit/Hapus), filter kategori, ganti banner, logout | Berfungsi |
| `TambahProduk` | Form tambah/edit produk + browse gambar + history produk | Berfungsi |
| `DeskripsiProduk` | Detail produk | **Kosong** (belum diisi) |
| `DeskkripsiProduk` | Duplikat/typo | Dead code |
| `Keranjang` | Keranjang belanja + hapus item + checkout | Berfungsi secara UI, **data dummy** |
| `Profile` | Tampil nama/email/role + tombol keluar | Berfungsi (data via parameter) |
| `CardProduct` | Komponen kartu produk reusable | Ada tapi tidak dipakai |
| `Frame2`/`frame2` | Sisa template | Dead code |

**Navigasi:** `main` → `dashboard` (login) → `Beranda`. Dari `Beranda` admin → `AdminPenjual` → `TambahProduk`. Logout → kembali `dashboard`.

Masalah navigasi: ada **banyak `main()`** (hampir tiap frame punya), `IndexPage`/`SplashScreen` tidak masuk alur utama, dan `DeskripsiProduk`/`Keranjang`/`Profile` belum tersambung mulus dari `Beranda` (mis. Profile/Keranjang dibuka dengan data dummy). Alur perlu dirapikan jadi satu jalur jelas.

---

## 6. Analisis Arsitektur

Target: Model – DAO – View – Util.

| Lapisan | Seharusnya | Kenyataan |
|---------|-----------|-----------|
| Model | POJO data | Ada (`Produk` dkk) tapi tak terpakai & tak sinkron DB |
| DAO | Akses DB terpusat | **TIDAK ADA** |
| View | JFrame Swing | Ada, **tapi merangkap DAO** (SQL di dalam frame) |
| Util | koneksi, session, logout | Ada (`koneksi`, `Session`, `Logout`) tapi `koneksi` terduplikasi |

**Pelanggaran Separation of Concerns (utama):** logika SQL bercampur di View. Contoh: `dashboard.jButton1ActionPerformed` melakukan validasi + buka koneksi + query SELECT + set session + pindah frame, semuanya dalam satu method.

**Class yang sebaiknya dipindah/dirapikan:**
- `form/koneksi.java` → hapus, pakai satu `koneksi` saja (atau pindah ke package `util`).
- `Keranjang`, `Profile`, `Logout`, `Session` di `asyncode_thrifthub` → pindahkan View ke `form`/`view`, dan `Session`/`Logout` ke `util`.
- `testKoneksi`, `DeskkripsiProduk`, `Frame2/frame2` → hapus (dead code).

**Saran refactoring (contoh DAO yang menyatukan interface + Model + CRUD):**

```java
package dao;

import interfaces.KelolaProduk;
import model.Produk;
import java.sql.*;
import java.util.*;
import koneksi.koneksi;

public class ProdukDAO implements KelolaProduk {

    @Override
    public void tambahProduk() { /* ... */ }
    @Override
    public void hapusProduk() { /* ... */ }

    public void simpan(Produk p) throws SQLException {
        String sql = "INSERT INTO produk(nama_produk,harga,kategori,deskripsi,gambar,status) VALUES(?,?,?,?,?, 'aktif')";
        try (Connection c = koneksi.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNamaProduk());
            ps.setDouble(2, p.getHarga());
            // ...
            ps.executeUpdate();
        }
    }

    public List<Produk> ambilSemua() throws SQLException { /* SELECT → List<Produk> */ }
    public void hapus(int id) throws SQLException { /* UPDATE status='hapus' WHERE id_produk=? */ }
}
```

Lalu `TambahProduk`/`AdminPenjual` cukup memanggil `new ProdukDAO().simpan(produk)` — View bersih, DAO terpusat, interface terpakai.

---

## 7. Analisis Clean Code (Temuan + Solusi)

| # | Temuan | Lokasi | Dampak | Solusi |
|---|--------|--------|--------|--------|
| 1 | **Password plaintext** | `registrasi` (`ps.setString(2, password); // langsung tanpa hash`) & `dashboard` (SELECT bandingkan plaintext) | Keamanan kritis | Hash (BCrypt/`MessageDigest` SHA-256+salt). `MessageDigest` sudah di-import tapi tak dipakai. |
| 2 | **Class koneksi duplikat** | `koneksi/koneksi.java` & `form/koneksi.java` | Duplicate code, inkonsistensi | Sisakan satu, hapus lainnya. |
| 3 | **SQL di dalam View** | `dashboard`, `registrasi`, `TambahProduk`, `AdminPenjual`, `Beranda` | Pelanggaran SoC | Pindahkan ke DAO. |
| 4 | **Resource tidak ditutup** | `TambahProduk.loadDataEdit/loadHistoryProduk/loadKategori/TambahActionPerformed`, `AdminPenjual.loadProduk/hapusProduk` (Connection/PreparedStatement/ResultSet dibiarkan terbuka) | Kebocoran koneksi | Pakai try-with-resources seperti di `dashboard`/`registrasi`. |
| 5 | **Penamaan class tidak sesuai konvensi** | `koneksi`, `dashboard`, `registrasi`, `testKoneksi`, `frame2` (huruf kecil) | Tidak standar Java (harus PascalCase) | Rename → `Koneksi`, `LoginFrame`, `RegisterFrame`, dst. |
| 6 | **Dead code** | `testKoneksi` (kosong), `DeskripsiProduk` (kosong), `DeskkripsiProduk`, `Frame2/frame2`, `CardProduct` (tak dipakai), `IndexPage` (orphan) | Membingungkan penguji | Hapus / sambungkan. |
| 7 | **Magic string** | `"aktif"`, `"hapus"`, `"Admin"`, `"User"`, kategori (`"Kaos"`,`"Celana"`,...) tersebar | Rentan typo | Jadikan konstanta/enum (`Status.AKTIF`, `Role.ADMIN`). |
| 8 | **Magic number** | ukuran/bounds di banyak tempat (`152,195`, `901,220`, `170,240`, dll.) | Sulit dipelihara | Sebagian wajar untuk layout; ekstrak yang berulang ke konstanta. |
| 9 | **`catch (Exception e)` generik + tampilkan `e.getMessage()` ke user** | `TambahProduk`, `AdminPenjual`, `Beranda` | Bocor detail teknis, sulit debug | Tangkap `SQLException` spesifik, log internal, pesan user yang ramah. |
| 10 | **`e.printStackTrace()`** | `TambahProduk.loadHistoryProduk/loadKategori` | Tidak ada logging proper | Gunakan `Logger`. |
| 11 | **Harga sebagai String** | tabel & kode produk | Tidak bisa hitung/validasi numerik | Simpan numerik (INT/DECIMAL), parse + validasi. |
| 12 | **Validasi input minim** | `TambahProduk.TambahActionPerformed` (nama/harga/gambar tidak dicek kosong; harga tidak dicek numerik) | Data kotor masuk DB | Validasi sebelum INSERT/UPDATE. |
| 13 | **Path gambar absolut disimpan ke DB** | `TambahProduk` (`img.setText(absolutePath)`) | Tidak portabel antar-komputer | Simpan ke folder project (relative) atau BLOB. |
| 14 | **Kredensial DB hardcoded** | kedua `koneksi` (`root`, password kosong) | Keamanan & portabilitas | Pindah ke file `db.properties`. |
| 15 | **Kode duplikat di event handler** | `IndexPage.jButton2ActionPerformed` membuat `dashboard` dua kali; blok Nimbus L&F dobel di `registrasi.main` | Code smell | Bersihkan. |

**Hal positif (akui di presentasi):** seluruh akses DB konsisten memakai **`PreparedStatement` dengan parameter `?`** → project ini **aman dari SQL Injection**. Validasi login/register (email mengandung `@`, sandi ≥ 8 karakter, field wajib) juga sudah ada.

---

## 8. Penilaian Presentasi

| Aspek | Status | Catatan |
|-------|--------|---------|
| Fitur | ⚠️ Sebagian | Login, register, CRUD produk, banner, filter kategori jalan. Keranjang/checkout & profile masih dummy. |
| GUI | ✅ Konsisten | Tema hijau (#2C4A3B) konsisten, kartu produk rapi. |
| Navigasi | ⚠️ Membingungkan | Banyak `main()`, beberapa frame orphan, beberapa pakai data dummy. |
| Tampilan | ✅ Menarik | Layout modern, ada banner & grid produk. |

**Validasi input per form:**

| Form | Validasi? | Detail |
|------|-----------|--------|
| Login (`dashboard`) | ✅ | field wajib, email `@`, sandi ≥ 8 |
| Register (`registrasi`) | ✅ | field wajib, email `@`, sandi ≥ 8, role; **tapi tidak cek email duplikat** |
| Tambah/Edit Produk | ❌ | tidak ada validasi kosong/numerik |
| Hapus produk | ✅ | ada konfirmasi YES/NO |
| Keranjang | ⚠️ | konfirmasi hapus & cek kosong saat checkout, tapi data dummy |
| CRUD User (selain register) | ❌ | tidak ada manajemen user |

---

## 9. Penilaian Project (Skor)

| Aspek | Nilai | Alasan singkat |
|-------|-------|----------------|
| OOP | **78/100** | Interface, inheritance, overloading, overriding, super, super.method, encapsulation, constructor semua ADA. Minus: `instanceof`/casting tidak ada, interface tanpa default/static method, OOP tidak terhubung ke alur nyata. |
| Database | **62/100** | Koneksi & CRUD produk jalan, PreparedStatement aman. Minus: tanpa DAO, model tidak sinkron, tanpa tabel transaksi, kredensial hardcoded. |
| GUI | **80/100** | Konsisten & menarik. Minus: frame kosong/orphan, navigasi belum mulus. |
| Struktur Project | **60/100** | Ada model/interfaces/koneksi, tapi DAO hilang, package campur, banyak dead code, koneksi duplikat. |
| Clean Code | **55/100** | PreparedStatement & validasi login plus. Minus: password plaintext, resource leak, penamaan non-standar, magic value, dead code. |
| Presentasi | **70/100** | Demo inti meyakinkan, tapi fitur dummy bisa jadi sasaran pertanyaan. |

### Total Nilai

```
67 / 100
```

**Alasan:** Fondasi OOP untuk lulus rubrik sebenarnya **hampir lengkap** (8 dari ~9 kriteria ada). Yang menahan nilai adalah: (1) konsep OOP belum tersambung ke aplikasi nyata (interface/inheritance hanya "pajangan"), (2) klaim DAO Pattern tidak terbukti di kode, (3) isu clean code & keamanan (password plaintext, resource leak), dan (4) beberapa fitur masih dummy. Dengan perbaikan prioritas merah di bawah, nilai bisa naik ke kisaran 80+.

---

## 10. Checklist Akhir

| Kriteria | Status | Lokasi File | Catatan |
|----------|--------|-------------|---------|
| Interface | ✅ | `interfaces/KelolaProduk.java` | Ada, tapi tanpa default & static method |
| Implements | ✅ | `model/Admin.java` | `Admin implements KelolaProduk` |
| Inheritance | ✅ | `model/Produk` ← `ProdukIndividu`, `ProdukBundle` | 1 super + 2 sub terpenuhi |
| Overloading | ✅ | `form/TambahProduk.java`, `Profile.java` | Constructor overload |
| Overriding | ✅ | `ProdukIndividu/Bundle`, `Admin`, `AdminPenjual` (paintComponent), `Keranjang` (isCellEditable) | Banyak |
| Constructor | ✅ | `Produk.java` & banyak frame | Constructor berparameter |
| Encapsulation | ⚠️ | `model/Produk.java` (privat+getter) ; `Session` melanggar | Tambah setter; perbaiki Session |
| super() | ✅ | `ProdukIndividu.java`, `ProdukBundle.java` | `super(id,nama,harga)` |
| super.method() | ✅ | `form/AdminPenjual.java` | `super.paintComponent(g)` |
| instanceof | ❌ | — | Belum ada; lihat contoh Bagian 3.10 |
| CRUD | ⚠️ | `registrasi`, `dashboard`, `TambahProduk`, `AdminPenjual` | C/R/U ada; DELETE asli & transaksi belum |

---

## 11. Prioritas Perbaikan (Roadmap)

### 🔴 Wajib Ditambahkan Sebelum Presentasi
1. **Tambahkan `instanceof` + casting** (satu-satunya kriteria OOP yang ❌). Pakai contoh `Produk`/`ProdukBundle` di Bagian 3.10 — cukup beberapa baris.
2. **Lengkapi interface `KelolaProduk`** dengan minimal satu **default method** dan satu **static method** (Bagian 3.1).
3. **Sambungkan OOP ke aplikasi nyata**: buat minimal `ProdukDAO implements KelolaProduk` yang dipakai `TambahProduk`/`AdminPenjual`. Ini sekaligus membuktikan klaim "DAO Pattern".
4. **Perbaiki password plaintext** (hash saat register & saat login) — pertanyaan keamanan hampir pasti ditanya.

### 🟠 Sangat Disarankan
5. Hilangkan duplikat `koneksi` (sisakan satu, idealnya di package `util`).
6. Tutup semua resource JDBC dengan try-with-resources (hindari leak).
7. Sinkronkan `model/Produk` dengan tabel `produk` (tambah field kategori/deskripsi/gambar/status; harga numerik).
8. Sambungkan **Keranjang & checkout ke database** (buat tabel `transaksi`/`detail_transaksi`) — agar fitur toko utuh.
9. Tambah validasi di form Tambah Produk (kosong + harga numerik) dan cek email duplikat saat register.
10. Tambah setter + perbaiki `Session` (private + getter/setter).

### 🟢 Opsional (agar terlihat profesional)
11. Hapus dead code (`testKoneksi`, `DeskkripsiProduk`, `Frame2/frame2`, `DeskripsiProduk` kosong) atau lengkapi.
12. Rename class ke PascalCase (`Koneksi`, `LoginFrame`, `RegisterFrame`).
13. Pindahkan kredensial DB ke `db.properties`.
14. Satu titik masuk (`main`) yang jelas: SplashScreen → IndexPage → Login.
15. Ekstrak magic string ke enum/konstanta (`Status`, `Role`, `Kategori`).

---

## 12. Simulasi Dosen Penguji (30+ Pertanyaan & Jawaban Terbaik)

### A. Database
1. **Q: Database apa yang dipakai dan bagaimana strukturnya?**
   A: MySQL `toko_pakaian`, tabel utama `users`, `produk`, `kategori`. `produk` menyimpan id, nama, harga, kategori, deskripsi, gambar, status.
2. **Q: Bagaimana relasi antar tabel?**
   A: Saat ini relasi belum di-enforce dengan FK; `produk.kategori` merujuk `kategori.nama_kategori` secara logis. Rencana: ubah ke `kategori_id` sebagai foreign key.
3. **Q: Mengapa password disimpan tanpa enkripsi?**
   A: Itu kelemahan yang saya sadari; rencana perbaikan memakai hashing SHA-256 + salt (atau BCrypt) saat register dan membandingkan hash saat login.
4. **Q: Apa fungsi kolom `status` pada produk?**
   A: Untuk *soft delete*: 'aktif' tampil di beranda, 'hapus' disembunyikan tanpa benar-benar menghapus baris.
5. **Q: Kenapa harga disimpan sebagai teks?**
   A: Kekurangan implementasi; seharusnya DECIMAL/INT agar bisa dihitung & divalidasi. Akan saya ubah.

### B. OOP
6. **Q: Tunjukkan penerapan inheritance.**
   A: `Produk` superclass; `ProdukIndividu` & `ProdukBundle` extends `Produk` dan override `tampilInfo()`.
7. **Q: Di mana interface dan apa isinya?**
   A: `interfaces/KelolaProduk` dengan method `tambahProduk()` & `hapusProduk()`, diimplementasi `Admin`.
8. **Q: Apa beda overloading dan overriding di project Anda?**
   A: Overloading = nama sama beda parameter, contoh konstruktor `TambahProduk()` vs `TambahProduk(int)`. Overriding = method subclass menimpa superclass, contoh `tampilInfo()` di `ProdukBundle`.
9. **Q: Buktikan penggunaan `super`.**
   A: `super(id,nama,harga)` di konstruktor subclass, dan `super.paintComponent(g)` di `AdminPenjual`.
10. **Q: Bagaimana encapsulation diterapkan?**
    A: Field `Produk` privat dengan getter. (Jujur: setter belum ada & `Session` masih public — sedang diperbaiki.)
11. **Q: Apakah ada polimorfisme?**
    A: Ya, runtime polymorphism via override `tampilInfo()`; `Produk p = new ProdukBundle(...)` memanggil versi bundle.
12. **Q: Mengapa interface belum punya default/static method?**
    A: Belum sempat; saya akan tambah `tampilkanMenu()` (default) dan `role()` (static).
13. **Q: Apakah Anda memakai `instanceof`?**
    A: Belum di versi ini; rencana memakai untuk membedakan `ProdukIndividu`/`ProdukBundle` dan role user (sudah saya siapkan contohnya).
14. **Q: Abstract class atau interface — kenapa pilih interface?**
    A: Karena `KelolaProduk` hanya kontrak perilaku tanpa state. Bila perlu atribut bersama, saya akan pakai abstract class (mis. `User`).

### C. GUI
15. **Q: Ada berapa JFrame dan apa fungsinya?**
    A: Login, Register, Beranda, AdminPenjual, TambahProduk, Keranjang, Profile, SplashScreen, IndexPage (+ beberapa sisa template yang akan dibersihkan).
16. **Q: Bagaimana menampilkan produk secara dinamis?**
    A: Query SELECT lalu loop `ResultSet` membuat `JPanel` kartu (gambar, nama, harga) dan menambahkannya ke panel grid.
17. **Q: Bagaimana membedakan tampilan admin dan user?**
    A: `Beranda` cek `Session.roleLogin`; bila admin, tombol toko & Edit banner ditampilkan.
18. **Q: Bagaimana upload gambar produk?**
    A: `JFileChooser` mengambil path file, ditampilkan ke preview, dan path disimpan ke DB. (Akan saya ubah ke path relatif agar portabel.)

### D. CRUD
19. **Q: Tunjukkan alur Create produk.**
    A: `TambahProduk` ambil input → INSERT via `PreparedStatement` → pesan sukses → kembali ke `AdminPenjual`.
20. **Q: Bagaimana Update dibedakan dari Create?**
    A: Flag `idEdit`: -1 = INSERT, selain itu UPDATE WHERE id_produk.
21. **Q: Bagaimana Delete bekerja?**
    A: Soft delete — UPDATE status='hapus'. Saya bisa tambahkan DELETE asli bila diminta.
22. **Q: Apakah keranjang tersimpan di DB?**
    A: Belum, masih dummy; rencana membuat tabel transaksi.
23. **Q: Bagaimana mencegah SQL Injection?**
    A: Semua query memakai `PreparedStatement` dengan parameter `?`, tidak ada konkatenasi string input.

### E. JDBC
24. **Q: Langkah koneksi JDBC?**
    A: Register driver `com.mysql.cj.jdbc.Driver`, `DriverManager.getConnection(url,user,pass)`, buat `PreparedStatement`, eksekusi, proses `ResultSet`, tutup.
25. **Q: Beda `executeQuery` dan `executeUpdate`?**
    A: `executeQuery` untuk SELECT (mengembalikan `ResultSet`); `executeUpdate` untuk INSERT/UPDATE/DELETE (mengembalikan jumlah baris).
26. **Q: Kenapa ada dua class koneksi?**
    A: Itu kesalahan duplikasi; akan saya satukan.
27. **Q: Bagaimana menutup koneksi dengan benar?**
    A: Idealnya try-with-resources. Di login/register sudah; di beberapa method produk belum dan akan diperbaiki.

### F. Arsitektur
28. **Q: Pola arsitektur apa yang dipakai?**
    A: Niatnya MVC + DAO. Saat ini Model & Util ada, tapi DAO belum dipisah — SQL masih di View. Ini fokus refactor saya.
29. **Q: Apa itu Separation of Concerns dan apakah project ini sudah?**
    A: Pemisahan tanggung jawab tiap lapisan. Belum sepenuhnya; SQL di JFrame melanggarnya — saya akan pindah ke DAO.

### G. Algoritma
30. **Q: Bagaimana menghitung jumlah baris grid produk?**
    A: `Math.ceil(jumlahProduk / 4.0)` untuk 4 kartu per baris, lalu tinggi panel = baris × tinggi kartu.
31. **Q: Bagaimana filter kategori bekerja?**
    A: Bila kategori null → SELECT semua; bila ada → tambahkan `WHERE kategori=?` dengan parameter.
32. **Q: Bagaimana menghitung total keranjang?**
    A: Setiap item subtotal = harga × jumlah, diakumulasi ke `totalHarga`; saat hapus dikurangi subtotal baris.

### H. Clean Code
33. **Q: Code smell apa yang Anda sadari?**
    A: Password plaintext, koneksi duplikat, SQL di View, resource tak ditutup, penamaan class huruf kecil, dead code, magic value.
34. **Q: Kenapa nama class huruf kecil?**
    A: Tidak sesuai konvensi Java; seharusnya PascalCase. Akan saya rename.

### I. Presentasi
35. **Q: Bagian mana yang paling Anda banggakan?**
    A: Keamanan query (PreparedStatement), UI konsisten, dan penerapan inheritance/override yang benar.
36. **Q: Jika diberi waktu seminggu, apa prioritas Anda?**
    A: Tambah instanceof + lengkapi interface, pisahkan DAO, hash password, dan sambungkan keranjang ke DB.

---

## 13. Kesimpulan Akhir

⚠️ **Belum sepenuhnya siap untuk nilai maksimal, tetapi sangat dekat.**

Project **AsynCode_ThriftHub** sudah memenuhi **mayoritas** kriteria OOP rubrik: interface, implements, inheritance (1 super + 2 sub), overloading, overriding, constructor berparameter, encapsulation (sebagian), `super()`, dan `super.method()` semuanya ADA dengan bukti file. CRUD produk berjalan dan aman dari SQL Injection.

Yang **masih kurang dan perlu dibereskan sebelum presentasi**:
1. `instanceof` & casting **belum ada** (satu-satunya kriteria OOP yang kosong).
2. Interface belum punya **default & static method**.
3. Konsep OOP (interface, model, inheritance) **belum tersambung** ke alur aplikasi nyata; klaim **DAO Pattern belum terbukti** karena DAO tidak ada.
4. **Keamanan**: password plaintext.
5. **Clean code**: koneksi duplikat, resource leak, dead code, penamaan non-standar.
6. Beberapa fitur (keranjang, checkout, profile) masih **dummy**/tidak tersimpan DB.

Jika kamu mengerjakan seluruh item **🔴 Wajib** (estimasi beberapa jam: tambah instanceof, lengkapi interface, buat 1 DAO, hash password), project ini layak naik dari **67** ke kisaran **80+** dan aman dipresentasikan. Mulai dari nomor 1–4 di roadmap Bagian 11.
