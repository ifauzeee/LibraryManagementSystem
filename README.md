# 📚 Library Management System

![Java](https://img.shields.io/badge/Java-JDK%2017-red)  
![Swing](https://img.shields.io/badge/UI-Swing-blue)  
![MySQL](https://img.shields.io/badge/Database-MySQL-orange)  
![FlatLaf](https://img.shields.io/badge/Look%20&%20Feel-FlatLaf-yellowgreen)  
![License](https://img.shields.io/badge/License-MIT-green)  

The **Library Management System** is a modern, Java-based desktop application designed to streamline library operations. Built with **Java Swing** and enhanced with the **FlatLaf** theme, it offers an intuitive and visually appealing interface. The system manages books, borrowers, loan transactions, and user authentication, with data securely stored in a **MySQL** database using **jBCrypt** for password hashing.

Perfect for small to medium-sized libraries aiming to digitize their operations with simplicity and efficiency! 🚀

---

## 📋 Table of Contents

- [✨ Overview](#overview)
- [🔑 Key Features](#key-features)
- [👥 User Roles](#user-roles)
- [🛠️ Technologies](#technologies)
- [📦 Requirements](#requirements)
- [📁 Project Structure](#project-structure)
- [⚙️ Setup Guide](#setup-guide)
  - [1. Set Up MySQL Database with XAMPP](#1-set-up-mysql-database-with-xampp)
  - [2. Configure Java Project in IntelliJ IDEA](#2-configure-java-project-in-intellij-idea)
  - [3. Launch the Application](#3-launch-the-application)
- [🔐 Default Credentials](#default-credentials)
- [📸 Screenshots](#screenshots)
- [🤝 Contributing](#contributing)
- [📜 License](#license)
- [📬 Contact](#contact)

---

## ✨ Overview

The Library Management System simplifies library operations with a sleek, user-friendly interface. It supports:

- **Book Management**: Add, update, or delete books with real-time availability tracking.
- **Borrower Management**: Manage borrower details (exclusive to SUPER_ADMIN).
- **Loan Transactions**: Handle loan requests, approvals, and returns with automated stock updates.
- **User  Authentication**: Secure login and role-based access control for `SUPER_ADMIN`, `ADMIN`, and `USER`.

Built with **Java Swing** and styled with **FlatLaf**, it delivers a modern, web-inspired look. Data is stored securely in a **MySQL** database, ensuring reliability and performance.

---

## 🔑 Key Features

- **🔒 Authentication**:
  - User registration for `USER` and `ADMIN` roles.
  - Secure login with **jBCrypt** password hashing.
  - Role-based access control (`SUPER_ADMIN`, `ADMIN`, `USER`).

- **📚 Book Management**:
  - CRUD operations (ADMIN/SUPER_ADMIN) for books (title, author, ISBN, copies).
  - Real-time availability:
    - 🟢 `Full (Ready)`: All copies available.
    - 🟡 `Available (X/Y)`: X of Y copies available (e.g., `3/5`).
    - 🔴 `Out of Stock`: No copies available.
  - Book listing accessible to all roles.

- **👤 Borrower Management**:
  - CRUD operations (SUPER_ADMIN only) for borrower data (name, email, phone).
  - Automatic borrower entry updates during registration/login.

- **📝 Loan Transactions**:
  - Users submit loan requests (`Pending` status).
  - Admins approve/reject requests, updating stock.
  - Direct loan recording by admins.
  - Users return books, updating status to `Returned`.
  - Transaction history for admins.

- **🎨 Modern UI**:
  - Flat, web-inspired design with **FlatLaf** (rounded buttons, subtle shadows).
  - Maximized dashboard for optimal screen use.
  - "Refresh Data" buttons for manual updates.
  - Role-based tab/button visibility.

---

## 👥 User Roles

| Role          | Access                              | Permissions                                  |
|---------------|-------------------------------------|----------------------------------------------|
| **SUPER_ADMIN** | Books, Borrowers, Transactions | Full CRUD on all; approve/reject loans       |
| **ADMIN**       | Books, Transactions            | CRUD on Books/Transactions (no borrower deletion); approve/reject loans |
| **USER**        | Books                          | View books, submit loan requests, return books |

---

## 🛠️ Technologies

- **Language**: Java (JDK 17)
- **GUI**: Java Swing
- **Look & Feel**: [FlatLaf](https://www.formdev.com/flatlaf/) (FlatMacLightLaf)
- **Database**: [MySQL](https://www.mysql.com/)
- **Server**: [XAMPP](https://www.apachefriends.org/)
- **Security**: [jBCrypt](https://mvnrepository.com/artifact/org.mindrot/jbcrypt)
- **Connector**: MySQL Connector/J
- **IDE**: [IntelliJ IDEA](https://www.jetbrains.com/idea/) (recommended)

---

## 📦 Requirements

- **JDK**: Version 17 or higher
- **XAMPP**: MySQL module running
- **Internet**: For downloading JAR dependencies

---

## 📁 Project Structure

```plaintext
LibraryManagementSystem/
├── .idea/                       # IntelliJ IDEA config (auto-generated)
├── out/                         # Compiled output (auto-generated)
├── lib/                         # External JARs
│   ├── mysql-connector-j-<version>.jar
│   ├── flatlaf-<version>.jar
│   ├── flatlaf-themes-<version>.jar
│   ├── jbcrypt-<version>.jar
├── src/                         # Source code
│   └── com/library/
│       ├── dao/             # Database interactions
│       │   ├── BookDAO.java
│       │   ├── BorrowerDAO.java
│       │   ├── TransactionDAO.java
│       │   ├── UserDAO.java
│       ├── model/           # Data models
│       │   ├── Book.java
│       │   ├── Borrower.java
│       │   ├── Transaction.java
│       │   ├── User.java
│       ├── view/            # GUI components
│       │   ├── BookPanel.java
│       │   ├── BorrowerPanel.java
│       │   ├── LoginFrame.java
│       │   ├── MainFrame.java
│       │   ├── RegisterFrame.java
│       │   ├── TransactionPanel.java
│       ├── Main.java         # Application entry point
├── images/                      # Screenshots
├── database_setup.sql           # SQL script for database setup
├── .gitignore                   # Git ignore
├── README.md                    # Documentation
├── LICENSE                      # MIT License
```

---

## ⚙️ Setup Guide

Follow these steps to set up and run the application locally. Ensure all requirements are met before proceeding.

### 1. Set Up MySQL Database with XAMPP

- **Install XAMPP**:
   - Download and install from [apachefriends.org](https://www.apachefriends.org/).
   - Launch XAMPP Control Panel and start the MySQL module.

- **Access MySQL**:
   - In XAMPP, click Shell or CMD next to MySQL.
   - Run: `mysql -u root -p` (press Enter if no password is set for the root user).
   - Create database: `CREATE DATABASE library_db;` then `USE library_db;`.

- **Run SQL Script**:
   - Copy the SQL script from `database_setup.sql` in the project root.
   - Paste and execute it in the MySQL Command Line Client to create tables.
   - Note: If you encounter errors (e.g., "Access denied"), ensure MySQL is running and no password is set for root. Alternatively, use phpMyAdmin to import the script.

### 2. Configure Java Project in IntelliJ IDEA

- **Clone Repository**:
  ```bash
  git clone https://github.com/ifauzeee/LibraryManagementSystem.git
  cd LibraryManagementSystem
  ```

- **Open Project**:
   - Launch IntelliJ IDEA.
   - Select File > Open... and choose folder `LibraryManagementSystem`.

- **Add External JARs**:
   - Create folder `lib/` di root proyek jika belum ada.
   - Download JAR berikut dan tempatkan di `lib/`:
      - MySQL Connector/J (e.g., `mysql-connector-j-8.0.33.jar`)
      - FlatLaf Core (e.g., `flatlaf-3.4.jar`)
      - FlatLaf Themes (e.g., `flatlaf-themes-3.4.jar`)
      - jBCrypt (e.g., `jbcrypt-0.4.jar`)

   - Di IntelliJ: File > Project Structure... > Libraries > + > Java > pilih semua JAR di `lib/` > OK.

- **Rebuild Project**:
   - Pergi ke Build > Rebuild Project.
   - Jika IntelliJ menunjukkan kesalahan, pilih File > Invalidate Caches / Restart... > Invalidate and Restart.

### 3. Launch the Application

- Pastikan MySQL berjalan di XAMPP.
- Di IntelliJ, navigasikan ke `src/com/library/Main.java`.
- Klik kanan dan pilih Run 'Main.main()'.
- Catatan: Jika aplikasi gagal terhubung ke database, verifikasi MySQL berjalan dan database `library_db` ada.

Jendela login akan muncul setelah peluncuran berhasil.

---

## 🔐 Default Credentials

Setelah mereset database, akun-akun berikut akan dibuat secara otomatis:

| Role          | Username   | Password   |
|---------------|------------|------------|
| **SUPER_ADMIN** | superadmin | superpass  |
| **ADMIN**       | admin      | adminpass  |
| **USER**        | user       | userpass   |

---

## 📸 Screenshots

Upload screenshot ke folder `images/` dan perbarui jalur di bawah ini.

---

## 🤝 Contributing

We welcome contributions! To contribute:

- Fork the repository.
- Create a branch: `git checkout -b feature/your-feature`.
- Commit changes: `git commit -m 'Add your feature'`.
- Push to your fork: `git push origin feature/your-feature`.
- Create a Pull Request on GitHub.

Please follow the Code of Conduct.

---

## 📜 License

This project is licensed under the MIT License.

---

## 📬 Contact

⭐ Thank you for exploring the Library Management System! We hope it simplifies your library operations. ⭐
```