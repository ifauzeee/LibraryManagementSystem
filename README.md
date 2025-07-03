
```markdown
# Library Management System

![Java Swing UI](https://img.shields.io/badge/UI-Java%20Swing-blue)
![Database](https://img.shields.io/badge/Database-MySQL-orange)
![Development](https://img.shields.io/badge/Development-Java%20JDK%2017-red)
![Build System](https://img.shields.io/badge/Build-Non--Maven%20(Manual%20JARs)-lightgrey)
![Look and Feel](https://img.shields.io/badge/L&F-FlatLaf-yellowgreen)

The **Library Management System** is a Java-based desktop application designed to streamline the management of library operations, including book collections, borrower data, loan transactions, and user authentication. It features a modern and intuitive graphical user interface (GUI) built with Java Swing and enhanced with the FlatLaf theme for a web-inspired aesthetic. Data is securely stored and managed in a MySQL database.

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
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

---

## Project Overview

The Library Management System provides a robust solution for managing core library operations. Built with Java Swing, it offers a user-friendly interface with a modern, flat design powered by FlatLaf. The application supports a complete workflow for borrowing books, including user-initiated loan requests, admin approvals, and automated stock updates. All data is securely stored in a MySQL database, with password hashing for enhanced security.

This project is ideal for small to medium-sized libraries looking to digitize their operations while maintaining simplicity and efficiency.

---

## Key Features

The application offers comprehensive functionality tailored to different user roles:

- **Authentication and Authorization:**
  - **User Registration:** Allows new users to create accounts with `USER` or `ADMIN` roles.
  - **Secure Login:** Authenticates users with password hashing using the jBCrypt library for enhanced security.
  - **Role-Based Access Control:** Restricts features and modules based on user roles (`SUPER_ADMIN`, `ADMIN`, `USER`).

- **Book Management:**
  - **CRUD Operations (ADMIN/SUPER_ADMIN):** Add, update, or delete books, including details like title, author, ISBN, and total/available copies.
  - **Dynamic Availability Status:** Displays real-time book availability in the table:
    - `Full (Ready)`: All copies are available.
    - `Available (X/Y)`: Shows available copies out of total copies (e.g., `3/5`).
    - `Out of Stock`: No copies available for borrowing.
  - **Book Listing:** Viewable by all roles, showing comprehensive book details and stock status.

- **Borrower Management:**
  - **CRUD Operations (SUPER_ADMIN only):** Manage borrower data, including name, email, and phone number. Borrower entries are automatically created/updated during user registration or login.

- **Loan Transaction Management:**
  - **Loan Requests (USER):** Users can select books, specify quantities, and submit loan requests with a `Pending` status.
  - **Loan Approval/Rejection (ADMIN/SUPER_ADMIN):** Admins review and approve or reject pending requests. Approvals automatically reduce available book copies.
  - **Direct Loan Recording (ADMIN/SUPER_ADMIN):** Admins can record loans directly with immediate approval.
  - **Book Returns (USER):** Users can mark borrowed books as returned, updating the transaction status to `Returned` and restoring available copies.
  - **Transaction History (ADMIN/SUPER_ADMIN):** View a complete history of all loan and return transactions.

- **Modern User Interface:**
  - Clean, flat, and modern design using **FlatLaf**, mimicking web-inspired aesthetics with rounded buttons, subtle shadows, and consistent typography.
  - Maximized dashboard window for optimal screen usage.
  - "Refresh Data" buttons on each tab for manual data updates.
  - Dynamic visibility of tabs and action buttons based on user role.

---

## User Roles and Permissions

The application implements role-based access control to restrict functionality:

- **`SUPER_ADMIN`**:
  - **Access:** All tabs (Books, Borrowers, Transactions).
  - **Permissions:** Full CRUD operations on Books, Borrowers, and Transactions; approve/reject loan requests.
- **`ADMIN`**:
  - **Access:** Books and Transactions tabs.
  - **Permissions:** Full CRUD operations on Books and Transactions (except borrower deletion); approve/reject loan requests.
- **`USER`**:
  - **Access:** Books tab only.
  - **Permissions:** View book list, submit loan requests, and return borrowed books.

---

## Technologies Used

- **Programming Language:** Java (JDK 17)
- **GUI Framework:** Java Swing
- **Look and Feel:** [FlatLaf](https://www.formdev.com/flatlaf/) (FlatMacLightLaf theme for modern visuals)
- **Database:** [MySQL](https://www.mysql.com/)
- **Local Database Server:** [XAMPP](https://www.apachefriends.org/) (for running MySQL)
- **Password Hashing:** [jBCrypt](https://mvnrepository.com/artifact/org.mindrot/jbcrypt) (for secure password storage)
- **Database Connector:** MySQL Connector/J (JDBC driver for Java-MySQL connectivity)
- **IDE:** [IntelliJ IDEA](https://www.jetbrains.com/idea/) (recommended for development)

---

## System Requirements

To compile and run the application, ensure your system meets the following requirements:

- **Java Development Kit (JDK):** Version 17 or higher.
- **XAMPP:** Installed with the MySQL module running.
- **Internet Connection:** Required for downloading external JAR files during initial setup.

---

## Project File Structure

The project follows a standard Java project structure for organized code:

```
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
├── README.md                    # Project documentation
├── LICENSE                      # License file (e.g., MIT License)
```

---

## Installation and Setup

Follow these steps to set up and run the application locally.

### 1. Configure the Database (MySQL via XAMPP)

1. **Download and Install XAMPP:** If not already installed, download and install XAMPP from [https://www.apachefriends.org/](https://www.apachefriends.org/).
2. **Start MySQL:** Open the **XAMPP Control Panel** and click **Start** on the **MySQL** module. Ensure the status changes to "Running".
3. **Access MySQL Command Line:**
   - In the XAMPP Control Panel, click **Shell** or **CMD** next to the MySQL module to open a terminal.
   - Log in to MySQL: Run `mysql -u root -p` and press `Enter`. If no password is set for the `root` user, press `Enter` again.
   - Select or create the database: Run `USE library_db;` to select the database. If it doesn’t exist, create it with `CREATE DATABASE library_db;` followed by `USE library_db;`.
4. **Reset and Create Table Schema:** Copy and paste the following SQL script into the MySQL Command Line Client and execute it as a single block. This script clears existing data, drops old tables, and creates a fresh schema with `AUTO_INCREMENT` starting from 1.

   ```sql
   SET FOREIGN_KEY_CHECKS = 0; -- Disable foreign key checks temporarily

   TRUNCATE TABLE transactions;
   TRUNCATE TABLE books;
   TRUNCATE TABLE borrowers;
   TRUNCATE TABLE users;

   -- Drop existing tables to ensure the latest schema
   DROP TABLE IF EXISTS transactions;
   DROP TABLE IF EXISTS books;
   DROP TABLE IF EXISTS borrowers;
   DROP TABLE IF EXISTS users;

   -- Books table
   CREATE TABLE books (
       book_id INT PRIMARY KEY AUTO_INCREMENT,
       title VARCHAR(100) NOT NULL,
       author VARCHAR(50) NOT NULL,
       isbn VARCHAR(13) UNIQUE,
       total_copies INT DEFAULT 1 NOT NULL,
       available_copies INT DEFAULT 1 NOT NULL
   );

   -- Borrowers table
   CREATE TABLE borrowers (
       borrower_id INT PRIMARY KEY AUTO_INCREMENT,
       name VARCHAR(50) NOT NULL,
       email VARCHAR(100) UNIQUE,
       phone VARCHAR(15)
   );

   -- Users table
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

   -- Transactions table
   CREATE TABLE transactions (
       transaction_id INT PRIMARY KEY AUTO_INCREMENT,
       book_id INT,
       borrower_id INT,
       borrow_date DATE NOT NULL,
       return_date DATE,
       status VARCHAR(20) DEFAULT 'Pending' NOT NULL, -- Pending, Approved, Rejected, Returned
       quantity INT DEFAULT 1 NOT NULL, -- Number of copies borrowed
       FOREIGN KEY (book_id) REFERENCES books(book_id),
       FOREIGN KEY (borrower_id) REFERENCES borrowers(borrower_id)
   );

   SET FOREIGN_KEY_CHECKS = 1; -- Re-enable foreign key checks
   ```

### 2. Configure the Java Project (IntelliJ IDEA)

1. **Clone the Repository:**
   Open a terminal or Git Bash and clone the repository:
   ```bash
   git clone https://github.com/ifauzeee/LibraryManagementSystem.git
   cd LibraryManagementSystem
   ```
   Replace `ifauzeee` with your GitHub username if you’ve forked the repository.

2. **Open the Project in IntelliJ IDEA:**
   - Launch IntelliJ IDEA.
   - Select `File` > `Open...` and navigate to the `LibraryManagementSystem` folder.

3. **Download and Add External JARs Manually:**
   - Create a `lib/` folder in the project root (`LibraryManagementSystem/lib/`) if it doesn’t exist.
   - Download the following JAR files and place them in the `lib/` folder:
      - **MySQL Connector/J:** Download from [dev.mysql.com/downloads/connector/j/](https://dev.mysql.com/downloads/connector/j/) (e.g., `mysql-connector-j-8.0.33.jar` or the latest compatible version).
      - **FlatLaf Core:** Download from [mvnrepository.com/artifact/com.formdev/flatlaf](https://mvnrepository.com/artifact/com.formdev/flatlaf) (use version `3.4` or later, e.g., `flatlaf-3.4.jar`).
      - **FlatLaf Themes:** Download from [mvnrepository.com/artifact/com.formdev/flatlaf-themes](https://mvnrepository.com/artifact/com.formdev/flatlaf-themes) (use version `3.4` or later, matching FlatLaf Core, e.g., `flatlaf-themes-3.4.jar`).
      - **jBCrypt:** Download from [mvnrepository.com/artifact/org.mindrot/jbcrypt](https://mvnrepository.com/artifact/org.mindrot/jbcrypt) (use version `0.4` or later, e.g., `jbcrypt-0.4.jar`).
   - In IntelliJ IDEA, go to `File` > `Project Structure...` (`Ctrl+Alt+Shift+S`).
   - In the `Project Structure` window, select **Libraries** under `Project Settings` in the left panel.
   - Click the **`+`** (Add) button, choose **Java**, navigate to the `lib/` folder, select all downloaded `.jar` files, and click `OK`.
   - Select the `LibraryManagementSystem` module if prompted, then click `OK`.

4. **Invalidate Caches and Restart IDE:**
   - Go to `File` > `Invalidate Caches / Restart...`.
   - Select **Invalidate and Restart** to clear the IDE’s internal cache and restart IntelliJ.

5. **Rebuild the Project:**
   - After IntelliJ restarts, go to `Build` > **Rebuild Project** to recompile the project from scratch.

### 3. Run the Application

1. Ensure the **MySQL** module in the **XAMPP Control Panel** is running.
2. In IntelliJ IDEA, navigate to `src/com/library/Main.java` in the Project Explorer.
3. Right-click `Main.java` and select **Run 'Main.main()'**.

The Library Management System will launch, displaying the login window.

---

## Default User Credentials

Upon first running the application after resetting the database, the following accounts are automatically created for testing:

- **SUPER_ADMIN:**
   - Username: `superadmin`
   - Password: `superpass`
- **ADMIN:**
   - Username: `admin`
   - Password: `adminpass`
- **USER:**
   - Username: `user`
   - Password: `userpass`

---

## Contributing

Contributions in the form of bug reports, feature requests, or pull requests are highly appreciated! To contribute to this project, follow these steps:

1. **Fork** the repository to your GitHub account.
2. Create a new branch for your feature or fix (`git checkout -b feature/new-feature-or-bug-fix`).
3. Make your changes to the codebase.
4. Commit your changes with a clear, descriptive message (`git commit -m 'feat: Add new feature X' ` or `fix: Resolve bug Y`).
5. Push your branch to your fork (`git push origin feature/new-feature-or-bug-fix`).
6. Create a **Pull Request** from your fork to the main repository, explaining your changes and their necessity.

---

## License

This project is licensed under the [MIT License](LICENSE). See the `LICENSE` file for details.

---

## Contact

For questions, suggestions, or issues, please open a new issue on the [GitHub repository](https://github.com/ifauzeee/LibraryManagementSystem/issues). Replace `ifauzeee` with your GitHub username if you’ve forked the repository.

---

Thank you for using the Library Management System! We hope this application streamlines your library operations effectively.
```

