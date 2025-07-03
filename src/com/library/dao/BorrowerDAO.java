package com.library.dao;

import com.library.model.Borrower;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowerDAO {
    public void addBorrower(Borrower borrower) throws SQLException {
        String sql = "INSERT INTO borrowers (name, email, phone) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, borrower.getName());
            stmt.setString(2, borrower.getEmail());
            stmt.setString(3, borrower.getPhone());
            stmt.executeUpdate();
        }
    }

    public void updateBorrower(Borrower borrower) throws SQLException {
        String sql = "UPDATE borrowers SET name = ?, email = ?, phone = ? WHERE borrower_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, borrower.getName());
            stmt.setString(2, borrower.getEmail());
            stmt.setString(3, borrower.getPhone());
            stmt.setInt(4, borrower.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteBorrower(int borrowerId) throws SQLException {
        String sql = "DELETE FROM borrowers WHERE borrower_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, borrowerId);
            stmt.executeUpdate();
        }
    }

    public List<Borrower> getAllBorrowers() throws SQLException {
        List<Borrower> borrowers = new ArrayList<>();
        String sql = "SELECT * FROM borrowers";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Borrower borrower = new Borrower();
                borrower.setId(rs.getInt("borrower_id"));
                borrower.setName(rs.getString("name"));
                borrower.setEmail(rs.getString("email"));
                borrower.setPhone(rs.getString("phone"));
                borrowers.add(borrower);
            }
        }
        return borrowers;
    }
}