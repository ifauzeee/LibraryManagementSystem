package com.library.view;

import com.library.dao.BorrowerDAO;
import com.library.model.Borrower;

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

    public BorrowerPanel() {
        borrowerDAO = new BorrowerDAO();
        setLayout(new BorderLayout(10, 10)); // Border layout dengan gap
        setBorder(new EmptyBorder(15, 15, 15, 15)); // Padding

        // Bagian atas untuk pencarian atau filter jika ada
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // Anda bisa menambahkan search bar di sini nanti
        add(topPanel, BorderLayout.NORTH);

        // Tabel Peminjam
        String[] columns = {"ID", "Nama", "Email", "Telepon"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        borrowerTable = new JTable(tableModel);
        borrowerTable.setFillsViewportHeight(true);
        borrowerTable.setRowHeight(30);
        borrowerTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        borrowerTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        borrowerTable.getTableHeader().setReorderingAllowed(false);
        borrowerTable.getTableHeader().setBackground(new Color(220, 220, 220));

        borrowerTable.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
        borrowerTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Nama
        borrowerTable.getColumnModel().getColumn(2).setPreferredWidth(250); // Email
        borrowerTable.getColumnModel().getColumn(3).setPreferredWidth(120); // Telepon

        JScrollPane scrollPane = new JScrollPane(borrowerTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        add(scrollPane, BorderLayout.CENTER);

        // Panel Tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton addButton = new JButton("Tambah Peminjam");
        JButton updateButton = new JButton("Perbarui Peminjam");
        JButton deleteButton = new JButton("Hapus Peminjam");

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 13);
        int buttonHeight = 40;
        int buttonWidth = 160;
        Dimension buttonSize = new Dimension(buttonWidth, buttonHeight);

        addButton.setFont(buttonFont);
        addButton.setPreferredSize(buttonSize);
        updateButton.setFont(buttonFont);
        updateButton.setPreferredSize(buttonSize);
        deleteButton.setFont(buttonFont);
        deleteButton.setPreferredSize(buttonSize);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Muat data
        loadBorrowers();

        // Aksi tombol
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
                        borrower.getId(), borrower.getName(), borrower.getEmail(), borrower.getPhone()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error memuat peminjam: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void addBorrower() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);

        panel.add(new JLabel("Nama:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Telepon:"));
        panel.add(phoneField);

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
        int selectedRow = borrowerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih peminjam dari tabel untuk diperbarui.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField nameField = new JTextField((String) tableModel.getValueAt(selectedRow, 1), 20);
        JTextField emailField = new JTextField((String) tableModel.getValueAt(selectedRow, 2), 20);
        JTextField phoneField = new JTextField((String) tableModel.getValueAt(selectedRow, 3), 20);

        panel.add(new JLabel("Nama:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Telepon:"));
        panel.add(phoneField);

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