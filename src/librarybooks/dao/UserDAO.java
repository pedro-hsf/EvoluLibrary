package librarybooks.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import librarybooks.database.Database;
import librarybooks.model.User;

public class UserDAO {

    public static void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users(name) VALUES(?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                
            pstmt.setString(1, user.getName());
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }
        }
    }
    
    public static List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                User user = new User(rs.getString("name"));
                user.setId(rs.getInt("id"));
                user.setHasLoan(rs.getBoolean("has_loan"));
                users.add(user);
            }
        }
        return users;
    }
    
    public static User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(rs.getString("name"));
                    user.setId(id);
                    user.setHasLoan(rs.getBoolean("has_loan"));
                    return user;
                }
            }
        }
        return null;
    }
    
    public static void updateUserLoanStatus(int userId, boolean hasLoan) throws SQLException {
        String sql = "UPDATE users SET has_loan = ? WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, hasLoan);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        }
    }

    public static boolean deleteUser(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }
}
