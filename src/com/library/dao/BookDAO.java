package com.library.dao;

import com.library.model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books (title, author, isbn, total_copies, available_copies) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setInt(4, book.getTotalCopies());
            stmt.setInt(5, book.getAvailableCopies());
            stmt.executeUpdate();
        }
    }

    public void updateBook(Book book) throws SQLException {
        String sql = "UPDATE books SET title = ?, author = ?, isbn = ?, total_copies = ?, available_copies = ? WHERE book_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setInt(4, book.getTotalCopies());
            stmt.setInt(5, book.getAvailableCopies());
            stmt.setInt(6, book.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteBook(int bookId) throws SQLException {
        String sql = "DELETE FROM books WHERE book_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
        }
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setTotalCopies(rs.getInt("total_copies"));
                book.setAvailableCopies(rs.getInt("available_copies"));
                books.add(book);
            }
        }
        return books;
    }

    public Book getBookById(int bookId) throws SQLException {
        String sql = "SELECT * FROM books WHERE book_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setIsbn(rs.getString("isbn"));
                    book.setTotalCopies(rs.getInt("total_copies"));
                    book.setAvailableCopies(rs.getInt("available_copies"));
                    return book;
                }
            }
        }
        return null;
    }

    // BARU: Metode untuk mengurangi jumlah buku yang tersedia (saat disetujui pinjam)
    public void decreaseAvailableCopies(int bookId, int quantity) throws SQLException { // Parameter quantity
        String sql = "UPDATE books SET available_copies = available_copies - ? WHERE book_id = ? AND available_copies >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, bookId);
            stmt.setInt(3, quantity); // Pastikan ada cukup stok
            stmt.executeUpdate();
        }
    }

    // BARU: Metode untuk menambah jumlah buku yang tersedia (saat dikembalikan)
    public void increaseAvailableCopies(int bookId, int quantity) throws SQLException { // Parameter quantity
        String sql = "UPDATE books SET available_copies = available_copies + ? WHERE book_id = ? AND available_copies + ? <= total_copies";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, bookId);
            stmt.setInt(3, quantity); // Pastikan tidak melebihi total_copies
            stmt.executeUpdate();
        }
    }
}