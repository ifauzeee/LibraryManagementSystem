package com.library.dao;

import com.library.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {

    public UserDAO() {
        // Konstruktor kosong
    }

    public boolean addUser(String username, String password, String fullName, String email, String role) throws SQLException {
        if (getUserByUsername(username) != null) {
            System.out.println("Username " + username + " already exists. Failed to add user.");
            return false;
        }
        if (email != null && !email.isEmpty() && getUserByEmail(email) != null) {
            System.out.println("Email " + email + " is already registered. Failed to add user.");
            return false;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        String sql = "INSERT INTO users (username, password_hash, full_name, email, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, fullName);
            stmt.setString(4, email);
            stmt.setString(5, role);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Failed to add user, username or email might already exist. " + e.getMessage());
            return false;
        }
    }

    public boolean updateUser(User user) throws SQLException {
        User existingUserWithEmail = getUserByEmail(user.getEmail());
        if (existingUserWithEmail != null && existingUserWithEmail.getId() != user.getId()) {
            System.err.println("Email " + user.getEmail() + " is already used by another user.");
            return false;
        }

        String sql = "UPDATE users SET username = ?, full_name = ?, email = ?, role = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFullName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole());
            stmt.setInt(5, user.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean updatePassword(int userId, String newPassword) throws SQLException {
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
        String sql = "UPDATE users SET password_hash = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hashedPassword);
            stmt.setInt(2, userId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        }
        return null;
    }

    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        }
        return null;
    }

    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        }
        return users;
    }

    public User login(String username, String password) throws SQLException {
        User user = getUserByUsername(username);
        if (user != null) {
            if (BCrypt.checkpw(password, user.getPasswordHash())) {
                return user;
            }
        }
        return null;
    }

    public boolean deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getString("role"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setUpdatedAt(rs.getTimestamp("updated_at"));
        return user;
    }
}