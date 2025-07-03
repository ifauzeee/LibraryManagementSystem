```markdown
# Library Management System

![Java Swing UI](https://img.shields.io/badge/UI-Java%20Swing-blue)
![Database](https://img.shields.io/badge/Database-MySQL-orange)
![Development](https://img.shields.io/badge/Development-Java%20JDK%2017-red)
![Build System](https://img.shields.io/badge/Build-Non--Maven%20(Manual%20JARs)-lightgrey)
![Look and Feel](https://img.shields.io/badge/L&F-FlatLaf-yellowgreen)

The **Library Management System** is a Java-based desktop application designed to streamline library operations, including book collections, borrower data, loan transactions, and user authentication. It features a modern, intuitive graphical user interface (GUI) built with Java Swing and enhanced with the FlatLaf theme for a web-inspired aesthetic. Data is securely stored and managed in a MySQL database.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Key Features](#key-features)
- [User Roles and Permissions](#user-roles-and-permissions)
- [Technologies Used](#technologies-used)
- [System Requirements](#system-requirements)
- [Project File Structure](#project-file-structure)
- [Installation and Setup](#installation-and-setup)
  - [1. Configure the Database (MySQL via XAMPP)](#1-configure-the-database-mysql-via-xampp)
  - [2. Configure the Java Project (IntelliJ IDEA)](#2-configure-the-java-project-intellij-idea)
  - [3. Run the Application](#3-run-the-application)
- [Default User Credentials](#default-user-credentials)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

---

## Project Overview

The Library Management System provides a robust solution for managing core library operations. Built with Java Swing, it offers a user-friendly interface with a modern, flat design powered by FlatLaf. The application supports a complete workflow for borrowing books, including user-initiated loan requests, admin approvals, and automated stock updates. All data is securely stored in a MySQL database with password hashing for enhanced security.

This project is ideal for small to medium-sized libraries looking to digitize their operations while maintaining simplicity and efficiency.

---

## Key Features

- **Authentication and Authorization:**
  - User registration for `USER` or `ADMIN` roles.
  - Secure login with password hashing using jBCrypt.
  - Role-based access control (`SUPER_ADMIN`, `ADMIN`, `USER`).

- **Book Management:**
  - CRUD operations (ADMIN/SUPER_ADMIN) for books (title, author, ISBN, total/available copies).
  - Real-time book availability status:
    - `Full (Ready)`: All copies available.
    - `Available (X/Y)`: X available out of Y total copies (e.g., `3/5`).
    - `Out of Stock`: No copies available.
  - Book listing viewable by all roles.

- **Borrower Management:**
  - CRUD operations (SUPER_ADMIN only) for borrower data (name, email, phone).
  - Automatic borrower entry creation/update during registration or login.

- **Loan Transaction Management:**
  - Loan requests by `USER` with `Pending` status.
  - Loan approval/rejection by `ADMIN`/`SUPER_ADMIN`, updating book stock.
  - Direct loan recording by `ADMIN`/`SUPER_ADMIN`.
  - Book returns by `USER`, updating status to `Returned` and restoring stock.
  - Transaction history view for `ADMIN`/`SUPER_ADMIN`.

- **Modern User Interface:**
  - Clean, flat design with **FlatLaf** (rounded buttons, subtle shadows, consistent typography).
  - Maximized dashboard window.
  - "Refresh Data" buttons for manual data updates.
  - Role-based visibility for tabs and action buttons.

---

## User Roles and Permissions

- **`SUPER_ADMIN`**:
  - Access: All tabs (Books, Borrowers, Transactions).
  - Permissions: Full CRUD on Books, Borrowers, Transactions; approve/reject loan requests.
- **`ADMIN`**:
  - Access: Books and Transactions tabs.
  - Permissions: Full CRUD on Books and Transactions (except borrower deletion); approve/reject loan requests.
- **`USER`**:
  - Access: Books tab only.
  - Permissions: View book list, submit loan requests, return borrowed books.

---

## Technologies Used

- **Programming Language:** Java (JDK 17)
- **GUI Framework:** Java Swing
- **Look and Feel:** [FlatLaf](https://www.formdev.com/flatlaf/) (FlatMacLightLaf theme)
- **Database:** [MySQL](https://www.mysql.com/)
- **Local Database Server:** [XAMPP](https://www.apachefriends.org/)
- **Password Hashing:** [jBCrypt](https://mvnrepository.com/artifact/org.mindrot/jbcrypt)
- **Database Connector:** MySQL Connector/J
- **IDE:** [IntelliJ IDEA](https://www.jetbrains.com/idea/) (recommended)

---

## System Requirements

- **Java Development Kit (JDK):** Version 17 or higher.
- **XAMPP:** Installed with MySQL module running.
- **Internet Connection:** Required for downloading external JAR files.

---

## Project File Structure

```plaintext
LibraryManagementSystem/
├── .idea/                       # IntelliJ IDEA configuration files (auto-generated)
├── out/                         # Compiled output (auto-generated during build)
├── lib/                         # Directory for manually downloaded JAR files
│   ├── mysql-connector-j-<version>.jar
│   ├── flatlaf-<version>.jar
│   ├── flatlaf-themes-<version>.jar
│   ├── jbcrypt-<version>.jar
├── src/                         # Source code
│   └── com/library/
│       ├── dao/             # Data Access Objects (database interactions)
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
├── images/                      # Screenshots or other images
├── .gitignore                   # Git ignore file
├── README.md                    # Project documentation
├── LICENSE                      # License file (e.g., MIT License)
```

---

## Installation and Setup

### 1. Configure the Database (MySQL via XAMPP)

1. **Install XAMPP:** Download and install from [https://www.apachefriends.org/](https://www.apachefriends.org/).
2. **Start MySQL:** Open XAMPP Control Panel, click **Start** for the MySQL module.
3. **Access MySQL Command Line:**
   - In XAMPP Control Panel, click **Shell** or **CMD** next to MySQL.
   - Run `mysql -u root -p`. Press `Enter` for no password (default for root).
   - Create/select database: Run `CREATE DATABASE library_db;` (if not exists), then `USE library_db;`.
4. **Create Table Schema:** Copy and paste the following SQL script into the MySQL Command Line Client and execute:

   ```sql
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
   ```

### 2. Configure the Java Project (IntelliJ IDEA)

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/ifauzeee/LibraryManagementSystem.git
   cd LibraryManagementSystem
   ```
   Replace `ifauzeee` with your GitHub username if forked.

2. **Open in IntelliJ IDEA:**
   - Launch IntelliJ IDEA.
   - Select `File` > `Open...` and choose the `LibraryManagementSystem` folder.

3. **Add External JARs:**
   - Create a `lib/` folder in the project root if it doesn’t exist.
   - Download and place these JARs in `lib/`:
      - [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) (e.g., `mysql-connector-j-8.0.33.jar`).
      - [FlatLaf Core](https://mvnrepository.com/artifact/com.formdev/flatlaf) (e.g., `flatlaf-3.4.jar`).
      - [FlatLaf Themes](https://mvnrepository.com/artifact/com.formdev/flatlaf-themes) (e.g., `flatlaf-themes-3.4.jar`).
      - [jBCrypt](https://mvnrepository.com/artifact/org.mindrot/jbcrypt) (e.g., `jbcrypt-0.4.jar`).
   - In IntelliJ, go to `File` > `Project Structure...` (`Ctrl+Alt+Shift+S`).
   - Select **Libraries**, click `+`, choose **Java**, select all JARs in `lib/`, and click `OK`.
   - Apply changes.

4. **Invalidate Caches and Restart:**
   - Go to `File` > `Invalidate Caches / Restart...` > **Invalidate and Restart**.

5. **Rebuild Project:**
   - Go to `Build` > **Rebuild Project**.

### 3. Run the Application

1. Ensure MySQL is running in XAMPP.
2. In IntelliJ, navigate to `src/com/library/Main.java`.
3. Right-click and select **Run 'Main.main()'**.

The application will launch with the login window.

---

## Default User Credentials

After resetting the database, these accounts are created:

- **SUPER_ADMIN**: Username: `superadmin`, Password: `superpass`
- **ADMIN**: Username: `admin`, Password: `adminpass`
- **USER**: Username: `user`, Password: `userpass`

---

## Screenshots

*Coming soon! Screenshots of the application will be added to the `images/` folder.*

<!-- Uncomment and update with actual image paths once uploaded to the repository
![Login Screen](images/login-screen.png)
![Dashboard](images/dashboard.png)
-->

---

## Contributing

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a branch: `git checkout -b feature/your-feature`.
3. Commit changes: `git commit -m 'Add your feature'`.
4. Push to your fork: `git push origin feature/your-feature`.
5. Create a Pull Request on GitHub.

---

## License

This project is licensed under the [MIT License](LICENSE). See the `LICENSE` file for details.

---

## Contact

For questions or issues, open an issue on the [GitHub repository](https://github.com/ifauzeee/LibraryManagementSystem/issues). Replace `ifauzeee` with your GitHub username if forked.

---

Thank you for using the Library Management System!
```