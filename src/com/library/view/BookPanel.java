package com.library.view;

import com.library.dao.BookDAO;
import com.library.model.Book;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class BookPanel extends JPanel {
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private BookDAO bookDAO;

    public BookPanel() {
        bookDAO = new BookDAO();
        setLayout(new BorderLayout(10, 10)); // Border layout dengan gap
        setBorder(new EmptyBorder(15, 15, 15, 15)); // Padding

        // Bagian atas untuk pencarian atau filter jika ada
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // Anda bisa menambahkan search bar di sini nanti
        // JTextField searchField = new JTextField(20);
        // topPanel.add(new JLabel("Cari Buku:"));
        // topPanel.add(searchField);
        add(topPanel, BorderLayout.NORTH);

        // Tabel Buku
        String[] columns = {"ID", "Judul", "Penulis", "ISBN", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Membuat semua sel tidak bisa diedit langsung di tabel
            }
        };
        bookTable = new JTable(tableModel);
        bookTable.setFillsViewportHeight(true);
        bookTable.setRowHeight(30); // Tinggi baris lebih nyaman
        bookTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bookTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        bookTable.getTableHeader().setReorderingAllowed(false); // Kolom tidak bisa diubah posisi
        bookTable.getTableHeader().setBackground(new Color(220, 220, 220)); // Warna header tabel

        // Mengatur lebar kolom (opsional)
        bookTable.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
        bookTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Judul
        bookTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Penulis
        bookTable.getColumnModel().getColumn(3).setPreferredWidth(120); // ISBN
        bookTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Status

        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); // Border tipis untuk scroll pane
        add(scrollPane, BorderLayout.CENTER);

        // Panel Tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10)); // Layout flow dengan gap
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Padding atas

        JButton addButton = new JButton("Tambah Buku");
        JButton updateButton = new JButton("Perbarui Buku");
        JButton deleteButton = new JButton("Hapus Buku");

        // Styling tombol
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 13);
        int buttonHeight = 40; // Tinggi tombol yang seragam
        int buttonWidth = 150; // Lebar tombol yang seragam
        Dimension buttonSize = new Dimension(buttonWidth, buttonHeight);

        addButton.setFont(buttonFont);
        addButton.setPreferredSize(buttonSize);
        // FlatLaf akan mengatur warna tombol secara default, tapi bisa di-override jika perlu
        // addButton.setBackground(new Color(76, 175, 80)); // Contoh jika ingin override warna hijau

        updateButton.setFont(buttonFont);
        updateButton.setPreferredSize(buttonSize);

        deleteButton.setFont(buttonFont);
        deleteButton.setPreferredSize(buttonSize);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Muat data
        loadBooks();

        // Aksi tombol
        addButton.addActionListener(e -> addBook());
        updateButton.addActionListener(e -> updateBook());
        deleteButton.addActionListener(e -> deleteBook());
    }

    private void loadBooks() {
        try {
            List<Book> books = bookDAO.getAllBooks();
            tableModel.setRowCount(0);
            for (Book book : books) {
                tableModel.addRow(new Object[]{
                        book.getId(), book.getTitle(), book.getAuthor(),
                        book.getIsbn(), book.getStatus()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error memuat buku: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void addBook() {
        // Menggunakan JPanel untuk layout dialog yang lebih baik
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10)); // GridLayout dengan gap
        JTextField titleField = new JTextField(20);
        JTextField authorField = new JTextField(20);
        JTextField isbnField = new JTextField(20);
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Available", "Borrowed"});
        statusComboBox.setSelectedItem("Available"); // Default status

        panel.add(new JLabel("Judul:"));
        panel.add(titleField);
        panel.add(new JLabel("Penulis:"));
        panel.add(authorField);
        panel.add(new JLabel("ISBN:"));
        panel.add(isbnField);
        panel.add(new JLabel("Status:"));
        panel.add(statusComboBox);

        int option = JOptionPane.showConfirmDialog(this, panel, "Tambah Buku Baru", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            if (titleField.getText().trim().isEmpty() || authorField.getText().trim().isEmpty() || isbnField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Judul, Penulis, dan ISBN tidak boleh kosong.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Book book = new Book();
            book.setTitle(titleField.getText().trim());
            book.setAuthor(authorField.getText().trim());
            book.setIsbn(isbnField.getText().trim());
            book.setStatus((String) statusComboBox.getSelectedItem());
            try {
                bookDAO.addBook(book);
                loadBooks();
                JOptionPane.showMessageDialog(this, "Buku berhasil ditambahkan.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error menambah buku: " + e.getMessage() + "\nPastikan ISBN unik.", "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void updateBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih buku dari tabel untuk diperbarui.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField titleField = new JTextField((String) tableModel.getValueAt(selectedRow, 1), 20);
        JTextField authorField = new JTextField((String) tableModel.getValueAt(selectedRow, 2), 20);
        JTextField isbnField = new JTextField((String) tableModel.getValueAt(selectedRow, 3), 20);
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Available", "Borrowed"});
        statusComboBox.setSelectedItem(tableModel.getValueAt(selectedRow, 4));

        panel.add(new JLabel("Judul:"));
        panel.add(titleField);
        panel.add(new JLabel("Penulis:"));
        panel.add(authorField);
        panel.add(new JLabel("ISBN:"));
        panel.add(isbnField);
        panel.add(new JLabel("Status:"));
        panel.add(statusComboBox);

        int option = JOptionPane.showConfirmDialog(this, panel, "Perbarui Buku", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            if (titleField.getText().trim().isEmpty() || authorField.getText().trim().isEmpty() || isbnField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Judul, Penulis, dan ISBN tidak boleh kosong.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Book book = new Book();
            book.setId(id);
            book.setTitle(titleField.getText().trim());
            book.setAuthor(authorField.getText().trim());
            book.setIsbn(isbnField.getText().trim());
            book.setStatus((String) statusComboBox.getSelectedItem());
            try {
                bookDAO.updateBook(book);
                loadBooks();
                JOptionPane.showMessageDialog(this, "Buku berhasil diperbarui.");
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
}