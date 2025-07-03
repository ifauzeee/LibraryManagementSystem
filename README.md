# Sistem Manajemen Perpustakaan

![Java Swing UI](https://img.shields.io/badge/UI-Java%20Swing-blue)
![Database](https://img.shields.io/badge/Database-MySQL-orange)
![Development](https://img.shields.io/badge/Development-Java%20JDK%2017-red)
![Build System](https://img.shields.io/badge/Build-Manual%20JARs-lightgrey)
![Look and Feel](https://img.shields.io/badge/L&F-FlatLaf-yellowgreen)

Sistem Manajemen Perpustakaan adalah aplikasi desktop berbasis Java yang dirancang untuk mengelola koleksi buku, data peminjam, transaksi peminjaman, dan otentikasi pengguna. Aplikasi ini dilengkapi dengan antarmuka pengguna grafis (GUI) yang modern dan intuitif menggunakan Java Swing, dengan tema visual FlatLaf. Data disimpan secara persisten dan aman menggunakan database MySQL.

---

## Daftar Isi

* [Deskripsi Proyek](#deskripsi-proyek)
* [Fitur Utama](#fitur-utama)
* [Peran Pengguna & Akses](#peran-pengguna--akses)
* [Teknologi yang Digunakan](#teknologi-yang-digunakan)
* [Persyaratan Sistem](#persyaratan-sistem)
* [Struktur File Proyek](#struktur-file-proyek)
* [Panduan Instalasi & Penggunaan](#panduan-instalasi--penggunaan)
   * [1. Konfigurasi Database](#1-konfigurasi-database-mysql-via-xampp)
   * [2. Konfigurasi Proyek Java](#2-konfigurasi-proyek-java-intellij-idea)
   * [3. Jalankan Aplikasi](#3-jalankan-aplikasi)
* [Kredensial Pengguna Default](#kredensial-pengguna-default)
* [Kontribusi](#kontribusi)

---

## Deskripsi Proyek

Sistem Manajemen Perpustakaan ini menyediakan solusi komprehensif untuk mengelola operasional dasar perpustakaan. Aplikasi desktop ini dibangun menggunakan Java Swing, menawarkan pengalaman pengguna yang mulus melalui desain antarmuka modern yang terinspirasi oleh FlatLaf. Semua data dikelola dan disimpan dengan aman di database MySQL. Aplikasi ini mendukung alur kerja peminjaman buku yang melibatkan pengajuan oleh pengguna, persetujuan oleh admin, hingga pengembalian buku dengan pembaruan stok otomatis.

---

## Fitur Utama

Aplikasi ini menyediakan fungsionalitas komprehensif yang disesuaikan untuk berbagai peran pengguna:

* **Sistem Otentikasi dan Otorisasi (Login & Registrasi):**
   * **Registrasi Akun Baru:** Memungkinkan pengguna untuk mendaftar akun baru dengan peran `USER` atau `ADMIN`.
   * **Login Aman:** Otentikasi pengguna dengan *hashing* password menggunakan library jBCrypt untuk keamanan data.
   * **Manajemen Peran (Role-Based Access Control):** Mengontrol fitur yang dapat diakses berdasarkan peran pengguna (`SUPER_ADMIN`, `ADMIN`, `USER`).

* **Manajemen Buku:**
   * **Tambah/Perbarui/Hapus Buku:** (Hanya `ADMIN`/`SUPER_ADMIN`) Mengelola detail buku dan stok salinan.
   * **Lihat Daftar Buku:** (Semua peran) Menampilkan daftar buku lengkap beserta informasi stoknya.
   * **Status Ketersediaan Dinamis:** Menampilkan status ketersediaan buku secara real-time:
      * `Full (Ready)`: Semua salinan tersedia.
      * `Tersedia (X/Y)`: Menunjukkan jumlah salinan yang tersedia dari total.
      * `Habis`: Tidak ada salinan yang tersedia.

* **Manajemen Peminjam:**
   * **Tambah/Perbarui/Hapus Peminjam:** (Hanya `SUPER_ADMIN`) Mengelola data anggota perpustakaan.

* **Manajemen Transaksi Peminjaman:**
   * **Pengajuan Peminjaman:** (`USER`) Pengguna mengajukan permintaan peminjaman yang berstatus `Pending`.
   * **Persetujuan/Penolakan Peminjaman:** (`ADMIN`/`SUPER_ADMIN`) Meninjau dan mengubah status permintaan. Persetujuan otomatis mengurangi stok buku.
   * **Pengembalian Buku:** (`USER`) Menandai buku sebagai dikembalikan, yang otomatis memperbarui status dan stok buku.
   * **Lihat Riwayat Transaksi:** (`ADMIN`/`SUPER_ADMIN`) Menampilkan riwayat lengkap semua transaksi.

* **Antarmuka Pengguna (GUI) Modern:**
   * Desain yang bersih dan modern menggunakan **FlatLaf**.
   * Jendela utama dimaksimalkan saat dibuka untuk pengalaman layar penuh.
   * Visibilitas tab dan tombol disesuaikan secara dinamis berdasarkan peran pengguna.

---

## Peran Pengguna & Akses

Aplikasi ini menerapkan kontrol akses berbasis peran:

* **`SUPER_ADMIN`**:
   * Akses penuh ke semua tab: **Buku**, **Peminjam**, **Transaksi**.
   * Dapat melakukan semua operasi CRUD pada semua data.
   * Menyetujui/menolak permintaan peminjaman.

* **`ADMIN`**:
   * Akses ke tab: **Buku**, **Transaksi**.
   * Dapat melakukan CRUD pada Buku dan Transaksi.
   * Menyetujui/menolak permintaan peminjaman.

* **`USER`**:
   * Akses terbatas ke tab: **Buku**.
   * Hanya dapat melihat daftar buku, mengajukan peminjaman, dan mengembalikan buku.

---

## Teknologi yang Digunakan

* **Bahasa Pemrograman:** Java (JDK 17)
* **Antarmuka Pengguna (GUI):** Java Swing
* **Look and Feel:** [FlatLaf](https://www.formdev.com/flatlaf/)
* **Database:** [MySQL](https://www.mysql.com/)
* **Server Lokal:** [XAMPP](https://www.apachefriends.org/index.html)
* **Hashing Password:** [jBCrypt](https://github.com/patrickfav/jbcrypt)
* **Konektor Database:** MySQL Connector/J
* **IDE:** [IntelliJ IDEA](https://www.jetbrains.com/idea/) (direkomendasikan)

---

## Persyaratan Sistem

* Java Development Kit (JDK) 17 atau lebih baru.
* XAMPP dengan modul MySQL yang sedang berjalan.
* Koneksi internet (untuk mengunduh dependensi jika diperlukan).

---

## Struktur File Proyek

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


---

## Panduan Instalasi & Penggunaan

### 1. Konfigurasi Database (MySQL via XAMPP)

1.  **Unduh & Instal XAMPP** dari [situs resminya](https://www.apachefriends.org/).
2.  **Jalankan MySQL** melalui XAMPP Control Panel.
3.  **Buat Database & Tabel:**
   * Buka `phpMyAdmin` atau MySQL client.
   * Buat database baru bernama `library_db`.
   * Jalankan skrip SQL berikut di database `library_db`:
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

### 2. Konfigurasi Proyek Java (IntelliJ IDEA)

1.  **Clone Repositori:**
    ```bash
    git clone [https://github.com/ifauzeee/LibraryManagementSystem.git](https://github.com/ifauzeee/LibraryManagementSystem.git)
    cd LibraryManagementSystem
    ```
2.  **Buka Proyek** di IntelliJ IDEA.
3.  **Tambahkan File JAR Eksternal:**
   * Pastikan semua file `.jar` yang dibutuhkan ada di dalam folder `lib/`.
   * Buka `File > Project Structure... > Libraries`.
   * Klik `+ > Java` dan pilih semua file `.jar` di dalam folder `lib/`.
4.  **Rebuild Proyek:**
   * Pilih `Build > Rebuild Project` dari menu atas.

### 3. Jalankan Aplikasi

1.  Pastikan modul MySQL di XAMPP masih berjalan.
2.  Di IntelliJ IDEA, cari file `src/com/library/Main.java`.
3.  Klik kanan pada file `Main.java`, lalu pilih **`Run 'Main.main()'`**.

---

## Kredensial Pengguna Default

Anda dapat mendaftar atau menggunakan akun berikut setelah database dibuat:

* **`SUPER_ADMIN`**:
   * Username: `superadmin`
   * Password: `superpass`
* **`ADMIN`**:
   * Username: `admin`
   * Password: `adminpass`
* **`USER`**:
   * Username: `user`
   * Password: `userpass`

> *Catatan: Akun-akun ini perlu didaftarkan terlebih dahulu melalui fitur registrasi aplikasi.*

---

## Kontribusi

Kontribusi sangat kami hargai!

1.  **Fork** repositori ini.
2.  Buat *branch* baru (`git checkout -b feature/NamaFitur`).
3.  *Commit* perubahan Anda (`git commit -m 'feat: Menambahkan NamaFitur'`).
4.  *Push* ke *branch* Anda (`git push origin feature/NamaFitur`).
5.  Buat **Pull Request** baru.

---

Terima kasih telah menggunakan Sistem Manajemen Perpustakaan ini!