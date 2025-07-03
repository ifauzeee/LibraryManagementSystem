```markdown
# Sistem Manajemen Perpustakaan

![Java Swing UI](https://img.shields.io/badge/UI-Java%20Swing-blue)
![Database](https://img.shields.io/badge/Database-MySQL-orange)
![Development](https://img.shields.io/badge/Development-Java%20JDK%2017-red)
![Build System](https://img.shields.io/badge/Build-Non--Maven%20(Manual%20JARs))-lightgrey)
![Look and Feel](https://img.shields.io/badge/L&F-FlatLaf-yellowgreen)

Sistem Manajemen Perpustakaan adalah aplikasi desktop berbasis Java yang dirancang untuk mengelola koleksi buku, data peminjam, transaksi peminjaman, dan otentikasi pengguna di sebuah perpustakaan. Aplikasi ini dilengkapi dengan antarmuka pengguna grafis (GUI) yang modern dan intuitif menggunakan Java Swing dengan FlatLaf, terintegrasi dengan database MySQL untuk penyimpanan data yang persisten dan aman.

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

Sistem ini adalah solusi komprehensif untuk mengelola operasional dasar perpustakaan. Aplikasi desktop ini dibangun dengan Java Swing, menawarkan pengalaman pengguna yang mulus melalui desain antarmuka modern yang terinspirasi oleh FlatLaf. Semua data dikelola dan disimpan dengan aman di database MySQL, mendukung alur kerja peminjaman buku mulai dari pengajuan oleh pengguna, persetujuan oleh admin, hingga pengembalian buku dengan pembaruan stok otomatis.

---

## Fitur Utama

Aplikasi ini menyediakan fungsionalitas komprehensif untuk berbagai peran pengguna:

* **Sistem Otentikasi dan Otorisasi (Login & Registrasi):**
    * **Registrasi Akun Baru:** Pengguna dapat mendaftar untuk membuat akun baru dengan peran `USER` atau `ADMIN`.
    * **Login Aman:** Autentikasi pengguna dengan *hashing* password menggunakan library jBCrypt untuk keamanan data sensitif.
    * **Manajemen Peran (Role-Based Access Control):** Menentukan fitur dan modul yang dapat diakses berdasarkan peran pengguna yang login (`SUPER_ADMIN`, `ADMIN`, `USER`).
* **Manajemen Buku:**
    * **Tambah/Perbarui/Hapus Buku:** (Hanya untuk `ADMIN`/`SUPER_ADMIN`) Mengelola detail buku seperti judul, penulis, ISBN, dan mengatur jumlah total salinan yang dimiliki.
    * **Status Ketersediaan Dinamis:** Menampilkan status ketersediaan buku secara real-time di tabel (`Full (Ready)`, `Tersedia (X/Y)`, `Habis`) berdasarkan `total_copies` dan `available_copies`.
    * **Lihat Daftar Buku:** (Semua peran) Menampilkan daftar lengkap buku beserta informasi stoknya.
* **Manajemen Peminjam:**
    * **Tambah/Perbarui/Hapus Peminjam:** (Hanya untuk `SUPER_ADMIN`) Mengelola data anggota perpustakaan, termasuk nama, email, dan nomor telepon.
* **Manajemen Transaksi Peminjaman:**
    * **Pengajuan Peminjaman:** (`USER`) Pengguna dapat memilih buku dan menentukan jumlah salinan yang ingin dipinjam, kemudian mengajukan permintaan yang berstatus `Pending`.
    * **Persetujuan/Penolakan Peminjaman:** (`ADMIN`/`SUPER_ADMIN`) Meninjau permintaan peminjaman yang `Pending`, menyetujui atau menolaknya. Persetujuan otomatis mengurangi `available_copies` buku.
    * **Pencatatan Peminjaman Langsung:** (`ADMIN`/`SUPER_ADMIN`) Mencatat peminjaman buku yang langsung disetujui (untuk alur admin).
    * **Pengembalian Buku:** (`USER`) Pengguna dapat menandai buku yang telah mereka pinjam sebagai dikembalikan. Proses ini secara otomatis memperbarui status transaksi menjadi `Returned` dan menambah `available_copies` buku.
    * **Lihat Riwayat Transaksi:** (`ADMIN`/`SUPER_ADMIN`) Menampilkan riwayat lengkap semua transaksi peminjaman dan pengembalian.
* **Antarmuka Pengguna (GUI) Modern:**
    * Desain yang bersih, datar, dan modern menggunakan **FlatLaf** untuk pengalaman pengguna yang menyenangkan.
    * Jendela utama (dashboard) otomatis dimaksimalkan saat aplikasi dibuka.
    * Tombol "Refresh Data" di setiap tab untuk memperbarui tampilan data terkini.
    * Visibilitas tab dan tombol aksi disesuaikan secara dinamis berdasarkan peran pengguna yang login.

---

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

---

## Teknologi yang Digunakan

* **Bahasa Pemrograman:** Java (JDK 17)
* **Antarmuka Pengguna (GUI):** Java Swing
* **Look and Feel:** [FlatLaf](https://www.formdev.com/flatlaf/) (FlatLaf Light Mac Theme untuk tampilan modern)
* **Database:** [MySQL](https://www.mysql.com/)
* **Server Database Lokal:** [XAMPP](https://www.apachefriends.org/index.html) (untuk menjalankan server MySQL)
* **Hashing Password:** [jBCrypt](https://mvnrepository.com/artifact/org.mindrot/jbcrypt) (digunakan untuk menyimpan password dengan aman)
* **Konektor Database:** MySQL Connector/J (Driver JDBC untuk koneksi Java-MySQL)
* **Integrated Development Environment (IDE):** [IntelliJ IDEA](https://www.jetbrains.com/idea/) (direkomendasikan untuk pengembangan)

---

## Persyaratan Sistem

Untuk mengkompilasi dan menjalankan aplikasi ini, pastikan sistem Anda memenuhi persyaratan berikut:

* **Java Development Kit (JDK) 17** atau versi yang lebih baru terinstal.
* **XAMPP** terinstal dan **modul MySQL** harus dalam keadaan berjalan.
* Koneksi internet aktif (diperlukan saat pertama kali mengunduh file JAR eksternal).

---

## Struktur File Proyek

Struktur proyek mengikuti konvensi standar proyek Java untuk organisasi kode yang rapi:

```

LibraryManagementSystem/
├── .idea/                       \# Folder konfigurasi IntelliJ IDEA
├── out/                         \# Output kompilasi (dibuat otomatis)
├── lib/                         \# \<--- DIREKTORI UNTUK FILE JAR EKSTERNAL
│   ├── mysql-connector-j-\<version\>.jar
│   ├── flatlaf-\<version\>.jar
│   ├── flatlaf-themes-\<version\>.jar
│   └── jbcrypt-\<version\>.jar
├── src/                         \# Sumber kode utama aplikasi
│   └── com/
│       └── library/
│           ├── dao/             \# Data Access Objects (Interaksi dengan Database)
│           │   ├── BookDAO.java         \# Operasi CRUD untuk tabel 'books'
│           │   ├── BorrowerDAO.java     \# Operasi CRUD untuk tabel 'borrowers'
│           │   ├── TransactionDAO.java  \# Operasi CRUD untuk tabel 'transactions'
│           │   └── UserDAO.java         \# Operasi CRUD untuk tabel 'users'
│           ├── model/           \# Model Data (Representasi data dari database)
│           │   ├── Book.java            \# Model untuk buku
│           │   ├── Borrower.java        \# Model untuk peminjam
│           │   ├── Transaction.java     \# Model untuk transaksi
│           │   └── User.java            \# Model untuk pengguna sistem
│           ├── view/            \# Antarmuka Pengguna Grafis (GUI Swing)
│           │   ├── LoginFrame.java      \# Jendela login utama
│           │   ├── MainFrame.java       \# Jendela dashboard utama aplikasi
│           │   ├── BookPanel.java       \# Panel tab untuk manajemen buku
│           │   ├── BorrowerPanel.java   \# Panel tab untuk manajemen peminjam
│           │   ├── RegisterFrame.java   \# Jendela registrasi akun baru
│           │   └── TransactionPanel.java\# Panel tab untuk manajemen transaksi
│           └── Main.java        \# Titik masuk aplikasi (main method)
├── .gitignore                   \# Mengatur file/folder yang diabaikan oleh Git
└── README.md                    \# Dokumentasi proyek ini

````

---

## Panduan Instalasi & Penggunaan

Ikuti langkah-langkah di bawah ini untuk menyiapkan dan menjalankan aplikasi di lingkungan lokal Anda.

### 1. Konfigurasi Database (MySQL via XAMPP)

1.  **Unduh & Instal XAMPP:** Jika belum, unduh dan instal XAMPP dari [https://www.apachefriends.org/](https://www.apachefriends.org/).
2.  **Jalankan MySQL:** Buka **XAMPP Control Panel** dan klik tombol **Start** pada modul **MySQL**. Pastikan statusnya berubah menjadi "Running".
3.  **Akses MySQL Command Line Client:**
    * Di **XAMPP Control Panel**, pada baris **MySQL**, klik tombol **`Shell`** atau **`CMD`** untuk membuka Command Prompt/Terminal MySQL.
    * Login ke MySQL: Ketik `mysql -u root -p` lalu tekan `Enter`. Jika Anda tidak memiliki password untuk user `root`, cukup tekan `Enter` saat diminta password.
    * Pilih database `library_db`: Ketik `USE library_db;` (jika database belum ada, Anda bisa membuatnya terlebih dahulu di phpMyAdmin atau dengan perintah `CREATE DATABASE library_db;` lalu `USE library_db;`).
4.  **Reset dan Buat Skema Tabel:** Tempelkan dan jalankan perintah SQL berikut **secara keseluruhan** dalam satu blok di MySQL Command Line Client. Ini akan menghapus data dan tabel lama, lalu membuat ulang dengan skema terbaru, memastikan `AUTO_INCREMENT` dimulai dari 1.

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
        role VARCHAR(20) NOT NULL DEFAULT 'USER', -- SUPER_ADMIN, ADMIN, USER
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
    Buka terminal atau Git Bash dan *clone* repositori ini ke komputer Anda:
    ```bash
    git clone [https://github.com/ifauzeee/LibraryManagementSystem.git](https://github.com/ifauzeee/LibraryManagementSystem.git)
    cd LibraryManagementSystem
    ```
2.  **Buka Proyek di IntelliJ IDEA:**
    * Buka IntelliJ IDEA.
    * Pilih `File` > `Open...` dan navigasikan ke folder `LibraryManagementSystem` yang baru Anda *clone*.
3.  **Unduh dan Tambahkan File JAR Secara Manual:**
    * Buat folder bernama `lib/` di *root* proyek Anda jika belum ada (`LibraryManagementSystem/lib/`).
    * Unduh semua file `.jar` berikut dan masukkan ke dalam folder `lib/`:
        * **MySQL Connector/J:** Kunjungi [dev.mysql.com/downloads/connector/j/](https://dev.mysql.com/downloads/connector/j/) dan unduh `mysql-connector-j-<version>.jar` (gunakan versi terbaru yang kompatibel dengan JDK Anda, misalnya `mysql-connector-j-8.0.33.jar`).
        * **FlatLaf Core:** Kunjungi [mvnrepository.com/artifact/com.formdev/flatlaf](https://mvnrepository.com/artifact/com.formdev/flatlaf) dan unduh `flatlaf-<version>.jar` (gunakan versi `3.4` atau terbaru).
        * **FlatLaf Themes:** Kunjungi [mvnrepository.com/artifact/com.formdev/flatlaf-themes](https://mvnrepository.com/artifact/com.formdev/flatlaf-themes) dan unduh `flatlaf-themes-<version>.jar` (gunakan versi `3.4` atau terbaru, harus cocok dengan versi FlatLaf Core).
        * **jBCrypt:** Kunjungi [mvnrepository.com/artifact/org.mindrot/jbcrypt](https://mvnrepository.com/artifact/org.mindrot/jbcrypt) dan unduh `jbcrypt-<version>.jar` (gunakan versi `0.4` atau terbaru).
    * Di IntelliJ IDEA, pergi ke `File` > `Project Structure...` (`Ctrl+Alt+Shift+S`).
    * Di jendela `Project Structure`, di panel kiri, pilih **`Libraries`** di bawah `Project Settings`.
    * Klik tombol **`+`** (tambah) di bagian atas panel tengah, lalu pilih **`Java`**.
    * Navigasikan ke folder `lib/` proyek Anda, pilih **semua file `.jar`** yang baru saja Anda unduh, dan klik `OK`.
    * Pilih modul proyek Anda (biasanya `LibraryManagementSystem`) jika diminta, lalu klik `OK`.
4.  **Invalidate Caches dan Restart IDE:**
    * Di IntelliJ IDEA, pergi ke `File` > `Invalidate Caches / Restart...` dari menu atas.
    * Klik **`Invalidate and Restart`**. Ini akan membersihkan *cache* internal IDE dan me-restart IntelliJ.
5.  **Rebuild Proyek:**
    * Setelah IntelliJ IDEA restart dan proyek terbuka sepenuhnya, pergi ke `Build` > **`Rebuild Project`** dari menu atas. Ini akan mengkompilasi ulang seluruh proyek Anda dari awal.

### 3. Jalankan Aplikasi

1.  Pastikan modul **MySQL** di **XAMPP Control Panel** masih dalam keadaan "Running".
2.  Di IntelliJ IDEA, navigasikan ke file `src/com/library/Main.java` di Project Explorer.
3.  Klik kanan pada file `Main.java` tersebut, lalu pilih **`Run 'Main.main()'`**.

Aplikasi Sistem Manajemen Perpustakaan akan terbuka dengan jendela login.

---

## Kredensial Pengguna Default

Saat pertama kali Anda menjalankan aplikasi setelah mereset database, akun-akun berikut akan dibuat secara otomatis untuk memudahkan pengujian:

* **SUPER_ADMIN:**
    * Username: `superadmin`
    * Password: `superpass`
* **ADMIN:**
    * Username: `admin`
    * Password: `adminpass`
* **USER:**
    * Username: `user`
    * Password: `userpass`

---

## Kontribusi

Kontribusi dalam bentuk *bug reports*, permintaan fitur, atau *pull requests* sangat kami hargai! Jika Anda ingin berkontribusi pada proyek ini, silakan ikuti langkah-langkah berikut:

1.  *Fork* repositori ini ke akun GitHub Anda.
2.  Buat cabang baru untuk fitur atau perbaikan Anda (`git checkout -b feature/nama-fitur-baru-atau-perbaikan-bug`).
3.  Lakukan perubahan Anda di kode.
4.  *Commit* perubahan Anda dengan pesan yang jelas dan deskriptif (`git commit -m 'feat: Menambahkan fitur X' ` atau `fix: Memperbaiki bug Y`).
5.  *Push* cabang Anda ke *fork* Anda (`git push origin feature/nama-fitur-baru-atau-perbaikan-bug`).
6.  Buat *Pull Request* dari *fork* Anda ke repositori utama ini, jelaskan perubahan yang Anda buat, dan mengapa perubahan itu diperlukan.

---

Terima kasih telah menggunakan Sistem Manajemen Perpustakaan ini! Jika Anda memiliki pertanyaan atau saran, jangan ragu untuk berinteraksi dengan membuka *issue* baru di repositori GitHub ini.
````