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
   - [1. Database Setup (MySQL via XAMPP)](#1-database-setup-mysql-via-xampp)
   - [2. Java Project Setup (IntelliJ IDEA)](#2-java-project-setup-intellij-idea)
   - [3. Running the Application](#3-running-the-application)
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
- **User Authentication**: Secure login and role-based access control for `SUPER_ADMIN`, `ADMIN`, and `USER`.

Built with **Java Swing** and styled with **FlatLaf**, it delivers a modern, web-inspired look. Data is stored securely in a **MySQL** database, ensuring reliability and performance.

---

## 🔑 Key Features

- **🔒 Authentication**:
   - User registration for `USER` and `ADMIN` roles.
   - Secure login with **jBCrypt** password hashing.
   - Role-based access control for restricted functionality.

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
├── .gitignore                   # Git ignore
├── README.md                    # Documentation
├── LICENSE                      # MIT License


⚙️ Setup Guide
1. Database Setup (MySQL via XAMPP)

Install XAMPP: Download from apachefriends.org.

Start MySQL: Open XAMPP Control Panel, click Start for MySQL.

Access MySQL:

In XAMPP, click Shell or CMD for MySQL.
Run: mysql -u root -p (press Enter for no password).
Create/select database: CREATE DATABASE library_db; then USE library_db;.


Run SQL Script: Paste and execute this script in MySQL Command Line Client:
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE transactions;
TRUNCATE TABLE books;
TRUNCATE TABLE borrowers;
TRUNCATE TABLE users;

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
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    book_id INT,
    borrower_id INT,
    borrow_date DATE NOT NULL,
    return_date DATE,
    status VARCHAR(20) DEFAULT 'Pending' NOT NULL,
    quantity INT DEFAULT 1 NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (borrower_id) REFERENCES borrowers(borrower_id)
);

SET FOREIGN_KEY_CHECKS = 1;



2. Java Project Setup (IntelliJ IDEA)

Clone Repository:
git clone https://github.com/ifauzeee/LibraryManagementSystem.git
cd LibraryManagementSystem

Replace ifauzeee with your GitHub username if forked.

Open in IntelliJ:

Launch IntelliJ IDEA.
Select File > Open... > LibraryManagementSystem folder.


Add JARs:

Create lib/ folder in project root.
Download and place these JARs in lib/:
MySQL Connector/J (e.g., mysql-connector-j-8.0.33.jar)
FlatLaf Core (e.g., flatlaf-3.4.jar)
FlatLaf Themes (e.g., flatlaf-themes-3.4.jar)
jBCrypt (e.g., jbcrypt-0.4.jar)


In IntelliJ: File > Project Structure... > Libraries > + > Java > select all JARs in lib/ > OK.


Invalidate Caches:

File > Invalidate Caches / Restart... > Invalidate and Restart.


Rebuild Project:

Build > Rebuild Project.



3. Running the Application

Ensure MySQL is running in XAMPP.
In IntelliJ, go to src/com/library/Main.java.
Right-click > Run 'Main.main()'.

The login window will appear.

🔐 Default Credentials
After database reset, these accounts are auto-created:



Role
Username
Password



SUPER_ADMIN
superadmin
superpass


ADMIN
admin
adminpass


USER
user
userpass



📸 Screenshots
Upload screenshots to the images/ folder and update paths below.



🤝 Contributing
We welcome contributions! To contribute:

Fork the repository.
Create a branch: git checkout -b feature/your-feature.
Commit changes: git commit -m 'Add your feature'.
Push to your fork: git push origin feature/your-feature.
Create a Pull Request on GitHub.

Please follow the Code of Conduct.

📜 License
This project is licensed under the MIT License.

📬 Contact
For issues or suggestions, open an issue on the GitHub repository. Replace ifauzeee with your GitHub username if forked.

⭐ Thank you for exploring the Library Management System! We hope it simplifies your library operations. ⭐```