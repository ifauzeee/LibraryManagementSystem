package com.library.dao;

import com.library.model.Transaction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    public void addTransaction(Transaction transaction) throws SQLException {
        // Tambahkan 'quantity' ke INSERT
        String sql = "INSERT INTO transactions (book_id, borrower_id, borrow_date, status, quantity) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getBookId());
            stmt.setInt(2, transaction.getBorrowerId());
            stmt.setDate(3, new java.sql.Date(transaction.getBorrowDate().getTime()));
            stmt.setString(4, transaction.getStatus());
            stmt.setInt(5, transaction.getQuantity()); // BARU
            stmt.executeUpdate();
        }
    }

    public void updateTransactionReturnDate(Transaction transaction) throws SQLException {
        String sql = "UPDATE transactions SET return_date = ? WHERE transaction_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(transaction.getReturnDate().getTime()));
            stmt.setInt(2, transaction.getId());
            stmt.executeUpdate();
        }
    }

    public void updateTransactionStatus(int transactionId, String status) throws SQLException {
        String sql = "UPDATE transactions SET status = ? WHERE transaction_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, transactionId);
            stmt.executeUpdate();
        }
    }

    public void deleteTransaction(int transactionId) throws SQLException {
        String sql = "DELETE FROM transactions WHERE transaction_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            stmt.executeUpdate();
        }
    }

    public List<Transaction> getAllTransactions() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        // Ambil juga kolom 'quantity'
        String sql = "SELECT * FROM transactions";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("transaction_id"));
                transaction.setBookId(rs.getInt("book_id"));
                transaction.setBorrowerId(rs.getInt("borrower_id"));
                transaction.setBorrowDate(rs.getDate("borrow_date"));
                transaction.setReturnDate(rs.getDate("return_date"));
                transaction.setStatus(rs.getString("status"));
                transaction.setQuantity(rs.getInt("quantity")); // BARU
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    public Transaction getTransactionById(int transactionId) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setId(rs.getInt("transaction_id"));
                    transaction.setBookId(rs.getInt("book_id"));
                    transaction.setBorrowerId(rs.getInt("borrower_id"));
                    transaction.setBorrowDate(rs.getDate("borrow_date"));
                    transaction.setReturnDate(rs.getDate("return_date"));
                    transaction.setStatus(rs.getString("status"));
                    transaction.setQuantity(rs.getInt("quantity")); // BARU
                    return transaction;
                }
            }
        }
        return null;
    }
}