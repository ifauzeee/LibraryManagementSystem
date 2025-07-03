package com.library.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Sistem Manajemen Perpustakaan");
        setSize(1000, 700); // Ukuran jendela lebih besar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Menempatkan jendela di tengah layar

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(60, 63, 65)); // Warna gelap untuk header
        headerPanel.setPreferredSize(new Dimension(getWidth(), 70)); // Tinggi header

        JLabel titleLabel = new JLabel(" SISTEM MANAJEMEN PERPUSTAKAAN", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28)); // Font lebih besar
        titleLabel.setForeground(Color.WHITE); // Warna teks putih
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // Padding kiri

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Tabbed pane untuk Books, Borrowers, Transactions
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Font tab
        tabbedPane.addTab("Buku", new BookPanel());
        tabbedPane.addTab("Peminjam", new BorrowerPanel());
        tabbedPane.addTab("Transaksi", new TransactionPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }
}