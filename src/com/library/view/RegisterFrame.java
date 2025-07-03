package com.library.view;

import com.formdev.flatlaf.FlatClientProperties; // BARU
import com.library.dao.UserDAO;
import com.library.dao.BorrowerDAO;
import com.library.model.Borrower;
import com.library.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JComboBox<String> roleComboBox;
    private JButton registerButton;
    private UserDAO userDAO;
    private BorrowerDAO borrowerDAO;

    public RegisterFrame() {
        userDAO = new UserDAO();
        borrowerDAO = new BorrowerDAO();
        setTitle("Sistem Manajemen Perpustakaan - Registrasi");
        setSize(450, 550); // Ukuran sedikit lebih besar
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(25, 40, 25, 40));
        mainPanel.setBackground(UIManager.getColor("Panel.background"));

        mainPanel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc: 20;" +
                "background: #FFFFFF;" +
                "shadowWidth: 15;" +
                "shadowColor: #00000033");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0); // Padding vertikal antar komponen
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("DAFTAR AKUN BARU", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(60, 63, 65));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0); // Margin bawah
        mainPanel.add(titleLabel, gbc);

        gbc.insets = new Insets(8, 0, 8, 0); // Reset insets
        gbc.gridwidth = 2;

        usernameField = new JTextField(20);
        usernameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Username");
        mainPanel.add(usernameField, gbc);

        passwordField = new JPasswordField(20);
        passwordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Password");
        gbc.gridy = 2;
        mainPanel.add(passwordField, gbc);

        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Konfirmasi Password");
        gbc.gridy = 3;
        mainPanel.add(confirmPasswordField, gbc);

        fullNameField = new JTextField(20);
        fullNameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nama Lengkap");
        gbc.gridy = 4;
        mainPanel.add(fullNameField, gbc);

        emailField = new JTextField(20);
        emailField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Email");
        gbc.gridy = 5;
        mainPanel.add(emailField, gbc);

        roleComboBox = new JComboBox<>(new String[]{"USER", "ADMIN"});
        gbc.gridy = 6;
        mainPanel.add(roleComboBox, gbc);

        registerButton = new JButton("DAFTAR");
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        registerButton.setBackground(new Color(76, 175, 80)); // Warna hijau
        registerButton.setForeground(Color.WHITE);
        registerButton.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        gbc.gridy = 7; gbc.insets = new Insets(20, 0, 0, 0);
        mainPanel.add(registerButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        registerButton.addActionListener(e -> performRegistration());
        confirmPasswordField.addActionListener(e -> performRegistration());
    }

    private void performRegistration() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String role = (String) roleComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username, Password, dan Nama Lengkap tidak boleh kosong.", "Validasi Registrasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Password dan konfirmasi password tidak cocok.", "Validasi Registrasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!email.isEmpty() && !email.matches("^[\\w.-]+@([\\w-]+\\.)+[A-Z a-z]{2,4}$")) {
            JOptionPane.showMessageDialog(this, "Format email tidak valid.", "Validasi Registrasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            boolean userAdded = userDAO.addUser(username, password, fullName, email, role);
            if (userAdded) {
                User newUser = userDAO.getUserByUsername(username);
                if (newUser != null && (newUser.getRole().equals("USER") || newUser.getRole().equals("ADMIN") || newUser.getRole().equals("SUPER_ADMIN"))) {
                    Borrower existingBorrower = borrowerDAO.getBorrowerById(newUser.getId());
                    if (existingBorrower == null) {
                        Borrower newBorrower = new Borrower();
                        newBorrower.setId(newUser.getId());
                        newBorrower.setName(fullName);
                        newBorrower.setEmail(email);
                        newBorrower.setPhone("");
                        borrowerDAO.addBorrower(newBorrower);
                        System.out.println("Peminjam otomatis dibuat untuk user: " + username + " dengan ID: " + newUser.getId());
                    }
                }

                JOptionPane.showMessageDialog(this, "Registrasi berhasil! Silakan login dengan akun Anda.", "Registrasi Sukses", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registrasi gagal. Username atau Email mungkin sudah digunakan.", "Registrasi Gagal", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saat registrasi: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}