package com.library.view;

import com.formdev.flatlaf.FlatClientProperties; // BARU
import com.library.dao.BorrowerDAO;
import com.library.model.Borrower;
import com.library.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class BorrowerPanel extends JPanel {
    private JTable borrowerTable;
    private DefaultTableModel tableModel;
    private BorrowerDAO borrowerDAO;
    private String userRole;

    public BorrowerPanel(String userRole) {
        this.userRole = userRole;

        borrowerDAO = new BorrowerDAO();
        setLayout(new BorderLayout(15, 15)); // Jarak lebih besar antar komponen utama
        setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding lebih besar
        setBackground(UIManager.getColor("Panel.background")); // Gunakan warna global

        // Panel atas (untuk tombol refresh)
        JPanel topControlPanel = new JPanel(new BorderLayout(10, 0));
        topControlPanel.setOpaque(false);

        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        topButtonPanel.setOpaque(false);

        JButton refreshButton = new JButton("Refresh Data");
        refreshButton.setFont(UIManager.getFont("Button.font"));
        refreshButton.setPreferredSize(new Dimension(120, 38));
        refreshButton.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        refreshButton.addActionListener(e -> loadBorrowers());
        topButtonPanel.add(refreshButton);

        topControlPanel.add(topButtonPanel, BorderLayout.EAST);
        add(topControlPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Nama", "Email", "Telepon"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        borrowerTable = new JTable(tableModel);
        // Properti tabel sudah diatur di Main.java

        JScrollPane scrollPane = new JScrollPane(borrowerTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Table.gridColor"), 1));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setOpaque(false);

        JButton addButton = new JButton("Tambah Peminjam");
        JButton updateButton = new JButton("Perbarui Peminjam");
        JButton deleteButton = new JButton("Hapus Peminjam");

        Dimension buttonSize = new Dimension(160, 45);

        addButton.setFont(UIManager.getFont("Button.font"));
        addButton.setPreferredSize(buttonSize);
        updateButton.setFont(UIManager.getFont("Button.font"));
        updateButton.setPreferredSize(buttonSize);
        deleteButton.setFont(UIManager.getFont("Button.font"));
        deleteButton.setPreferredSize(buttonSize);

        // Warna tombol
        addButton.setBackground(new Color(76, 175, 80)); // Hijau
        addButton.setForeground(Color.WHITE);
        updateButton.setBackground(new Color(33, 150, 243)); // Biru
        updateButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(244, 67, 54)); // Merah
        deleteButton.setForeground(Color.WHITE);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        if (userRole.equals("USER") || userRole.equals("ADMIN")) {
            addButton.setVisible(false);
            updateButton.setVisible(false);
            deleteButton.setVisible(false);
        } else { // SUPER_ADMIN
            addButton.setVisible(true);
            updateButton.setVisible(true);
            deleteButton.setVisible(true);
        }

        loadBorrowers();

        addButton.addActionListener(e -> addBorrower());
        updateButton.addActionListener(e -> updateBorrower());
        deleteButton.addActionListener(e -> deleteBorrower());
    }

    private void loadBorrowers() {
        try {
            List<Borrower> borrowers = borrowerDAO.getAllBorrowers();
            tableModel.setRowCount(0);
            for (Borrower borrower : borrowers) {
                tableModel.addRow(new Object[]{
                        borrower.getId(),
                        borrower.getName(),
                        borrower.getEmail(),
                        borrower.getPhone()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error memuat peminjam: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void addBorrower() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 15, 10)); // Gap lebih besar
        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);

        panel.add(new JLabel("Nama:")); panel.add(nameField);
        panel.add(new JLabel("Email:")); panel.add(emailField);
        panel.add(new JLabel("Telepon:")); panel.add(phoneField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Tambah Peminjam Baru", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            if (nameField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama dan Email tidak boleh kosong.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Borrower borrower = new Borrower();
            borrower.setName(nameField.getText().trim());
            borrower.setEmail(emailField.getText().trim());
            borrower.setPhone(phoneField.getText().trim());
            try {
                borrowerDAO.addBorrower(borrower);
                loadBorrowers();
                JOptionPane.showMessageDialog(this, "Peminjam berhasil ditambahkan.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error menambah peminjam: " + e.getMessage() + "\nPastikan Email unik.", "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void updateBorrower() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 15, 10)); // Gap lebih besar
        int selectedRow = borrowerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih peminjam dari tabel untuk diperbarui.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        JTextField nameField = new JTextField((String) tableModel.getValueAt(selectedRow, 1), 20);
        JTextField emailField = new JTextField((String) tableModel.getValueAt(selectedRow, 2), 20);
        JTextField phoneField = new JTextField((String) tableModel.getValueAt(selectedRow, 3), 20);

        panel.add(new JLabel("Nama:")); panel.add(nameField);
        panel.add(new JLabel("Email:")); panel.add(emailField);
        panel.add(new JLabel("Telepon:")); panel.add(phoneField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Perbarui Peminjam", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            if (nameField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama dan Email tidak boleh kosong.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Borrower borrower = new Borrower();
            borrower.setId(id);
            borrower.setName(nameField.getText().trim());
            borrower.setEmail(emailField.getText().trim());
            borrower.setPhone(phoneField.getText().trim());
            try {
                borrowerDAO.updateBorrower(borrower);
                loadBorrowers();
                JOptionPane.showMessageDialog(this, "Peminjam berhasil diperbarui.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error memperbarui peminjam: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void deleteBorrower() {
        int selectedRow = borrowerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih peminjam dari tabel untuk dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String name = (String) tableModel.getValueAt(selectedRow, 1);

        int option = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus peminjam '" + name + "'?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            try {
                borrowerDAO.deleteBorrower(id);
                loadBorrowers();
                JOptionPane.showMessageDialog(this, "Peminjam berhasil dihapus.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error menghapus peminjam: " + e.getMessage() + "\nPastikan tidak ada transaksi terkait dengan peminjam ini.", "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}