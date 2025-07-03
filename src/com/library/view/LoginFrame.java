package com.library.view;

import com.formdev.flatlaf.FlatClientProperties;
import com.library.dao.UserDAO;
import com.library.dao.BorrowerDAO;
import com.library.model.User;
import com.library.model.Borrower;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private UserDAO userDAO;
    private BorrowerDAO borrowerDAO;

    public LoginFrame() {
        userDAO = new UserDAO();
        borrowerDAO = new BorrowerDAO();
        setTitle("Sistem Manajemen Perpustakaan - Login");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(UIManager.getColor("Panel.background"));

        mainPanel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc: 20;" +
                "background: #FFFFFF");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("SELAMAT DATANG", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(new Color(60, 63, 65));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(titleLabel, gbc);

        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.gridwidth = 2;

        // Username
        usernameField = new JTextField(20);
        usernameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Username");
        gbc.gridy = 2;
        mainPanel.add(usernameField, gbc);

        // Password
        passwordField = new JPasswordField(20);
        passwordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Password");
        gbc.gridy = 3;
        mainPanel.add(passwordField, gbc);

        // Login Button
        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setBackground(new Color(33, 150, 243));
        loginButton.setForeground(Color.WHITE);
        loginButton.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        gbc.gridy = 4; gbc.insets = new Insets(20, 0, 10, 0);
        mainPanel.add(loginButton, gbc);

        // Register Button
        registerButton = new JButton("Daftar Akun Baru");
        registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        registerButton.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_BORDERLESS);
        registerButton.setForeground(UIManager.getColor("Button.default.background"));
        gbc.gridy = 5; gbc.insets = new Insets(5, 0, 0, 0);
        mainPanel.add(registerButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> performLogin());
        passwordField.addActionListener(e -> performLogin());

        registerButton.addActionListener(e -> {
            RegisterFrame registerFrame = new RegisterFrame();
            registerFrame.setVisible(true);
        });

        try {
            if (userDAO.getUserByUsername("superadmin") == null) {
                userDAO.addUser("superadmin", "superpass", "Super Admin", "superadmin@example.com", "SUPER_ADMIN");
                User superAdminUser = userDAO.getUserByUsername("superadmin");
                if (superAdminUser != null && borrowerDAO.getBorrowerById(superAdminUser.getId()) == null) {
                    Borrower superAdminBorrower = new Borrower(superAdminUser.getId(), superAdminUser.getFullName(), superAdminUser.getEmail(), "");
                    borrowerDAO.addBorrower(superAdminBorrower);
                }
                System.out.println("User default: 'superadmin' (role: SUPER_ADMIN) dan peminjam dibuat.");
            }

            if (userDAO.getUserByUsername("admin") == null) {
                userDAO.addUser("admin", "adminpass", "Admin Perpustakaan", "admin@example.com", "ADMIN");
                User adminUser = userDAO.getUserByUsername("admin");
                if (adminUser != null && borrowerDAO.getBorrowerById(adminUser.getId()) == null) {
                    Borrower adminBorrower = new Borrower(adminUser.getId(), adminUser.getFullName(), adminUser.getEmail(), "");
                    borrowerDAO.addBorrower(adminBorrower);
                }
                System.out.println("User default: 'admin' (role: ADMIN) dan peminjam dibuat.");
            }

            if (userDAO.getUserByUsername("user") == null) {
                userDAO.addUser("user", "userpass", "Anggota Perpustakaan", "user@example.com", "USER");
                User regularUser = userDAO.getUserByUsername("user");
                if (regularUser != null && borrowerDAO.getBorrowerById(regularUser.getId()) == null) {
                    Borrower regularBorrower = new Borrower(regularUser.getId(), regularUser.getFullName(), regularUser.getEmail(), "");
                    borrowerDAO.addBorrower(regularBorrower);
                }
                System.out.println("User default: 'user' (role: USER) dan peminjam dibuat.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saat inisialisasi user default: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password tidak boleh kosong.", "Validasi Login", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            User loggedInUser = userDAO.login(username, password);
            if (loggedInUser != null) {
                if (loggedInUser.getRole().equals("USER") || loggedInUser.getRole().equals("ADMIN") || loggedInUser.getRole().equals("SUPER_ADMIN")) {
                    Borrower existingBorrower = borrowerDAO.getBorrowerById(loggedInUser.getId());
                    if (existingBorrower == null) {
                        Borrower newBorrower = new Borrower();
                        newBorrower.setId(loggedInUser.getId());
                        newBorrower.setName(loggedInUser.getFullName());
                        newBorrower.setEmail(loggedInUser.getEmail());
                        newBorrower.setPhone("");
                        borrowerDAO.addBorrower(newBorrower);
                        System.out.println("Peminjam baru dibuat saat login untuk user: " + loggedInUser.getUsername());
                    }
                }

                JOptionPane.showMessageDialog(this, "Login berhasil sebagai " + loggedInUser.getRole().replace("_", " ") + "!", "Login Sukses", JOptionPane.INFORMATION_MESSAGE);
                MainFrame mainFrame = new MainFrame(loggedInUser);
                mainFrame.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username atau password salah.", "Login Gagal", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saat login: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
