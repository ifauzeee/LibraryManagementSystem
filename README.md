```markdown
# Library Management System

![Java Swing UI](https://img.shields.io/badge/UI-Java%20Swing-blue)
![Database](https://img.shields.io/badge/Database-MySQL-orange)
![Java](https://img.shields.io/badge/Java-JDK%2017-red)
![Build](https://img.shields.io/badge/Build-Manual%20JARs-lightgrey)
![Look and Feel](https://img.shields.io/badge/Look%20&%20Feel-FlatLaf-yellowgreen)

The Library Management System is a Java-based desktop application designed to streamline library operations, including book inventory management, borrower records, loan transactions, and user authentication. It features a modern, user-friendly graphical interface built with Java Swing and the FlatLaf look-and-feel, integrated with a MySQL database for secure and persistent data storage.

This project is hosted on GitHub: [ifauzeee/LibraryManagementSystem](https://github.com/ifauzeee/LibraryManagementSystem).

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [System Requirements](#system-requirements)
- [Installation and Setup](#installation-and-setup)
  - [1. Database Configuration (MySQL via XAMPP)](#1-database-configuration-mysql-via-xampp)
  - [2. Java Project Setup (IntelliJ IDEA)](#2-java-project-setup-intellij-idea)
  - [3. Running the Application](#3-running-the-application)
- [Default Credentials](#default-credentials)
- [Usage](#usage)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Features

The application provides robust functionality tailored to different user roles:

- **Authentication and Authorization:**
  - **User Registration:** Create accounts with `USER` or `ADMIN` roles.
  - **Secure Login:** Passwords are hashed using jBCrypt for enhanced security.
  - **Role-Based Access:**
    - `SUPER_ADMIN`: Full access, including user management.
    - `ADMIN

`: Manage books (create, read, update, delete), borrowers (CRUD), and loan transactions (approve, reject, record).
    - `USER`: View book catalog, request loans, and return borrowed books.

- **Book Management:**
  - **CRUD Operations:** Admins can add, update, or delete books and manage copy inventory.
  - **Book Listing:** Displays books with dynamic availability status (e.g., "Full (Ready)", "Available (X/Y)", "Out of Stock").
  
- **Borrower Management:**
  - **CRUD Operations:** Exclusive to `SUPER_ADMIN` for managing borrower data.

- **Loan Transaction Management:**
  - **Loan Requests:** Users can submit loan requests, pending admin approval.
  - **Approval/Rejection:** Admins can approve or reject requests, automatically updating book inventory.
  - **Direct Loan Recording:** Admins can record approved loans directly.
  - **Book Returns:** Users can return books, updating inventory and transaction status.
  - **Transaction History:** Admins can view a complete transaction log.

- **User Interface:**
  - Modern, flat design using **FlatLaf** (Light Mac Theme).
  - Maximized dashboard window on startup.
  - Role-based visibility for tabs and buttons.
  - "Refresh Data" button on each tab to update displayed data.

## Technologies Used

- **Programming Language:** Java (JDK 17)
- **GUI Framework:** Java Swing
- **Look and Feel:** [FlatLaf](https://www.formdev.com/flatlaf/) (Light Mac Theme)
- **Database:** [MySQL](https://www.mysql.com/)
- **Local Database Server:** [XAMPP](https://www.apachefriends.org/)
- **Password Hashing:** [jBCrypt](https://mvnrepository.com/artifact/org.mindrot/jbcrypt)
- **Database Connector:** MySQL Connector/J
- **Recommended IDE:** [IntelliJ IDEA](https://www.jetbrains.com/idea/)

## System Requirements

- **Java Development Kit (JDK):** Version 17 or higher
- **XAMPP:** With MySQL module running
- **Internet Connection:** Required for downloading external JAR files
- **Operating System:** Windows, macOS, or Linux (XAMPP and JDK compatible)
- **Disk Space:** At least 500 MB for project files, dependencies, and database

## Installation and Setup

Follow these steps to set up and run the application locally.

### 1. Database Configuration (MySQL via XAMPP)

1. **Install XAMPP:**
   - Download and install XAMPP from [apachefriends.org](https://www.apachefriends.org/).
   - Ensure the MySQL module is installed.

2. **Start MySQL:**
   - Open the XAMPP Control Panel.
   - Click **Start** for the MySQL module to ensure it is running.

3. **Access MySQL:**
   - In the XAMPP Control Panel, click **Shell** or **CMD** for MySQL.
   - Log in: `mysql -u root -p` (press Enter if no password is set).
   - Create or select the database: `CREATE DATABASE library_db;` or `USE library_db;`.

4. **Set Up Database Schema:**
   - Copy and execute the following SQL script in the MySQL Command Line Client to create the required tables:

    ```sql
    SET FOREIGN_KEY_CHECKS = 0;

    -- Clear existing data
    TRUNCATE TABLE transactions;
    TRUNCATE TABLE books;
    TRUNCATE TABLE borrowers;
    TRUNCATE TABLE users;

    -- Drop existing tables
    DROP TABLE IF EXISTS transactions;
    DROP TABLE IF EXISTS books;
    DROP TABLE IF EXISTS borrowers;
    DROP TABLE IF EXISTS users;

    -- Books Table
    CREATE TABLE books (
        book_id INT PRIMARY KEY AUTO_INCREMENT,
        title VARCHAR(100) NOT NULL,
        author VARCHAR(50) NOT NULL,
        isbn VARCHAR(13) UNIQUE,
        total_copies INT DEFAULT 1 NOT NULL,
        available_copies INT DEFAULT 1 NOT NULL
    );

    -- Borrowers Table
    CREATE TABLE borrowers (
        borrower_id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(50) NOT NULL,
        email VARCHAR(100) UNIQUE,
        phone VARCHAR(15)
    );

    -- Users Table
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

    -- Transactions Table
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

### 2. Java Project Setup (IntelliJ IDEA)

1. **Clone the Repository:**
    ```bash
    git clone https://github.com/ifauzeee/LibraryManagementSystem.git
    cd LibraryManagementSystem
    ```

2. **Open in IntelliJ IDEA:**
   - Launch IntelliJ IDEA.
   - Select `File` > `Open` and navigate to the `LibraryManagementSystem` folder.

3. **Add External JARs:**
   - Create a `lib/` folder in the project root (`LibraryManagementSystem/lib/`).
   - Download the following JAR files and place them in `lib/`:
     - `mysql-connector-j-<version>.jar` (e.g., `mysql-connector-j-8.0.33.jar`)
     - `flatlaf-<version>.jar` (e.g., `flatlaf-3.4.jar`)
     - `flatlaf-themes-<version>.jar` (e.g., `flatlaf-themes-3.4.jar`)
     - `jbcrypt-<version>.jar` (e.g., `jbcrypt-0.4.jar`)
   - In IntelliJ, go to `File` > `Project Structure` (`Ctrl+Alt+Shift+S`).
   - Under `Project Settings` > `Libraries`, click `+` > `Java`.
   - Navigate to `lib/`, select all JAR files, click `OK`, and apply changes.

4. **Rebuild Project:**
   - Go to `Build` > `Rebuild Project`.
   - If issues persist, select `File` > `Invalidate Caches / Restart` and choose `Invalidate and Restart`.

### 3. Running the Application

1. Ensure the MySQL module is running in the XAMPP Control Panel.
2. In IntelliJ IDEA, locate `src/com/library/Main.java`.
3. Right-click `Main.java` and select `Run 'Main.main()'`.

The application will launch, displaying the login window.

## Default Credentials

After resetting the database, the following default accounts are created automatically:

- **SUPER_ADMIN:**
  - Username: `superadmin`
  - Password: `superpass`
- **ADMIN:**
  - Username: `admin`
  - Password: `adminpass`
- **USER:**
  - Username: `user`
  - Password: `userpass`

## Usage

- **Login Screen:**
  - Log in using default credentials or register a new account.
- **Dashboard:**
  - **Header:** Displays the logged-in user’s name, role, and a Logout button.
  - **Books Tab:**
    - `USER`: View book catalog, check availability, request loans, or return borrowed books.
    - `ADMIN`/`SUPER_ADMIN`: Add, update, or delete books and manage inventory.
  - **Borrowers Tab:**
    - Visible only to `SUPER_ADMIN` for managing borrower records (CRUD).
  - **Transactions Tab:**
    - Visible to `ADMIN`/`SUPER_ADMIN` for reviewing, approving, rejecting, or recording loans.
  - **Refresh Data:** Each tab includes a button to refresh displayed data.

## Troubleshooting

- **MySQL Connection Issues:**
  - Ensure the MySQL module is running in XAMPP.
  - Verify the database `library_db` exists and the SQL schema is applied correctly.
  - Check MySQL credentials (default: `root`, no password).

- **JAR Dependency Errors:**
  - Confirm all required JARs are added to the `lib/` folder and included in IntelliJ’s project structure.
  - Rebuild the project or invalidate caches if errors persist.

- **Application Fails to Start:**
  - Ensure JDK 17 is installed and configured in IntelliJ (`File` > `Project Structure` > `SDK`).
  - Check the console for error messages and verify database connectivity.

- **UI Issues:**
  - Ensure FlatLaf and its theme JARs are correctly included.
  - Verify the project is built with JDK 17 for compatibility.

For further assistance, check the [GitHub Issues](https://github.com/ifauzeee/LibraryManagementSystem/issues) page or contact the maintainer.

## Contributing

Contributions are welcome! To contribute:

1. Fork the repository: [ifauzeee/LibraryManagementSystem](https://github.com/ifauzeee/LibraryManagementSystem).
2. Create a feature branch: `git checkout -b feature/your-feature-name`.
3. Commit changes with descriptive messages: `git commit -m "Add feature description"`.
4. Push the branch: `git push origin feature/your-feature-name`.
5. Submit a Pull Request via GitHub.

Please report bugs or suggest features using [GitHub Issues](https://github.com/ifauzeee/LibraryManagementSystem/issues).

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For questions, feedback, or support, please:
- Open an issue on [GitHub](https://github.com/ifauzeee/LibraryManagementSystem/issues).
- Contact the maintainer at [your-email@example.com] (replace with your actual email).

---

Thank you for using the Library Management System!
```

### Key Improvements
1. **Correct and Consistent Formatting:**
   - Used proper Markdown syntax: headers (`#`, `##`, `###`), bullet points, code blocks (`sql`, `bash`), and links.
   - Ensured consistent indentation and spacing for readability.
   - Added a **Table of Contents** with anchor links for easy navigation.

2. **Complete Content:**
   - Included all sections from your original README: features, technologies, requirements, installation, credentials, usage, and contributing.
   - Added **Troubleshooting** section to address common setup and runtime issues.
   - Included **License** and **Contact** sections for a complete, professional README.
   - Preserved the exact SQL schema, default credentials, and setup instructions.

3. **Professional and Formal Tone:**
   - Adhered to your preference for a formal tone, avoiding colloquial language.
   - Used clear, precise language suitable for a technical audience.

4. **Enhanced Clarity:**
   - Simplified complex instructions (e.g., database setup, JAR inclusion) for clarity.
   - Organized installation steps into numbered substeps for ease of use.
   - Used consistent English terminology (e.g., "borrower" instead of "peminjam").

5. **GitHub Integration:**
   - Linked to your repository: [https://github.com/ifauzeee/LibraryManagementSystem](https://github.com/ifauzeee/LibraryManagementSystem).
   - Ensured badges are functional and visually appealing.
   - Provided clear instructions for committing and pushing changes.

6. **Additional Best Practices:**
   - Added system requirements for disk space and OS compatibility.
   - Included a placeholder for a `LICENSE` file (you may need to create one, e.g., MIT License).
   - Provided a contact email placeholder (replace with your actual email or remove if not needed).

### Instructions to Apply
1. **Save the README:**
   - Copy the above content into a `README.md` file in your project’s root directory (`LibraryManagementSystem/`).
   - Use a text editor or IntelliJ IDEA to save the file.

2. **Commit and Push to GitHub:**
   - Open IntelliJ IDEA and go to the **Git** panel (`Alt+9`).
   - In the **Local Changes** tab, select `README.md`.
   - Enter a commit message, e.g., `Update README with correct formatting and complete content`.
   - Click **Commit**, then go to `Git` > `Push` (`Ctrl+Shift+K`) and push to the `main` branch.
   - Verify the updated README on GitHub: [https://github.com/ifauzeee/LibraryManagementSystem](https://github.com/ifauzeee/LibraryManagementSystem).

3. **Optional Enhancements:**
   - Create a `LICENSE` file in your repository (e.g., MIT License) to match the README reference.
   - Add screenshots or a demo GIF under a new **Screenshots** section to showcase the GUI.
   - Replace the placeholder email (`your-email@example.com`) with your actual contact email or remove the contact section if preferred.
   - Test the setup instructions on a fresh machine to ensure accuracy.

If you meant something specific by "benar dan lengkap" (e.g., additional sections, specific formatting conventions, or inclusion of other project details), please provide further details, and I can refine the README accordingly. Let me know if you need assistance with other project aspects, such as code debugging or GitHub configuration!