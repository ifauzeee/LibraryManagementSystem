package com.library.view;

import com.formdev.flatlaf.FlatClientProperties; // BARU
import com.library.dao.TransactionDAO;
import com.library.dao.BookDAO;
import com.library.dao.BorrowerDAO;
import com.library.model.Transaction;
import com.library.model.Book;
import com.library.model.Borrower;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class TransactionPanel extends JPanel {
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private TransactionDAO transactionDAO;
    private BookDAO bookDAO;
    private BorrowerDAO borrowerDAO;
    private String userRole;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public TransactionPanel(String userRole) {
        this.userRole = userRole;

        transactionDAO = new TransactionDAO();
        bookDAO = new BookDAO();
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
        refreshButton.addActionListener(e -> loadTransactions()); // Aksi refresh
        topButtonPanel.add(refreshButton);

        topControlPanel.add(topButtonPanel, BorderLayout.EAST);
        add(topControlPanel, BorderLayout.NORTH);

        String[] columns = {"ID Transaksi", "ID Buku", "Judul Buku", "Jumlah", "ID Peminjam", "Nama Peminjam", "Tanggal Pinjam", "Tanggal Kembali", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        transactionTable = new JTable(tableModel);
        // Properti tabel sudah diatur di Main.java

        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Table.gridColor"), 1));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setOpaque(false);

        JButton addButton = new JButton("Catat Peminjaman");
        JButton deleteButton = new JButton("Hapus Transaksi");
        JButton approveButton = new JButton("Setujui Peminjaman");
        JButton rejectButton = new JButton("Tolak Peminjaman");

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 13);
        Dimension buttonSize = new Dimension(160, 45);
        Dimension approvalButtonSize = new Dimension(180, 45); // Lebih besar untuk approval

        addButton.setFont(buttonFont); addButton.setPreferredSize(buttonSize);
        deleteButton.setFont(buttonFont); deleteButton.setPreferredSize(buttonSize);
        approveButton.setFont(buttonFont); approveButton.setPreferredSize(approvalButtonSize);
        rejectButton.setFont(buttonFont); rejectButton.setPreferredSize(approvalButtonSize);

        // Warna tombol
        addButton.setBackground(new Color(0, 188, 212)); // Cyan (untuk catat peminjaman)
        addButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(244, 67, 54)); // Merah
        deleteButton.setForeground(Color.WHITE);
        approveButton.setBackground(new Color(76, 175, 80)); // Hijau
        approveButton.setForeground(Color.WHITE);
        rejectButton.setBackground(new Color(255, 87, 34)); // Orange gelap
        rejectButton.setForeground(Color.WHITE);


        if (userRole.equals("USER")) { // USER tidak melihat tombol ini sama sekali
            addButton.setVisible(false);
            deleteButton.setVisible(false);
            approveButton.setVisible(false);
            rejectButton.setVisible(false);
        } else if (userRole.equals("ADMIN") || userRole.equals("SUPER_ADMIN")) {
            buttonPanel.add(addButton);
            buttonPanel.add(deleteButton);
            buttonPanel.add(approveButton);
            buttonPanel.add(rejectButton);
        }
        add(buttonPanel, BorderLayout.SOUTH);

        loadTransactions();

        addButton.addActionListener(e -> addTransaction());
        deleteButton.addActionListener(e -> deleteTransaction());
        approveButton.addActionListener(e -> handleApproval("Approved"));
        rejectButton.addActionListener(e -> handleApproval("Rejected"));
    }

    private void loadTransactions() {
        try {
            List<Transaction> transactions = transactionDAO.getAllTransactions();
            tableModel.setRowCount(0);
            for (Transaction transaction : transactions) {
                String bookTitle = "N/A";
                String borrowerName = "N/A";
                try {
                    Book book = bookDAO.getBookById(transaction.getBookId());
                    if (book != null) {
                        bookTitle = book.getTitle();
                    }
                    Borrower borrower = borrowerDAO.getBorrowerById(transaction.getBorrowerId());
                    if (borrower != null) {
                        borrowerName = borrower.getName();
                    }
                } catch (SQLException e) {
                    System.err.println("Error fetching associated details for transaction: " + e.getMessage());
                }

                tableModel.addRow(new Object[]{
                        transaction.getId(),
                        transaction.getBookId(),
                        bookTitle,
                        transaction.getQuantity(),
                        transaction.getBorrowerId(),
                        borrowerName,
                        dateFormat.format(transaction.getBorrowDate()),
                        transaction.getReturnDate() == null ? "Belum Kembali" : dateFormat.format(transaction.getReturnDate()),
                        transaction.getStatus()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error memuat transaksi: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void addTransaction() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 15, 10)); // Gap lebih besar
        JTextField bookIdField = new JTextField(20);
        JTextField borrowerIdField = new JTextField(20);
        JTextField quantityField = new JTextField("1");

        panel.add(new JLabel("ID Buku:")); panel.add(bookIdField);
        panel.add(new JLabel("ID Peminjam:")); panel.add(borrowerIdField);
        panel.add(new JLabel("Jumlah Salinan:")); panel.add(quantityField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Catat Peminjaman Buku Oleh Admin", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int bookId = Integer.parseInt(bookIdField.getText().trim());
                int borrowerId = Integer.parseInt(borrowerIdField.getText().trim());
                int quantity = Integer.parseInt(quantityField.getText().trim());

                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Jumlah salinan harus lebih dari 0.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Book bookToBorrow = bookDAO.getBookById(bookId);
                if (bookToBorrow == null) {
                    JOptionPane.showMessageDialog(this, "Buku dengan ID " + bookId + " tidak ditemukan.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (bookToBorrow.getAvailableCopies() < quantity) {
                    JOptionPane.showMessageDialog(this, "Buku '" + bookToBorrow.getTitle() + "' tidak tersedia dalam jumlah yang diminta (" + quantity + "). Tersedia: " + bookToBorrow.getAvailableCopies(), "Validasi Input", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Borrower borrowerExists = borrowerDAO.getBorrowerById(borrowerId);
                if (borrowerExists == null) {
                    JOptionPane.showMessageDialog(this, "Peminjam dengan ID " + borrowerId + " tidak ditemukan.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Transaction transaction = new Transaction();
                transaction.setBookId(bookId);
                transaction.setBorrowerId(borrowerId);
                transaction.setBorrowDate(new Date());
                transaction.setStatus("Approved");
                transaction.setQuantity(quantity);

                transactionDAO.addTransaction(transaction);
                bookDAO.decreaseAvailableCopies(bookId, quantity);

                loadTransactions();
                JOptionPane.showMessageDialog(this, "Peminjaman " + quantity + " salinan buku berhasil dicatat dan disetujui oleh Admin.", "Peminjaman Sukses", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID Buku, ID Peminjam, dan Jumlah Salinan harus berupa angka.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error mencatat peminjaman: " + e.getMessage() + "\nPastikan ID Buku dan ID Peminjam valid.", "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void deleteTransaction() {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih transaksi dari tabel untuk dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int transactionId = (int) tableModel.getValueAt(selectedRow, 0);
        String transactionStatus = (String) tableModel.getValueAt(selectedRow, 8);
        int bookId = (int) tableModel.getValueAt(selectedRow, 1);
        int quantity = (int) tableModel.getValueAt(selectedRow, 3);

        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus transaksi ini? Ini mungkin mempengaruhi stok buku.", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            if ("Approved".equals(transactionStatus) || "Returned".equals(transactionStatus)) {
                Book book = bookDAO.getBookById(bookId);
                if (book != null) {
                    if (book.getAvailableCopies() + quantity <= book.getTotalCopies()) {
                        bookDAO.increaseAvailableCopies(bookId, quantity);
                    } else {
                        book.setAvailableCopies(book.getTotalCopies());
                        bookDAO.updateBook(book);
                    }
                }
            }

            transactionDAO.deleteTransaction(transactionId);
            loadTransactions();
            JOptionPane.showMessageDialog(this, "Transaksi berhasil dihapus.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error menghapus transaksi: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void handleApproval(String decision) {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih transaksi peminjaman yang ingin Anda " + decision.toLowerCase() + ".", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int transactionId = (int) tableModel.getValueAt(selectedRow, 0);
        String currentStatus = (String) tableModel.getValueAt(selectedRow, 8);

        if (!"Pending".equals(currentStatus)) {
            JOptionPane.showMessageDialog(this, "Transaksi ini sudah " + currentStatus.toLowerCase() + ". Tidak bisa diubah lagi.", "Status Tidak Valid", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int bookId = (int) tableModel.getValueAt(selectedRow, 1);
            int quantity = (int) tableModel.getValueAt(selectedRow, 3);
            Book book = bookDAO.getBookById(bookId);

            if (book == null) {
                JOptionPane.showMessageDialog(this, "Buku tidak ditemukan untuk transaksi ini.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if ("Approved".equals(decision)) {
                if (book.getAvailableCopies() < quantity) {
                    JOptionPane.showMessageDialog(this, "Tidak ada cukup salinan buku '" + book.getTitle() + "' yang tersedia (" + book.getAvailableCopies() + "). Gagal menyetujui " + quantity + " salinan.", "Stok Habis", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                transactionDAO.updateTransactionStatus(transactionId, "Approved");
                bookDAO.decreaseAvailableCopies(bookId, quantity);
                JOptionPane.showMessageDialog(this, "Peminjaman " + quantity + " salinan berhasil disetujui. Stok buku dikurangi.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } else if ("Rejected".equals(decision)) {
                transactionDAO.updateTransactionStatus(transactionId, "Rejected");
                JOptionPane.showMessageDialog(this, "Peminjaman berhasil ditolak.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
            loadTransactions();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saat " + decision.toLowerCase() + " peminjaman: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}