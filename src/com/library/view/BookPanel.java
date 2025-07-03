package com.library.view;

import com.formdev.flatlaf.FlatClientProperties; // BARU
import com.library.dao.BookDAO;
import com.library.dao.TransactionDAO;
import com.library.dao.BorrowerDAO;
import com.library.model.Book;
import com.library.model.Transaction;
import com.library.model.Borrower;
import com.library.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class BookPanel extends JPanel {
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private BookDAO bookDAO;
    private TransactionDAO transactionDAO;
    private BorrowerDAO borrowerDAO;
    private User currentUser;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public BookPanel(User currentUser) {
        this.currentUser = currentUser;

        bookDAO = new BookDAO();
        transactionDAO = new TransactionDAO();
        borrowerDAO = new BorrowerDAO();

        setLayout(new BorderLayout(15, 15)); // Jarak lebih besar antar komponen utama
        setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding lebih besar di sekitar panel
        setBackground(UIManager.getColor("Panel.background")); // Gunakan warna global

        // Panel Kontrol Atas (Refresh, Search, dll.)
        JPanel topControlPanel = new JPanel(new BorderLayout(10, 0)); // Horizontal gap
        topControlPanel.setOpaque(false); // Transparan agar background panel utama terlihat

        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0)); // Tombol di kanan atas
        topButtonPanel.setOpaque(false);

        JButton refreshButton = new JButton("Refresh Data");
        refreshButton.setFont(UIManager.getFont("Button.font")); // Gunakan font global
        refreshButton.setPreferredSize(new Dimension(120, 38)); // Ukuran tombol
        refreshButton.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT); // Membulat
        refreshButton.addActionListener(e -> loadBooks());
        topButtonPanel.add(refreshButton);

        topControlPanel.add(topButtonPanel, BorderLayout.EAST);
        add(topControlPanel, BorderLayout.NORTH);

        // Tabel Buku
        String[] columns = {"ID", "Judul", "Penulis", "ISBN", "Total", "Tersedia", "Status Ketersediaan"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        bookTable = new JTable(tableModel);
        // Properti tabel sudah diatur di Main.java (font, row height, dll.)

        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Table.gridColor"), 1)); // Border dari warna grid
        add(scrollPane, BorderLayout.CENTER);

        // Panel Tombol Aksi (Add, Update, Delete, Borrow, Return)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15)); // Jarak antar tombol lebih besar
        buttonPanel.setOpaque(false); // Transparan

        JButton addButton = new JButton("Tambah Buku");
        JButton updateButton = new JButton("Perbarui Buku");
        JButton deleteButton = new JButton("Hapus Buku");
        JButton borrowButton = new JButton("Pinjam Buku");
        JButton returnBookUserButton = new JButton("Kembalikan Buku Saya");

        // Styling tombol umum
        Dimension buttonSize = new Dimension(150, 45);
        Dimension userActionBtnSize = new Dimension(180, 45); // Lebih besar untuk aksi user

        addButton.setPreferredSize(buttonSize);
        updateButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);
        borrowButton.setPreferredSize(buttonSize);
        returnBookUserButton.setPreferredSize(userActionBtnSize);

        // Warna tombol
        addButton.setBackground(new Color(76, 175, 80)); // Hijau
        addButton.setForeground(Color.WHITE);
        updateButton.setBackground(new Color(33, 150, 243)); // Biru
        updateButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(244, 67, 54)); // Merah
        deleteButton.setForeground(Color.WHITE);
        borrowButton.setBackground(new Color(0, 188, 212)); // Cyan
        borrowButton.setForeground(Color.WHITE);
        returnBookUserButton.setBackground(new Color(255, 193, 7)); // Amber
        returnBookUserButton.setForeground(Color.BLACK);


        // Logika tampilan tombol berdasarkan role
        if (currentUser.getRole().equals("USER")) {
            addButton.setVisible(false);
            updateButton.setVisible(false);
            deleteButton.setVisible(false);
            buttonPanel.add(borrowButton);
            buttonPanel.add(returnBookUserButton);
        } else if (currentUser.getRole().equals("ADMIN") || currentUser.getRole().equals("SUPER_ADMIN")) {
            buttonPanel.add(addButton);
            buttonPanel.add(updateButton);
            buttonPanel.add(deleteButton);
            borrowButton.setVisible(false);
            returnBookUserButton.setVisible(false);
        }
        add(buttonPanel, BorderLayout.SOUTH);

        loadBooks();

        addButton.addActionListener(e -> addBook());
        updateButton.addActionListener(e -> updateBook());
        deleteButton.addActionListener(e -> deleteBook());
        borrowButton.addActionListener(e -> borrowBook());
        returnBookUserButton.addActionListener(e -> returnBookByUser());
    }

    private void loadBooks() {
        try {
            List<Book> books = bookDAO.getAllBooks();
            tableModel.setRowCount(0);
            for (Book book : books) {
                String statusKetersediaan;
                if (book.getAvailableCopies() == 0) {
                    statusKetersediaan = "Habis"; // Merah
                } else if (book.getAvailableCopies() == book.getTotalCopies()) {
                    statusKetersediaan = "Full (Ready)"; // Hijau
                } else {
                    statusKetersediaan = "Tersedia (" + book.getAvailableCopies() + "/" + book.getTotalCopies() + ")"; // Orange
                }
                tableModel.addRow(new Object[]{
                        book.getId(), book.getTitle(), book.getAuthor(),
                        book.getIsbn(), book.getTotalCopies(), book.getAvailableCopies(),
                        statusKetersediaan
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error memuat buku: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void addBook() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 15, 10)); // Gap lebih besar
        JTextField titleField = new JTextField(20);
        JTextField authorField = new JTextField(20);
        JTextField isbnField = new JTextField(20);
        JTextField totalCopiesField = new JTextField("1");

        panel.add(new JLabel("Judul:")); panel.add(titleField);
        panel.add(new JLabel("Penulis:")); panel.add(authorField);
        panel.add(new JLabel("ISBN:")); panel.add(isbnField);
        panel.add(new JLabel("Total Salinan:")); panel.add(totalCopiesField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Tambah Buku Baru", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            if (titleField.getText().trim().isEmpty() || authorField.getText().trim().isEmpty() || isbnField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Judul, Penulis, dan ISBN tidak boleh kosong.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int totalCopies = Integer.parseInt(totalCopiesField.getText().trim());
                int availableCopies = totalCopies;

                if (totalCopies <= 0) {
                    JOptionPane.showMessageDialog(this, "Total Salinan harus lebih dari 0.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Book book = new Book();
                book.setTitle(titleField.getText().trim());
                book.setAuthor(authorField.getText().trim());
                book.setIsbn(isbnField.getText().trim());
                book.setTotalCopies(totalCopies);
                book.setAvailableCopies(availableCopies);

                bookDAO.addBook(book);
                loadBooks();
                JOptionPane.showMessageDialog(this, "Buku berhasil ditambahkan.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Total Salinan harus angka valid.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error menambah buku: " + e.getMessage() + "\nPastikan ISBN unik.", "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void updateBook() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 15, 10)); // Gap lebih besar
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih buku dari tabel untuk diperbarui.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        JTextField titleField = new JTextField((String) tableModel.getValueAt(selectedRow, 1), 20);
        JTextField authorField = new JTextField((String) tableModel.getValueAt(selectedRow, 2), 20);
        JTextField isbnField = new JTextField((String) tableModel.getValueAt(selectedRow, 3), 20);
        JTextField totalCopiesField = new JTextField(tableModel.getValueAt(selectedRow, 4).toString());
        JTextField availableCopiesField = new JTextField(tableModel.getValueAt(selectedRow, 5).toString());

        panel.add(new JLabel("Judul:")); panel.add(titleField);
        panel.add(new JLabel("Penulis:")); panel.add(authorField);
        panel.add(new JLabel("ISBN:")); panel.add(isbnField);
        panel.add(new JLabel("Total Salinan:")); panel.add(totalCopiesField);
        panel.add(new JLabel("Salinan Tersedia:")); panel.add(availableCopiesField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Perbarui Buku", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            if (titleField.getText().trim().isEmpty() || authorField.getText().trim().isEmpty() || isbnField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Judul, Penulis, dan ISBN tidak boleh kosong.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int totalCopies = Integer.parseInt(totalCopiesField.getText().trim());
                int availableCopies = Integer.parseInt(availableCopiesField.getText().trim());

                if (totalCopies <= 0 || availableCopies < 0 || availableCopies > totalCopies) {
                    JOptionPane.showMessageDialog(this, "Jumlah salinan tidak valid. Pastikan Total > 0 dan Tersedia <= Total.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Book book = new Book();
                book.setId(id);
                book.setTitle(titleField.getText().trim());
                book.setAuthor(authorField.getText().trim());
                book.setIsbn(isbnField.getText().trim());
                book.setTotalCopies(totalCopies);
                book.setAvailableCopies(availableCopies);

                bookDAO.updateBook(book);
                loadBooks();
                JOptionPane.showMessageDialog(this, "Buku berhasil diperbarui.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Total Salinan dan Salinan Tersedia harus angka valid.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error memperbarui buku: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void deleteBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih buku dari tabel untuk dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String title = (String) tableModel.getValueAt(selectedRow, 1);

        int option = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus buku '" + title + "'?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            try {
                bookDAO.deleteBook(id);
                loadBooks();
                JOptionPane.showMessageDialog(this, "Buku berhasil dihapus.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error menghapus buku: " + e.getMessage() + "\nPastikan tidak ada transaksi terkait dengan buku ini.", "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void borrowBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih buku yang ingin Anda pinjam.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int bookId = (int) tableModel.getValueAt(selectedRow, 0);
        String bookTitle = (String) tableModel.getValueAt(selectedRow, 1);
        int availableCopies = (int) tableModel.getValueAt(selectedRow, 5);

        if (availableCopies <= 0) {
            JOptionPane.showMessageDialog(this, "Buku '" + bookTitle + "' tidak tersedia untuk dipinjam (Stok habis).", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String quantityStr = JOptionPane.showInputDialog(this,
                "Berapa salinan buku '" + bookTitle + "' yang ingin Anda pinjam?\n" +
                        "(Tersedia: " + availableCopies + ")",
                "Jumlah Peminjaman", JOptionPane.QUESTION_MESSAGE);

        if (quantityStr == null || quantityStr.trim().isEmpty()) {
            return;
        }

        int quantityToBorrow;
        try {
            quantityToBorrow = Integer.parseInt(quantityStr.trim());
            if (quantityToBorrow <= 0) {
                JOptionPane.showMessageDialog(this, "Jumlah salinan harus lebih dari 0.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (quantityToBorrow > availableCopies) {
                JOptionPane.showMessageDialog(this, "Jumlah salinan yang diminta melebihi yang tersedia (" + availableCopies + ").", "Validasi Input", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah salinan harus berupa angka.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int borrowerId = currentUser.getId();
        String borrowerName = currentUser.getFullName();

        int confirmOption = JOptionPane.showConfirmDialog(this,
                "Anda akan meminjam " + quantityToBorrow + " salinan buku '" + bookTitle + "' sebagai:\n" +
                        "ID: " + borrowerId + "\n" +
                        "Nama: " + borrowerName + "\n\n" +
                        "Lanjutkan?",
                "Konfirmasi Peminjaman Buku", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirmOption == JOptionPane.OK_OPTION) {
            try {
                Borrower borrower = borrowerDAO.getBorrowerById(borrowerId);
                if (borrower == null) {
                    JOptionPane.showMessageDialog(this, "Terjadi kesalahan: ID peminjam Anda tidak ditemukan di database. Pastikan Anda terdaftar sebagai peminjam.", "Validasi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Transaction transaction = new Transaction();
                transaction.setBookId(bookId);
                transaction.setBorrowerId(borrowerId);
                transaction.setBorrowDate(new Date());
                transaction.setStatus("Pending");
                transaction.setQuantity(quantityToBorrow);

                transactionDAO.addTransaction(transaction);
                JOptionPane.showMessageDialog(this, "Permintaan peminjaman " + quantityToBorrow + " salinan buku '" + bookTitle + "' berhasil diajukan.\nMenunggu persetujuan admin.", "Peminjaman Diajukan", JOptionPane.INFORMATION_MESSAGE);
                loadBooks();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error saat mengajukan peminjaman: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void returnBookByUser() {
        try {
            List<Transaction> userTransactions = transactionDAO.getAllTransactions(); // Ambil semua transaksi
            List<String> approvedBorrowedTransactions = new java.util.ArrayList<>();
            List<Transaction> filteredTransactions = new java.util.ArrayList<>(); // Untuk menyimpan objek transaksi yang difilter

            for (Transaction t : userTransactions) {
                // Filter transaksi yang Approved (sudah dipinjam) dan belum dikembalikan oleh user ini
                if (t.getBorrowerId() == currentUser.getId() && t.getStatus().equals("Approved") && t.getReturnDate() == null) {
                    Book book = bookDAO.getBookById(t.getBookId());
                    String bookTitle = (book != null) ? book.getTitle() : "Buku Tidak Dikenal";
                    approvedBorrowedTransactions.add("ID Transaksi: " + t.getId() + " | Buku: " + bookTitle + " (" + t.getQuantity() + " salinan) | Dipinjam: " + dateFormat.format(t.getBorrowDate()));
                    filteredTransactions.add(t); // Tambahkan objek transaksi ke daftar yang difilter
                }
            }

            if (approvedBorrowedTransactions.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Anda tidak memiliki buku yang sedang dipinjam atau perlu dikembalikan.", "Info Pengembalian", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Tampilkan pilihan transaksi yang bisa dikembalikan
            String selectedTransactionString = (String) JOptionPane.showInputDialog(
                    this,
                    "Pilih transaksi peminjaman yang ingin Anda kembalikan:",
                    "Kembalikan Buku",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    approvedBorrowedTransactions.toArray(),
                    approvedBorrowedTransactions.get(0)
            );

            if (selectedTransactionString == null || selectedTransactionString.isEmpty()) {
                return; // User membatalkan
            }

            // Ekstrak ID Transaksi dari string yang dipilih
            int transactionId = Integer.parseInt(selectedTransactionString.split(" ")[2]);

            // Konfirmasi pengembalian
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin mengembalikan buku dari transaksi ini?", "Konfirmasi Pengembalian", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                Transaction transactionToReturn = transactionDAO.getTransactionById(transactionId);
                if (transactionToReturn != null) {
                    // Update tanggal kembali di transaksi
                    transactionToReturn.setReturnDate(new Date());
                    transactionDAO.updateTransactionReturnDate(transactionToReturn);

                    // Tingkatkan jumlah tersedia buku
                    Book book = bookDAO.getBookById(transactionToReturn.getBookId());
                    if (book != null) {
                        bookDAO.increaseAvailableCopies(book.getId(), transactionToReturn.getQuantity());
                    }

                    // Ubah status transaksi menjadi 'Returned'
                    transactionDAO.updateTransactionStatus(transactionId, "Returned");

                    JOptionPane.showMessageDialog(this, "Buku berhasil dikembalikan. Stok buku diperbarui.", "Pengembalian Sukses", JOptionPane.INFORMATION_MESSAGE);
                    loadBooks(); // Refresh tampilan buku
                } else {
                    JOptionPane.showMessageDialog(this, "Transaksi tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saat mengembalikan buku: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error memproses pilihan transaksi. Format tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}