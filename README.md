Sistem Manajemen Perpustakaan
Sistem Manajemen Perpustakaan adalah aplikasi desktop berbasis Java yang dirancang untuk mengelola koleksi buku, data peminjam, dan transaksi peminjaman serta pengembalian buku di perpustakaan. Aplikasi ini menggunakan antarmuka pengguna grafis (GUI) berbasis Java Swing dengan tampilan modern melalui integrasi FlatLaf. Data disimpan secara persisten menggunakan database MySQL.
Fitur Utama
Aplikasi ini mendukung operasi CRUD (Create, Read, Update, Delete) untuk tiga entitas utama:
Manajemen Buku

Tambah Buku: Memungkinkan pengguna untuk menambahkan buku baru ke dalam sistem.
Lihat Buku: Menampilkan daftar lengkap semua buku dalam koleksi perpustakaan.
Perbarui Buku: Mengedit informasi buku seperti judul, penulis, ISBN, dan status ketersediaan.
Hapus Buku: Menghapus buku dari database.

Manajemen Peminjam

Tambah Peminjam: Mendaftarkan peminjam baru dengan detail kontak mereka.
Lihat Peminjam: Menampilkan daftar semua peminjam yang terdaftar.
Perbarui Peminjam: Mengubah informasi peminjam seperti nama, email, dan nomor telepon.
Hapus Peminjam: Menghapus data peminjam dari sistem.

Manajemen Transaksi Peminjaman

Pinjam Buku: Mencatat transaksi peminjaman buku, secara otomatis memperbarui status buku menjadi 'Borrowed'.
Kembalikan Buku: Menandai buku sebagai dikembalikan, memperbarui status buku menjadi 'Available' dan mencatat tanggal pengembalian.
Lihat Transaksi: Menampilkan riwayat lengkap transaksi peminjaman dan pengembalian.
Hapus Transaksi: Menghapus catatan transaksi dari sistem.

Antarmuka Pengguna

Didesain dengan FlatLaf untuk tampilan modern dan pengalaman pengguna yang intuitif.

Teknologi yang Digunakan

Bahasa Pemrograman: Java (JDK 17)
Antarmuka Pengguna: Java Swing
Look and Feel: FlatLaf (FlatLaf Light Mac Theme)
Sistem Build: Apache Maven
Database: MySQL
Server Database Lokal: XAMPP (untuk menjalankan server MySQL)
Konektor Database: MySQL Connector/J
IDE Rekomendasi: IntelliJ IDEA

Persyaratan Sistem

Java Development Kit (JDK) 17 atau lebih baru.
XAMPP dengan modul MySQL aktif.
Koneksi internet aktif untuk mengunduh dependensi Maven saat pertama kali menyiapkan proyek.

Panduan Instalasi
1. Konfigurasi Database (MySQL via XAMPP)

Unduh dan Instal XAMPP: Unduh XAMPP dari situs resmi dan instal di komputer Anda.
Jalankan MySQL: Buka XAMPP Control Panel, lalu klik Start pada modul MySQL untuk memastikan statusnya Running.
Buat Database:
Akses http://localhost/phpmyadmin melalui browser.
Di tab Databases, masukkan nama library_db pada bagian Create database, lalu klik Create.


Buat Tabel:
Pilih database library_db dari panel kiri.
Buka tab SQL, lalu tempelkan dan jalankan perintah berikut:



-- Tabel Books
CREATE TABLE books (
book_id INT PRIMARY KEY AUTO_INCREMENT,
title VARCHAR(100) NOT NULL,
author VARCHAR(50) NOT NULL,
isbn VARCHAR(13) UNIQUE,
status VARCHAR(20) DEFAULT 'Available'
);

-- Tabel Borrowers
CREATE TABLE borrowers (
borrower_id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50) NOT NULL,
email VARCHAR(100) UNIQUE,
phone VARCHAR(15)
);

-- Tabel Transactions
CREATE TABLE transactions (
transaction_id INT PRIMARY KEY AUTO_INCREMENT,
book_id INT,
borrower_id INT,
borrow_date DATE NOT NULL,
return_date DATE,
FOREIGN KEY (book_id) REFERENCES books(book_id),
FOREIGN KEY (borrower_id) REFERENCES borrowers(borrower_id)
);


Masukkan Data Contoh (Opsional):
Untuk pengujian awal, jalankan perintah SQL berikut di tab SQL pada phpMyAdmin:



INSERT INTO books (title, author, isbn, status) VALUES
('Laskar Pelangi', 'Andrea Hirata', '9789793062792', 'Available'),
('Bumi Manusia', 'Pramoedya Ananta Toer', '9789799731234', 'Available');

INSERT INTO borrowers (name, email, phone) VALUES
('Budi Santoso', 'budi@example.com', '081234567890'),
('Siti Aminah', 'siti@example.com', '081987654321');

INSERT INTO transactions (book_id, borrower_id, borrow_date) VALUES
(1, 1, '2025-07-01');

2. Konfigurasi Proyek Java

Clone Repositori:
git clone https://github.com/YourUsername/LibraryManagementSystem.git
cd LibraryManagementSystem

Ganti YourUsername dengan nama pengguna GitHub Anda.

Buka Proyek di IntelliJ IDEA:

Buka IntelliJ IDEA, lalu pilih File > Open dan arahkan ke folder LibraryManagementSystem.
IntelliJ akan mendeteksi proyek Maven. Jika diminta, klik Load Maven Project atau Import Changes.


Verifikasi Dependensi Maven:

Buka file pom.xml di root proyek.
Pastikan dependensi untuk mysql-connector-java, flatlaf, dan flatlaf-themes telah tercantum.
Jika ada masalah dependensi, buka Maven tool window di IntelliJ IDEA dan klik ikon Reload All Maven Projects.


Rebuild Proyek:

Pilih Build > Rebuild Project dari menu IntelliJ IDEA untuk mengompilasi kode.



3. Jalankan Aplikasi

Pastikan modul MySQL di XAMPP sedang berjalan.
Di IntelliJ IDEA, navigasikan ke src/main/java/com/library/Main.java.
Klik kanan pada file Main.java, lalu pilih Run 'Main.main()'. Aplikasi akan terbuka dalam jendela baru.

Penggunaan Aplikasi

Tab Buku: Kelola koleksi buku (tambah, lihat, perbarui, hapus).
Tab Peminjam: Kelola data peminjam (tambah, lihat, perbarui, hapus).
Tab Transaksi: Catat peminjaman, tandai pengembalian, lihat riwayat, atau hapus transaksi.

Struktur Proyek
LibraryManagementSystem/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── library/
│   │   │           ├── dao/                  # Data Access Objects (interaksi DB)
│   │   │           │   ├── DatabaseConnection.java
│   │   │           │   ├── BookDAO.java
│   │   │           │   ├── BorrowerDAO.java
│   │   │           │   └── TransactionDAO.java
│   │   │           ├── model/                # Model Data (struktur data)
│   │   │           │   ├── Book.java
│   │   │           │   ├── Borrower.java
│   │   │           │   └── Transaction.java
│   │   │           ├── view/                 # Antarmuka Pengguna (GUI Swing)
│   │   │           │   ├── MainFrame.java
│   │   │           │   ├── BookPanel.java
│   │   │           │   ├── BorrowerPanel.java
│   │   │           │   └── TransactionPanel.java
│   │   │           └── Main.java             # Titik masuk aplikasi
│   │   └── resources/            # Untuk file seperti gambar/ikon (jika ada)
├── .gitignore                    # File untuk mengabaikan file tertentu dari Git
├── pom.xml                       # Konfigurasi proyek Maven
└── README.md                     # File dokumentasi proyek ini

Kontribusi
Kami menyambut kontribusi dalam bentuk laporan bug, permintaan fitur, atau pull request. Untuk berkontribusi:

Fork repositori ini.
Buat cabang baru: git checkout -b feature/nama-fitur-anda.
Lakukan perubahan dan commit: git commit -m "Deskripsi perubahan".
Push ke fork Anda: git push origin feature/nama-fitur-anda.
Buat Pull Request ke repositori utama.

Lisensi
Proyek ini dilisensikan di bawah MIT License.