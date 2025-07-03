package com.library.view;

import com.library.dao.TransactionDAO;
import com.library.dao.BookDAO; // Diperlukan untuk mengecek status buku
import com.library.model.Transaction;
import com.library.model.Book; // Diperlukan untuk mengecek status buku

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat; // Untuk format tanggal
import java.util.List;

public class TransactionPanel extends JPanel {
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private TransactionDAO transactionDAO;
    private BookDAO bookDAO; // Tambahkan BookDAO untuk interaksi dengan tabel buku

    // Format tanggal untuk tampilan
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public TransactionPanel() {
        transactionDAO = new TransactionDAO();
        bookDAO = new BookDAO(); // Inisialisasi BookDAO
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(topPanel, BorderLayout.NORTH);

        // Tabel Transaksi
        // Ubah kolom "Buku" dan "Peminjam" agar menampilkan nama/judul daripada ID
        String[] columns = {"ID Transaksi", "ID Buku", "Judul Buku", "ID Peminjam", "Nama Peminjam", "Tanggal Pinjam", "Tanggal Kembali"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        transactionTable = new JTable(tableModel);
        transactionTable.setFillsViewportHeight(true);
        transactionTable.setRowHeight(30);
        transactionTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        transactionTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        transactionTable.getTableHeader().setReorderingAllowed(false);
        transactionTable.getTableHeader().setBackground(new Color(220, 220, 220));

        // Mengatur lebar kolom (opsional)
        transactionTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // ID Transaksi
        transactionTable.getColumnModel().getColumn(1).setPreferredWidth(60);  // ID Buku
        transactionTable.getColumnModel().getColumn(2).setPreferredWidth(180); // Judul Buku
        transactionTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // ID Peminjam
        transactionTable.getColumnModel().getColumn(4).setPreferredWidth(180); // Nama Peminjam
        transactionTable.getColumnModel().getColumn(5).setPreferredWidth(120); // Tanggal Pinjam
        transactionTable.getColumnModel().getColumn(6).setPreferredWidth(120); // Tanggal Kembali

        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        add(scrollPane, BorderLayout.CENTER);

        // Panel Tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton addButton = new JButton("Pinjam Buku");
        JButton returnButton = new JButton("Kembalikan Buku");
        JButton deleteButton = new JButton("Hapus Transaksi");

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 13);
        int buttonHeight = 40;
        int buttonWidth = 160;
        Dimension buttonSize = new Dimension(buttonWidth, buttonHeight);

        addButton.setFont(buttonFont);
        addButton.setPreferredSize(buttonSize);
        returnButton.setFont(buttonFont);
        returnButton.setPreferredSize(buttonSize);
        deleteButton.setFont(buttonFont);
        deleteButton.setPreferredSize(buttonSize);

        buttonPanel.add(addButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Muat data
        loadTransactions();

        // Aksi tombol
        addButton.addActionListener(e -> addTransaction());
        returnButton.addActionListener(e -> returnBook());
        deleteButton.addActionListener(e -> deleteTransaction());
    }

    private void loadTransactions() {
        try {
            List<Transaction> transactions = transactionDAO.getAllTransactions();
            tableModel.setRowCount(0);
            for (Transaction transaction : transactions) {
                // Fetch book and borrower details for display
                Book book = null;
                try {
                    // Ini adalah asumsi bahwa BookDAO memiliki metode getBookById
                    // Jika tidak, Anda perlu menambahkannya atau mengadaptasi kueri di TransactionDAO
                    List<Book> allBooks = bookDAO.getAllBooks(); // Cara sederhana, tidak efisien untuk data besar
                    for (Book b : allBooks) {
                        if (b.getId() == transaction.getBookId()) {
                            book = b;
                            break;
                        }
                    }
                } catch (SQLException e) {
                    System.err.println("Error fetching book details for transaction: " + e.getMessage());
                }

                String bookTitle = (book != null) ? book.getTitle() : "N/A";
                // Lakukan hal serupa untuk peminjam jika ingin menampilkan nama peminjam

                tableModel.addRow(new Object[]{
                        transaction.getId(),
                        transaction.getBookId(),
                        bookTitle, // Menampilkan judul buku
                        transaction.getBorrowerId(),
                        "Nama Peminjam (TODO)", // Anda perlu mengambil nama peminjam dari BorrowerDAO
                        dateFormat.format(transaction.getBorrowDate()),
                        transaction.getReturnDate() == null ? "Belum Kembali" : dateFormat.format(transaction.getReturnDate())
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error memuat transaksi: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void addTransaction() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField bookIdField = new JTextField(20);
        JTextField borrowerIdField = new JTextField(20);

        panel.add(new JLabel("ID Buku:"));
        panel.add(bookIdField);
        panel.add(new JLabel("ID Peminjam:"));
        panel.add(borrowerIdField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Tambah Transaksi Peminjaman", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int bookId = Integer.parseInt(bookIdField.getText().trim());
                int borrowerId = Integer.parseInt(borrowerIdField.getText().trim());

                // Validasi: Pastikan buku tersedia
                Book bookToBorrow = null;
                try {
                    List<Book> allBooks = bookDAO.getAllBooks();
                    for(Book b : allBooks) { // Ini tidak efisien, seharusnya ada getBookById
                        if (b.getId() == bookId) {
                            bookToBorrow = b;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Gagal memeriksa ketersediaan buku: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (bookToBorrow == null) {
                    JOptionPane.showMessageDialog(this, "Buku dengan ID " + bookId + " tidak ditemukan.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!"Available".equals(bookToBorrow.getStatus())) {
                    JOptionPane.showMessageDialog(this, "Buku '" + bookToBorrow.getTitle() + "' tidak tersedia (Status: " + bookToBorrow.getStatus() + ").", "Validasi Input", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Transaction transaction = new Transaction();
                transaction.setBookId(bookId);
                transaction.setBorrowerId(borrowerId);
                transaction.setBorrowDate(new Date());

                transactionDAO.addTransaction(transaction);

                // Update status buku menjadi 'Borrowed'
                bookToBorrow.setStatus("Borrowed");
                bookDAO.updateBook(bookToBorrow); // Panggil updateBook untuk mengubah status

                loadTransactions();
                JOptionPane.showMessageDialog(this, "Transaksi berhasil ditambahkan. Status buku diperbarui.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID Buku dan ID Peminjam harus berupa angka.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error menambah transaksi: " + e.getMessage() + "\nPastikan ID Buku dan ID Peminjam valid.", "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void returnBook() {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih transaksi dari tabel untuk dikembalikan.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int transactionId = (int) tableModel.getValueAt(selectedRow, 0);
        String currentReturnDate = (String) tableModel.getValueAt(selectedRow, 6);

        if (!"Belum Kembali".equals(currentReturnDate)) {
            JOptionPane.showMessageDialog(this, "Buku ini sudah dikembalikan.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(this, "Apakah Anda ingin menandai buku ini sebagai dikembalikan?", "Konfirmasi Pengembalian", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            try {
                Transaction transaction = new Transaction();
                transaction.setId(transactionId);
                transaction.setReturnDate(new Date());

                transactionDAO.updateTransaction(transaction);

                // Update status buku menjadi 'Available'
                int bookId = (int) tableModel.getValueAt(selectedRow, 1);
                Book bookReturned = null;
                try {
                    List<Book> allBooks = bookDAO.getAllBooks(); // Ulangi cara sederhana
                    for(Book b : allBooks) {
                        if (b.getId() == bookId) {
                            bookReturned = b;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.err.println("Error fetching book for return status update: " + ex.getMessage());
                }

                if (bookReturned != null) {
                    bookReturned.setStatus("Available");
                    bookDAO.updateBook(bookReturned);
                }

                loadTransactions();
                JOptionPane.showMessageDialog(this, "Buku berhasil dikembalikan. Status buku diperbarui.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error mengembalikan buku: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
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
        int option = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus transaksi ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            try {
                transactionDAO.deleteTransaction(transactionId);
                loadTransactions();
                JOptionPane.showMessageDialog(this, "Transaksi berhasil dihapus.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error menghapus transaksi: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}