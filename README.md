# Sistem Manajemen Perpustakaan

![Java Swing UI](https://img.shields.io/badge/UI-Java%20Swing-blue)
![Database](https://img.shields.io/badge/Database-MySQL-orange)
![Development](https://img.shields.io/badge/Development-Java%20JDK%2017-red)
![Build System](https://img.shields.io/badge/Build-Non--Maven%20(Manual%20JARs))-lightgrey)
![Look and Feel](https://img.shields.io/badge/L&F-FlatLaf-yellowgreen)

Sistem Manajemen Perpustakaan adalah aplikasi desktop berbasis Java yang dirancang untuk mengelola koleksi buku, data peminjam, transaksi peminjaman, dan otentikasi pengguna di sebuah perpustakaan. Aplikasi ini dilengkapi dengan antarmuka pengguna grafis (GUI) yang modern dan intuitif menggunakan Java Swing dengan FlatLaf, terintegrasi dengan database MySQL untuk penyimpanan data yang persisten dan aman.

---

## Fitur Utama

Aplikasi ini menyediakan fungsionalitas komprehensif untuk berbagai peran pengguna:

* **Sistem Otentikasi dan Otorisasi (Login/Registrasi):**
   * **Registrasi Akun Baru:** Pengguna dapat mendaftar untuk membuat akun baru dengan peran `USER` atau `ADMIN`.
   * **Login Aman:** Autentikasi pengguna dengan *hashing* password menggunakan jBCrypt.
   * **Manajemen Peran (Roles):**
      * `SUPER_ADMIN`: Akses penuh ke semua fitur, termasuk manajemen pengguna (jika diimplementasikan).
      * `ADMIN`: Mengelola buku (CRUD), peminjam (CRUD), dan transaksi peminjaman/pengembalian (persetujuan, penolakan, pencatatan).
      * `USER`: Melihat daftar buku, mengajukan peminjaman, dan mengembalikan buku yang dipinjam.
* **Manajemen Buku:**
   * **Tambah/Perbarui/Hapus Buku:** (Hanya untuk `ADMIN`/`SUPER_ADMIN`) Mengelola detail buku dan stok salinan.
   * **Lihat Buku:** (Semua peran) Menampilkan daftar buku beserta status ketersediaannya.
   * **Status Ketersediaan Dinamis:** Menampilkan status buku berdasarkan `total_copies` dan `available_copies` (misalnya: "Full (Ready)", "Tersedia (X/Y)", "Habis").
* **Manajemen Peminjam:**
   * **Tambah/Perbarui/Hapus Peminjam:** (Hanya untuk `SUPER_ADMIN`) Mengelola data anggota perpustakaan.
* **Manajemen Transaksi Peminjaman:**
   * **Pengajuan Peminjaman:** (`USER`) Mengajukan permintaan peminjaman sejumlah salinan buku yang akan menunggu persetujuan admin.
   * **Persetujuan/Penolakan Peminjaman:** (`ADMIN`/`SUPER_ADMIN`) Meninjau dan menyetujui atau menolak permintaan peminjaman, yang secara otomatis memperbarui stok buku.
   * **Pencatatan Peminjaman Langsung:** (`ADMIN`/`SUPER_ADMIN`) Mencatat peminjaman buku yang langsung disetujui.
   * **Pengembalian Buku:** (`USER`) Mengembalikan buku yang telah dipinjam, secara otomatis memperbarui stok buku dan status transaksi.
   * **Lihat Transaksi:** (`ADMIN`/`SUPER_ADMIN`) Menampilkan riwayat lengkap semua transaksi.
* **Antarmuka Pengguna Modern:**
   * Desain GUI yang bersih, datar, dan modern menggunakan **FlatLaf**.
   * Jendela utama (dashboard) otomatis dimaksimalkan saat startup.
   * Tombol "Refresh Data" di setiap tab untuk memperbarui tampilan.
   * Visibilitas tab dan tombol disesuaikan berdasarkan peran pengguna yang login.

---

## Teknologi yang Digunakan

* **Bahasa Pemrograman:** Java (JDK 17)
* **Antarmuka Pengguna (GUI):** Java Swing
* **Look and Feel:** [FlatLaf](https://www.formdev.com/flatlaf/) (FlatLaf Light Mac Theme)
* **Database:** [MySQL](https://www.mysql.com/)
* **Server Database Lokal:** [XAMPP](https://www.apachefriends.org/index.html) (untuk menjalankan server MySQL)
* **Hashing Password:** [jBCrypt](https://mvnrepository.com/artifact/org.mindrot/jbcrypt)
* **Konektor Database:** MySQL Connector/J
* **Integrated Development Environment (IDE):** [IntelliJ IDEA](https://www.jetbrains.com/idea/) (direkomendasikan)

---

## Persyaratan Sistem

Untuk menjalankan aplikasi ini, pastikan Anda memiliki lingkungan pengembangan yang sesuai:

* **Java Development Kit (JDK) 17** atau versi yang lebih baru terinstal.
* **XAMPP** terinstal dan modul **MySQL** harus dalam keadaan berjalan.
* Koneksi internet aktif (diperlukan untuk mengunduh file JAR eksternal).

---

## Panduan Instalasi & Penggunaan

Ikuti langkah-langkah di bawah ini untuk menyiapkan dan menjalankan aplikasi di lingkungan lokal Anda.

### 1. Konfigurasi Database (MySQL via XAMPP)

1.  **Unduh & Instal XAMPP:** Jika belum, unduh dan instal XAMPP dari situs web resminya.
2.  **Jalankan MySQL:** Buka **XAMPP Control Panel** dan klik tombol **Start** pada modul **MySQL**. Pastikan statusnya berubah menjadi "Running".
3.  **Akses MySQL Command Line Client:**
   * Di **XAMPP Control Panel**, pada baris **MySQL**, klik tombol **`Shell`** atau **`CMD`**.
   * Login ke MySQL: `mysql -u root -p` (tekan Enter jika tidak ada password).
   * Pilih database: `USE library_db;` (Jika belum ada, Anda bisa buat di phpMyAdmin atau jalankan `CREATE DATABASE library_db;`).
4.  **Reset dan Buat Skema Tabel:** Tempelkan dan jalankan perintah SQL berikut **secara keseluruhan** dalam satu blok di MySQL Command Line Client:

    ```sql
    SET FOREIGN_KEY_CHECKS = 0; -- Nonaktifkan pemeriksaan foreign key sementara

    TRUNCATE TABLE transactions;
    TRUNCATE TABLE books;
    TRUNCATE TABLE borrowers;
    TRUNCATE TABLE users;

    -- Drop tabel lama jika ada, untuk memastikan skema terbaru yang digunakan
    DROP TABLE IF EXISTS transactions;
    DROP TABLE IF EXISTS books;
    DROP TABLE IF EXISTS borrowers;
    DROP TABLE IF EXISTS users;

    -- Tabel Books
    CREATE TABLE books (
        book_id INT PRIMARY KEY AUTO_INCREMENT,
        title VARCHAR(100) NOT NULL,
        author VARCHAR(50) NOT NULL,
        isbn VARCHAR(13) UNIQUE,
        total_copies INT DEFAULT 1 NOT NULL,
        available_copies INT DEFAULT 1 NOT NULL
    );

    -- Tabel Borrowers
    CREATE TABLE borrowers (
        borrower_id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(50) NOT NULL,
        email VARCHAR(100) UNIQUE,
        phone VARCHAR(15)
    );

    -- Tabel Users
    CREATE TABLE users (
        user_id INT PRIMARY KEY AUTO_INCREMENT,
        username VARCHAR(50) NOT NULL UNIQUE,
        password_hash VARCHAR(255) NOT NULL,
        full_name VARCHAR(100),
        email VARCHAR(100) UNIQUE,
        role VARCHAR(20) NOT NULL DEFAULT 'USER',
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

    -- Tabel Transactions
    CREATE TABLE transactions (
        transaction_id INT PRIMARY KEY AUTO_INCREMENT,
        book_id INT,
        borrower_id INT,
        borrow_date DATE NOT NULL,
        return_date DATE,
        status VARCHAR(20) DEFAULT 'Pending' NOT NULL, -- Pending, Approved, Rejected, Returned
        quantity INT DEFAULT 1 NOT NULL, -- Jumlah salinan yang dipinjam
        FOREIGN KEY (book_id) REFERENCES books(book_id),
        FOREIGN KEY (borrower_id) REFERENCES borrowers(borrower_id)
    );

    SET FOREIGN_KEY_CHECKS = 1; -- Aktifkan kembali pemeriksaan foreign key
    ```

### 2. Konfigurasi Proyek Java (IntelliJ IDEA)

1.  **Clone Repositori:**
    ```bash
    git clone [https://github.com/YourUsername/LibraryManagementSystem.git](https://github.com/YourUsername/LibraryManagementSystem.git)
    cd LibraryManagementSystem
    ```
    *(Ganti `YourUsername` dengan username GitHub Anda)*
2.  **Buka Proyek di IntelliJ IDEA:**
   * Buka IntelliJ IDEA.
   * Pilih `File` > `Open...` dan navigasikan ke folder `LibraryManagementSystem` yang baru Anda *clone*.
3.  **Unduh dan Tambahkan File JAR Secara Manual:**
   * Buat folder `lib/` di *root* proyek Anda jika belum ada (`LibraryManagementSystem/lib/`).
   * Unduh semua file `.jar` berikut dan masukkan ke dalam folder `lib/`:
      * `mysql-connector-j-<version>.jar` (misal: `mysql-connector-j-8.0.33.jar`)
      * `flatlaf-<version>.jar` (misal: `flatlaf-3.4.jar`)
      * `flatlaf-themes-<version>.jar` (misal: `flatlaf-themes-3.4.jar`)
      * `jbcrypt-<version>.jar` (misal: `jbcrypt-0.4.jar`)
   * Di IntelliJ IDEA, pergi ke `File` > `Project Structure...` (`Ctrl+Alt+Shift+S`).
   * Di panel kiri, pilih **`Libraries`** di bawah `Project Settings`.
   * Klik tombol **`+`** (tambah) > **`Java`**.
   * Navigasikan ke folder `lib/` proyek Anda, pilih **semua file `.jar`** yang Anda unduh, dan klik `OK`.
   * Pilih modul proyek Anda (`LibraryManagementSystem`) jika diminta, lalu klik `OK`.
4.  **Invalidate Caches dan Restart IDE:**
   * Di IntelliJ IDEA, pergi ke `File` > `Invalidate Caches / Restart...`.
   * Klik **`Invalidate and Restart`**.
5.  **Rebuild Proyek:** Setelah IntelliJ IDEA restart dan proyek terbuka sepenuhnya, pergi ke `Build` > **`Rebuild Project`** dari menu atas.

### 3. Jalankan Aplikasi

1.  Pastikan modul **MySQL** di **XAMPP Control Panel** masih dalam keadaan "Running".
2.  Di IntelliJ IDEA, navigasikan ke file `src/com/library/Main.java`.
3.  Klik kanan pada file `Main.java` di Project Explorer, lalu pilih **`Run 'Main.main()'`**.

Aplikasi Sistem Manajemen Perpustakaan akan terbuka dengan jendela login.

## Kredensial Pengguna Default

Saat pertama kali Anda menjalankan aplikasi setelah mereset database, akun-akun berikut akan dibuat secara otomatis:

* **SUPER_ADMIN:**
   * Username: `superadmin`
   * Password: `superpass`
* **ADMIN:**
   * Username: `admin`
   * Password: `adminpass`
* **USER:**
   * Username: `user`
   * Password: `userpass`

## Penggunaan Aplikasi

* **Login Form:** Masuk menggunakan kredensial di atas atau daftar akun baru.
* **Dashboard (MainFrame):** Jendela utama akan dimaksimalkan secara otomatis.
   * **Header:** Menampilkan nama pengguna dan peran, dengan tombol Logout.
   * **Tab "Buku":**
      * `USER`: Melihat daftar buku (dengan status ketersediaan seperti "Full (Ready)", "Tersedia (X/Y)", "Habis"), mengajukan peminjaman buku (menentukan jumlah salinan), dan mengembalikan buku yang dipinjam.
      * `ADMIN`/`SUPER_ADMIN`: Mengelola (Tambah/Perbarui/Hapus) buku dan stok salinannya.
   * **Tab "Peminjam":**
      * `SUPER_ADMIN`: Mengelola (Tambah/Perbarui/Hapus) data peminjam.
      * Peran lain: Tab ini tidak terlihat.
   * **Tab "Transaksi":**
      * `ADMIN`/`SUPER_ADMIN`: Melihat semua permintaan peminjaman, menyetujui/menolak permintaan yang 'Pending', dan mencatat peminjaman langsung.
      * Peran lain: Tab ini tidak terlihat.
* **Tombol "Refresh Data":** Tersedia di setiap tab untuk memperbarui data yang ditampilkan di tabel.

---

## Kontribusi

Kontribusi dalam bentuk *bug reports*, permintaan fitur, atau *pull requests* sangat kami hargai! Jika Anda ingin berkontribusi:

1.  *Fork* repositori ini.
2.  Buat cabang baru untuk fitur Anda (`git checkout -b feature/nama-fitur-anda`).
3.  Lakukan perubahan Anda dan *commit* dengan pesan yang deskriptif.
4.  *Push* cabang Anda ke *fork* Anda (`git push origin feature/nama-fitur-anda`).
5.  Buat *Pull Request* ke repositori utama.

---

Terima kasih telah menggunakan Sistem Manajemen Perpustakaan ini! Jika Anda memiliki pertanyaan atau saran, jangan ragu untuk berinteraksi.

---

### Langkah 2: Push ke GitHub

Setelah file `README.md` lokal Anda diperbarui:

1.  **Buka panel **Git** (atau **Version Control**) di bagian bawah IntelliJ IDEA (`Alt + 9`).
2.  Pergi ke tab **Local Changes**. Anda akan melihat `README.md` (dan mungkin file Java lainnya jika ada perubahan lokal yang belum di-commit) di bawah kategori **Default Changelist**.
3.  **Pilih semua perubahan** yang ingin Anda dorong.
4.  Di bagian bawah panel **Local Changes**, tulis pesan *commit* yang deskriptif, misalnya: `Final commit: All features implemented, GUI enhanced, and README updated.`
5.  Klik tombol **Commit**.
6.  Pergi ke menu **Git** (atau **VCS**) di bagian atas.
7.  Pilih **Push...** (atau tekan `Ctrl + Shift + K`).
8.  Di jendela `Push Git Repository`, pastikan cabang yang ingin Anda dorong adalah **`main`** (atau `master`) dan repositori yang dituju adalah `origin`.
9.  Klik tombol **Push**.
   * Jika diminta, login ke akun GitHub Anda.

Setelah proses *push* selesai, kunjungi repositori Anda di GitHub. Anda akan melihat semua perubahan terbaru, dan `README.md` akan ditampilkan dengan indah di halaman utama repositori Anda.

Selamat, proyek Anda sekarang sepenuhnya selesai dan terunggah di GitHub!