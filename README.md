
---

# 📚 Library Management System

![Java](https://img.shields.io/badge/Java-JDK%2017-red)  
![Swing](https://img.shields.io/badge/UI-Swing-blue)  
![MySQL](https://img.shields.io/badge/Database-MySQL-orange)  
![FlatLaf](https://img.shields.io/badge/Look%20&%20Feel-FlatLaf-yellowgreen)  
![License](https://img.shields.io/badge/License-MIT-green)

The **Library Management System** is a modern, Java-based desktop application designed to streamline library operations. Built with **Java Swing** and styled with the **FlatLaf** theme, it offers a visually appealing and intuitive user interface. The system manages books, borrowers, loan transactions, and user authentication, with data securely stored in a **MySQL** database using **jBCrypt** for password hashing.

Ideal for small to medium-sized libraries seeking to digitize operations with simplicity and efficiency! 🚀

---

## 📋 Table of Contents

- [✨ Overview](#overview)
- [🔑 Key Features](#key-features)
- [👥 User Roles](#user-roles)
- [🛠️ Technologies](#technologies)
- [📦 Prerequisites](#prerequisites)
- [📁 Project Structure](#project-structure)
- [⚙️ Setup Instructions](#setup-instructions)
   - [1. Configure MySQL Database with XAMPP](#1-configure-mysql-database-with-xampp)
   - [2. Set Up Java Project in IntelliJ IDEA](#2-set-up-java-project-in-intellij-idea)
   - [3. Run the Application](#3-run-the-application)
- [🔐 Default Credentials](#default-credentials)
- [📸 Screenshots](#screenshots)
- [🤝 Contributing](#contributing)
- [📜 License](#license)
- [📬 Contact](#contact)

---

## ✨ Overview

The **Library Management System** is a robust desktop application designed to simplify library operations. It provides an intuitive interface for managing books, borrowers, and loan transactions, with secure user authentication and role-based access control. The system leverages **Java Swing** for its GUI, enhanced with **FlatLaf** for a modern, web-inspired look, and stores data in a **MySQL** database for reliability and performance.

This application is perfect for libraries aiming to transition from manual to digital operations with minimal complexity.

---

## 🔑 Key Features

- **🔒 Secure Authentication**:
   - User registration for `USER` and `ADMIN` roles.
   - Passwords hashed with **jBCrypt** for enhanced security.
   - Role-based access control for `SUPER_ADMIN`, `ADMIN`, and `USER`.

- **📚 Book Management**:
   - Full CRUD operations (ADMIN/SUPER_ADMIN) for books (title, author, ISBN, copies).
   - Real-time availability tracking:
      - 🟢 **Full (Ready)**: All copies available.
      - 🟡 **Available (X/Y)**: X of Y copies available (e.g., `3/5`).
      - 🔴 **Out of Stock**: No copies available.
   - Book listing accessible to all users.

- **👤 Borrower Management**:
   - CRUD operations for borrower data (SUPER_ADMIN only).
   - Automatic updates to borrower records during registration/login.

- **📝 Loan Transactions**:
   - Users can submit loan requests (`Pending` status).
   - Admins can approve/reject requests, updating book stock automatically.
   - Direct loan recording by admins.
   - Book return functionality with status updates to `Returned`.
   - Comprehensive transaction history for admins.

- **🎨 Modern User Interface**:
   - Sleek, web-inspired design with **FlatLaf** (rounded buttons, subtle shadows).
   - Maximized dashboard for optimal screen utilization.
   - Manual "Refresh Data" buttons for real-time updates.
   - Role-based visibility for tabs and buttons.

---

## 👥 User Roles

| Role          | Accessible Modules               | Permissions                                  |
|---------------|----------------------------------|----------------------------------------------|
| **SUPER_ADMIN** | Books, Borrowers, Transactions | Full CRUD on all modules; approve/reject loans |
| **ADMIN**       | Books, Transactions            | CRUD on Books/Transactions (no borrower deletion); approve/reject loans |
| **USER**        | Books                          | View books, submit loan requests, return books |

---

## 🛠️ Technologies

- **Programming Language**: Java (JDK 17)
- **GUI Framework**: Java Swing
- **Look & Feel**: [FlatLaf](https://www.formdev.com/flatlaf/) (FlatMacLightLaf)
- **Database**: [MySQL](https://www.mysql.com/)
- **Server**: [XAMPP](https://www.apachefriends.org/)
- **Security**: [jBCrypt](https://mvnrepository.com/artifact/org.mindrot/jbcrypt)
- **Database Connector**: MySQL Connector/J
- **IDE (Recommended)**: [IntelliJ IDEA](https://www.jetbrains.com/idea/)

---

## 📦 Prerequisites

To set up and run the application, ensure the following are installed:
- **Java Development Kit (JDK)**: Version 17 or higher
- **XAMPP**: With MySQL module enabled
- **Internet Connection**: Required for downloading external JAR dependencies
- **IntelliJ IDEA**: Recommended for project setup and development

---

## 📁 Project Structure

```plaintext
LibraryManagementSystem/
├── .idea/                       # IntelliJ IDEA configuration
├── out/                         # Compiled output
├── lib/                         # External JAR dependencies
│   ├── mysql-connector-j-<version>.jar
│   ├── flatlaf-<version>.jar
│   ├── flatlaf-themes-<version>.jar
│   ├── jbcrypt-<version>.jar
├── src/                         # Source code
│   └── com/library/
│       ├── dao/             # Database access objects
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
├── database_setup.sql           # SQL script for database initialization
├── .gitignore                   # Git ignore file
├── README.md                    # Project documentation
├── LICENSE                      # MIT License
```

---

## ⚙️ Setup Instructions

Follow these steps to set up and run the **Library Management System** locally.

### 1. Configure MySQL Database with XAMPP

1. **Install XAMPP**:
   - Download and install XAMPP from [apachefriends.org](https://www.apachefriends.org/).
   - Start the XAMPP Control Panel and enable the **MySQL** module.

2. **Set Up the Database**:
   - Open the XAMPP Control Panel and click **Shell** or **CMD** next to MySQL.
   - Log in to MySQL: `mysql -u root -p` (press Enter if no password is set).
   - Create the database: `CREATE DATABASE library_db;`
   - Select the database: `USE library_db;`
   - Copy the contents of `database_setup.sql` (located in the project root) and execute them in the MySQL shell to create the required tables.

   **Alternative**: Use phpMyAdmin (accessible via XAMPP) to import `database_setup.sql`.

3. **Troubleshooting**:
   - If you encounter "Access denied" errors, verify that MySQL is running and the root user has no password.
   - Ensure the `library_db` database is created before running the application.

### 2. Set Up Java Project in IntelliJ IDEA

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/ifauzeee/LibraryManagementSystem.git
   cd LibraryManagementSystem
   ```

2. **Open the Project**:
   - Launch IntelliJ IDEA.
   - Go to **File > Open** and select the `LibraryManagementSystem` folder.

3. **Add External JARs**:
   - Create a `lib/` folder in the project root if it doesn't exist.
   - Download the following JARs and place them in `lib/`:
      - [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) (e.g., `mysql-connector-j-8.0.33.jar`)
      - [FlatLaf Core](https://mvnrepository.com/artifact/com.formdev/flatlaf) (e.g., `flatlaf-3.4.jar`)
      - [FlatLaf Themes](https://mvnrepository.com/artifact/com.formdev/flatlaf-themes) (e.g., `flatlaf-themes-3.4.jar`)
      - [jBCrypt](https://mvnrepository.com/artifact/org.mindrot/jbcrypt) (e.g., `jbcrypt-0.4.jar`)

   - In IntelliJ, go to **File > Project Structure > Libraries > + > Java**, select all JARs in `lib/`, and click **OK**.

4. **Rebuild the Project**:
   - Go to **Build > Rebuild Project**.
   - If errors persist, select **File > Invalidate Caches / Restart > Invalidate and Restart**.

### 3. Run the Application

1. Ensure the MySQL server is running in XAMPP.
2. In IntelliJ, navigate to `src/com/library/Main.java`.
3. Right-click and select **Run 'Main.main()'**.
4. The login window should appear upon successful launch.

**Troubleshooting**:
- If the application fails to connect to the database, verify that MySQL is running and the `library_db` database exists.
- Check that the MySQL Connector/J JAR is correctly added to the project.

---

## 🔐 Default Credentials

After setting up the database, the following default accounts are created:

| Role          | Username   | Password   |
|---------------|------------|------------|
| **SUPER_ADMIN** | superadmin | superpass  |
| **ADMIN**       | admin      | adminpass  |
| **USER**        | user       | userpass   |

**Note**: Change these credentials after the first login for security.

---

## 📸 Screenshots

Screenshots are located in the `images/` folder. [Add placeholders or specific image paths if available, e.g., `![Login Screen](images/login.png)`]

- **Login Screen**: Displays the authentication interface.
- **Dashboard**: Shows role-specific tabs and features.
- **Book Management**: Interface for adding, updating, and viewing books.
- **Transaction History**: Overview of loan requests and statuses.

---

## 🤝 Contributing

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a feature branch: `git checkout -b feature/your-feature`.
3. Commit your changes: `git commit -m "Add your feature"`.
4. Push to your fork: `git push origin feature/your-feature`.
5. Submit a Pull Request via GitHub.

Please adhere to the project's **Code of Conduct** and ensure code follows the existing style guidelines.

---

## 📜 License

This project is licensed under the [MIT License](LICENSE).

---

## 📬 Contact

For questions, suggestions, or issues, please open an issue on the [GitHub repository](https://github.com/ifauzeee/LibraryManagementSystem) or contact the maintainer.

⭐ **Thank you for exploring the Library Management System!** We hope it empowers your library to operate more efficiently. ⭐

---