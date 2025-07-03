package com.library.dao;

import com.library.model.Transaction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    public void addTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (book_id, borrower_id, borrow_date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getBookId());
            stmt.setInt(2, transaction.getBorrowerId());
            stmt.setDate(3, new java.sql.Date(transaction.getBorrowDate().getTime()));
            stmt.executeUpdate();
        }
    }

    public void updateTransaction(Transaction transaction) throws SQLException {
        String sql = "UPDATE transactions SET return_date = ? WHERE transaction_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, transaction.getReturnDate() != null ? new java.sql.Date(transaction.getReturnDate().getTime()) : null);
            stmt.setInt(2, transaction.getId());
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
        String sql = "SELECT t.*, b.title, br.name FROM transactions t " +
                "JOIN books b ON t.book_id = b.book_id " +
                "JOIN borrowers br ON t.borrower_id = br.borrower_id";
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
                transactions.add(transaction);
            }
        }
        return transactions;
    }
}