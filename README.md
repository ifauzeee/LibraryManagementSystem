
-----

# Sistem Manajemen Perpustakaan



Sistem Manajemen Perpustakaan adalah aplikasi desktop berbasis Java yang dirancang untuk mengelola koleksi buku, data peminjam, transaksi peminjaman, dan otentikasi pengguna. Aplikasi ini dilengkapi dengan antarmuka pengguna grafis (GUI) yang modern dan intuitif menggunakan Java Swing, dengan tema visual FlatLaf. Data disimpan secara persisten dan aman menggunakan database MySQL.

-----

## Daftar Isi

* [Deskripsi Proyek](https://www.google.com/search?q=%23deskripsi-proyek)
* [Fitur Utama](https://www.google.com/search?q=%23fitur-utama)
* [Peran Pengguna & Akses](https://www.google.com/search?q=%23peran-pengguna--akses)
* [Teknologi yang Digunakan](https://www.google.com/search?q=%23teknologi-yang-digunakan)
* [Persyaratan Sistem](https://www.google.com/search?q=%23persyaratan-sistem)
* [Struktur File Proyek](https://www.google.com/search?q=%23struktur-file-proyek)
* [Panduan Instalasi & Penggunaan](https://www.google.com/search?q=%23panduan-instalasi--penggunaan)
   * [1. Konfigurasi Database](https://www.google.com/search?q=%231-konfigurasi-database-mysql-via-xampp)
   * [2. Konfigurasi Proyek Java](https://www.google.com/search?q=%232-konfigurasi-proyek-java-intellij-idea)
   * [3. Jalankan Aplikasi](https://www.google.com/search?q=%233-jalankan-aplikasi)
* [Kredensial Pengguna Default](https://www.google.com/search?q=%23kredensial-pengguna-default)
* [Kontribusi](https://www.google.com/search?q=%23kontribusi)

-----

## Deskripsi Proyek

Sistem Manajemen Perpustakaan ini menyediakan solusi komprehensif untuk mengelola operasional dasar perpustakaan. Aplikasi desktop ini dibangun menggunakan Java Swing, menawarkan pengalaman pengguna yang mulus melalui desain antarmuka modern yang terinspirasi oleh FlatLaf. Semua data dikelola dan disimpan dengan aman di database MySQL. Aplikasi ini mendukung alur kerja peminjaman buku yang melibatkan pengajuan oleh pengguna, persetujuan oleh admin, hingga pengembalian buku dengan pembaruan stok otomatis.

-----

## Fitur Utama

Aplikasi ini menyediakan fungsionalitas komprehensif yang disesuaikan untuk berbagai peran pengguna:

* **Sistem Otentikasi dan Otorisasi (Login & Registrasi):**
   * **Registrasi Akun Baru:** Memungkinkan pengguna untuk mendaftar akun baru dengan peran `USER` atau `ADMIN`.
   * **Login Aman:** Otentikasi pengguna dengan *hashing* password menggunakan library jBCrypt untuk keamanan data yang sensitif.
   * **Manajemen Peran (Role-Based Access Control):** Mengontrol fitur dan modul yang dapat diakses berdasarkan peran pengguna yang login (`SUPER_ADMIN`, `ADMIN`, `USER`).
* **Manajemen Buku:**
   * **Tambah/Perbarui/Hapus Buku:** (Hanya untuk `ADMIN`/`SUPER_ADMIN`) Mengelola detail buku dan stok salinan yang tersedia.
   * **Lihat Daftar Buku:** (Semua peran) Menampilkan daftar buku lengkap beserta informasi stoknya.
   * **Status Ketersediaan Dinamis:** Menampilkan status ketersediaan buku secara real-time di tabel, seperti:
      * `Full (Ready)`: Semua salinan tersedia.
      * `Tersedia (X/Y)`: Menunjukkan jumlah salinan yang tersedia dari total salinan (misal: `3/5`).
      * `Habis`: Tidak ada salinan yang tersedia untuk dipinjam.
* **Manajemen Peminjam:**
   * **Tambah/Perbarui/Hapus Peminjam:** (Hanya untuk `SUPER_ADMIN`) Mengelola data anggota perpustakaan, termasuk nama, email, dan nomor telepon.
* **Manajemen Transaksi Peminjaman:**
   * **Pengajuan Peminjaman:** (`USER`) Pengguna dapat memilih buku dan menentukan jumlah salinan yang ingin dipinjam, kemudian mengajukan permintaan yang berstatus `Pending`.
   * **Persetujuan/Penolakan Peminjaman:** (`ADMIN`/`SUPER_ADMIN`) Meninjau dan menyetujui atau menolak permintaan peminjaman yang `Pending`. Persetujuan otomatis mengurangi jumlah `available_copies` buku.
   * **Pencatatan Peminjaman Langsung:** (`ADMIN`/`SUPER_ADMIN`) Mencatat peminjaman buku yang langsung disetujui (untuk alur admin yang mencatat peminjaman secara instan).
   * **Pengembalian Buku:** (`USER`) Pengguna dapat menandai buku yang telah dipinjam sebagai dikembalikan. Proses ini secara otomatis memperbarui status transaksi menjadi `Returned` dan menambah jumlah `available_copies` buku.
   * **Lihat Riwayat Transaksi:** (`ADMIN`/`SUPER_ADMIN`) Menampilkan riwayat lengkap semua transaksi peminjaman dan pengembalian.
* **Antarmuka Pengguna (GUI) Modern:**
   * Desain yang bersih, datar, dan modern menggunakan **FlatLaf** untuk pengalaman pengguna yang menyenangkan.
   * Jendela utama (dashboard) otomatis dimaksimalkan saat aplikasi dibuka untuk memanfaatkan ruang layar penuh.
   * Tombol "Refresh Data" di setiap tab untuk memperbarui tampilan data terkini.
   * Visibilitas tab dan tombol aksi disesuaikan secara dinamis berdasarkan peran pengguna yang login.

-----

## Peran Pengguna & Akses

Aplikasi ini menerapkan kontrol akses berbasis peran untuk membatasi fungsionalitas berdasarkan jenis pengguna:

* **`SUPER_ADMIN`**:
   * Akses penuh ke semua tab: **Buku**, **Peminjam**, **Transaksi**.
   * Dapat melakukan semua operasi CRUD (Tambah, Perbarui, Hapus) pada Buku, Peminjam, dan Transaksi.
   * Dapat menyetujui/menolak permintaan peminjaman.
* **`ADMIN`**:
   * Akses ke tab: **Buku**, **Transaksi**.
   * Dapat melakukan semua operasi CRUD pada Buku dan Transaksi (kecuali penghapusan peminjam).
   * Dapat menyetujui/menolak permintaan peminjaman.
* **`USER`**:
   * Akses terbatas ke tab: **Buku**.
   * Hanya dapat melihat daftar buku, mengajukan peminjaman buku, dan mengembalikan buku yang dipinjam.
   * Tidak dapat melakukan operasi CRUD pada Buku, Peminjam, atau Transaksi (kecuali pengajuan dan pengembalian mereka sendiri).

-----

## Teknologi yang Digunakan

* **Bahasa Pemrograman:** Java (JDK 17)
* **Antarmuka Pengguna (GUI):** Java Swing
* **Look and Feel:** [FlatLaf](https://www.formdev.com/flatlaf/) (FlatLaf Light Mac Theme untuk tampilan modern)
* **Database:** [MySQL](https://www.mysql.com/)
* **Server Database Lokal:** [XAMPP](https://www.apachefriends.org/index.html) (untuk menjalankan server MySQL)
* **Hashing Password:** [jBCrypt](https://mvnrepository.com/artifact/org.mindrot/jbcrypt) (digunakan untuk menyimpan password dengan aman)
* **Konektor Database:** MySQL Connector/J (Driver JDBC untuk koneksi Java-MySQL)
* **Integrated Development Environment (IDE):** [IntelliJ IDEA](https://www.jetbrains.com/idea/) (direkomendasikan untuk pengembangan)

-----

## Persyaratan Sistem

Untuk mengkompilasi dan menjalankan aplikasi ini, pastikan sistem Anda memenuhi persyaratan berikut:

* **Java Development Kit (JDK) 17** atau versi yang lebih baru terinstal.
* **XAMPP** terinstal dan **modul MySQL** harus dalam keadaan berjalan.
* Koneksi internet aktif (diperlukan saat pertama kali mengunduh file JAR eksternal).

-----

## Struktur File Proyek

Struktur proyek mengikuti konvensi standar proyek Java untuk organisasi kode yang rapi:

```
LibraryManagementSystem/
├── .idea/                      # Folder konfigurasi IntelliJ IDEA
├── out/                        # Output kompilasi (dibuat otomatis)
├── lib/                        # <-- DIREKTORI UNTUK FILE JAR EKSTERNAL
│   ├── flatlaf-3.4.jar
│   ├── flatlaf-themes-3.4.jar
│   ├── jbcrypt-0.4.jar
│   └── mysql-connector-j-8.0.33.jar
├── src/
│   └── com/
│       └── library/
│           ├── Main.java
│           ├── auth/
│           ├── config/
│           ├── dao/
│           ├── model/
│           └── view/
└── README.md
```

-----

## Panduan Instalasi & Penggunaan

Ikuti langkah-langkah di bawah ini untuk menyiapkan dan menjalankan aplikasi di lingkungan lokal Anda.

### 1\. Konfigurasi Database (MySQL via XAMPP)

1.  **Unduh & Instal XAMPP:** Jika belum, unduh dan instal XAMPP dari [situs resminya](https://www.apachefriends.org/).

2.  **Jalankan MySQL:** Buka **XAMPP Control Panel** dan klik tombol **Start** pada modul **MySQL**.

3.  **Buat Database & Tabel:**

   * Buka **phpMyAdmin** (`http://localhost/phpmyadmin`) atau gunakan MySQL client seperti **`Shell`** di XAMPP Control Panel.
   * Buat database baru bernama `library_db`.
   * Pilih database `library_db` dan buka tab **SQL**.
   * Tempelkan dan jalankan skrip SQL berikut untuk membuat semua tabel yang diperlukan.

    <!-- end list -->

    ```sql
    SET FOREIGN_KEY_CHECKS = 0;

    DROP TABLE IF EXISTS transactions;
    DROP TABLE IF EXISTS books;
    DROP TABLE IF EXISTS borrowers;
    DROP TABLE IF EXISTS users;

    CREATE TABLE books (
        book_id INT PRIMARY KEY AUTO_INCREMENT,
        title VARCHAR(100) NOT NULL,
        author VARCHAR(50) NOT NULL,
        isbn VARCHAR(13) UNIQUE,
        total_copies INT DEFAULT 1 NOT NULL,
        available_copies INT DEFAULT 1 NOT NULL
    );

    CREATE TABLE borrowers (
        borrower_id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(50) NOT NULL,
        email VARCHAR(100) UNIQUE,
        phone VARCHAR(15)
    );

    CREATE TABLE users (
        user_id INT PRIMARY KEY AUTO_INCREMENT,
        username VARCHAR(50) NOT NULL UNIQUE,
        password_hash VARCHAR(255) NOT NULL,
        full_name VARCHAR(100),
        email VARCHAR(100) UNIQUE,
        role VARCHAR(20) NOT NULL DEFAULT 'USER', -- SUPER_ADMIN, ADMIN, USER
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

    CREATE TABLE transactions (
        transaction_id INT PRIMARY KEY AUTO_INCREMENT,
        book_id INT,
        borrower_id INT,
        borrow_date DATE NOT NULL,
        return_date DATE,
        status VARCHAR(20) DEFAULT 'Pending' NOT NULL, -- Pending, Approved, Rejected, Returned
        quantity INT DEFAULT 1 NOT NULL,
        FOREIGN KEY (book_id) REFERENCES books(book_id),
        FOREIGN KEY (borrower_id) REFERENCES borrowers(borrower_id)
    );

    SET FOREIGN_KEY_CHECKS = 1;
    ```

### 2\. Konfigurasi Proyek Java (IntelliJ IDEA)

1.  **Clone Repositori:**

    ```bash
    git clone https://github.com/ifauzeee/LibraryManagementSystem.git
    cd LibraryManagementSystem
    ```

2.  **Buka Proyek di IntelliJ IDEA:**

   * Buka IntelliJ IDEA.
   * Pilih `File` \> `Open...` dan navigasikan ke folder `LibraryManagementSystem` yang baru Anda *clone*.

3.  **Tambahkan File JAR Eksternal:**

   * Buat direktori `lib` di dalam root proyek Anda jika belum ada.
   * Unduh file `.jar` berikut dan letakkan di dalam folder `lib/`:
      * [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)
      * [FlatLaf Core](https://mvnrepository.com/artifact/com.formdev/flatlaf)
      * [FlatLaf Themes](https://mvnrepository.com/artifact/com.formdev/flatlaf-themes)
      * [jBCrypt](https://mvnrepository.com/artifact/org.mindrot/jbcrypt)
   * Di IntelliJ IDEA, buka `File` \> `Project Structure...` (`Ctrl+Alt+Shift+S`).
   * Pilih **Libraries** di bawah `Project Settings`.
   * Klik ikon **`+`** \> **`Java`**.
   * Navigasikan ke folder `lib/` Anda, pilih semua file `.jar` yang telah diunduh, dan klik `OK`.

4.  **Rebuild Proyek:**

   * Untuk memastikan semua dependensi terhubung dengan benar, lakukan rebuild dengan memilih `Build` \> `Rebuild Project` dari menu atas.

### 3\. Jalankan Aplikasi

1.  Pastikan modul **MySQL** di XAMPP masih berjalan.
2.  Di Project Explorer IntelliJ IDEA, cari file `src/com/library/Main.java`.
3.  Klik kanan pada file `Main.java`, lalu pilih **`Run 'Main.main()'`**.

Aplikasi akan terbuka dan menampilkan jendela login.

-----

## Kredensial Pengguna Default

Saat pertama kali Anda menjalankan aplikasi setelah membuat skema database, akun-akun berikut dapat digunakan untuk login (atau Anda dapat mendaftar akun baru):

* **SUPER\_ADMIN:**
   * **Username:** `superadmin`
   * **Password:** `superpass`
* **ADMIN:**
   * **Username:** `admin`
   * **Password:** `adminpass`
* **USER:**
   * **Username:** `user`
   * **Password:** `userpass`

*Catatan: Akun-akun ini perlu didaftarkan terlebih dahulu melalui fitur registrasi atau dimasukkan manual ke database jika tidak dibuat secara otomatis oleh aplikasi.*

-----

## Kontribusi

Kontribusi dalam bentuk *bug reports*, permintaan fitur, atau *pull requests* sangat kami hargai\!

1.  **Fork** repositori ini.
2.  Buat branch baru (`git checkout -b feature/nama-fitur-keren`).
3.  Lakukan perubahan dan *commit* (`git commit -m 'feat: Menambahkan fitur keren'`).
4.  *Push* ke branch Anda (`git push origin feature/nama-fitur-keren`).
5.  Buat **Pull Request** baru.

-----

Terima kasih telah menggunakan Sistem Manajemen Perpustakaan ini\! Jika Anda memiliki pertanyaan atau saran, jangan ragu untuk membuka *issue* baru.