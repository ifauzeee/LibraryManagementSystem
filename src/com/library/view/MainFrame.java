package com.library.view;

import com.formdev.flatlaf.FlatClientProperties;
import com.library.model.User;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

// Pastikan semua import ini ada dan tidak ada yang terduplikasi atau salah ketik
import com.library.view.BookPanel;
import com.library.view.BorrowerPanel;
import com.library.view.TransactionPanel;

public class MainFrame extends JFrame {

    private User currentUser;

    public MainFrame(User currentUser) {
        this.currentUser = currentUser;

        setTitle("Sistem Manajemen Perpustakaan - " + currentUser.getRole().replace("_", " "));
        setSize(1000, 700); // Ukuran default (akan ditimpa oleh setExtendedState)
        setExtendedState(JFrame.MAXIMIZED_BOTH); // <-- BARIS INI AKAN MEMAKSIMALKAN JENDELA
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Ini akan kurang relevan karena sudah dimaksimalkan

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(45, 55, 72)); // Warna header lebih gelap dan elegan
        headerPanel.setPreferredSize(new Dimension(getWidth(), 80)); // Header lebih tinggi

        JLabel titleLabel = new JLabel(" SISTEM MANAJEMEN PERPUSTAKAAN", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32)); // Font lebih besar dan tebal
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));

        JPanel userInfoAndLogoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0)); // Padding horizontal
        userInfoAndLogoutPanel.setOpaque(false); // Agar background header terlihat

        JLabel userInfoLabel = new JLabel("Welcome, " + currentUser.getFullName() + " (" + currentUser.getRole().replace("_", " ").toUpperCase() + ")");
        userInfoLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        userInfoLabel.setForeground(new Color(173, 216, 230)); // Warna biru muda
        userInfoAndLogoutPanel.add(userInfoLabel);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setBackground(new Color(220, 53, 69)); // Merah untuk logout
        logoutButton.setForeground(Color.WHITE);
        logoutButton.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT); // Tombol membulat
        logoutButton.setPreferredSize(new Dimension(100, 38)); // Ukuran tombol logout
        userInfoAndLogoutPanel.add(logoutButton);

        headerPanel.add(titleLabel, BorderLayout.WEST); // Title di kiri
        headerPanel.add(userInfoAndLogoutPanel, BorderLayout.EAST); // User info dan logout di kanan

        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin logout?", "Konfirmasi Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                this.dispose();
            }
        });

        add(headerPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        // Properti tabbedPane global sudah diatur di Main.java (font, height, underline)

        tabbedPane.addTab("Buku", new BookPanel(currentUser)); // BookPanel butuh objek User lengkap

        // Logika Kontrol Visibilitas Tab
        // Tab "Peminjam" hanya untuk SUPER_ADMIN
        if (currentUser.getRole().equals("SUPER_ADMIN")) {
            tabbedPane.addTab("Peminjam", new BorrowerPanel(currentUser.getRole()));
        }

        // Tab "Transaksi" hanya untuk ADMIN dan SUPER_ADMIN
        if (currentUser.getRole().equals("ADMIN") || currentUser.getRole().equals("SUPER_ADMIN")) {
            tabbedPane.addTab("Transaksi", new TransactionPanel(currentUser.getRole()));
        }

        // Tambahkan tab management User hanya untuk SUPER_ADMIN (jika diimplementasikan)
        // if (currentUser.getRole().equals("SUPER_ADMIN")) {
        //    tabbedPane.addTab("Manajemen User", new UserManagementPanel(currentUser.getRole()));
        // }

        add(tabbedPane, BorderLayout.CENTER);
    }
}