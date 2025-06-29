package librarybooks.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import librarybooks.database.Database;
import librarybooks.model.User;

/**
 * Classe DAO para a entidade User.
 * Fornece métodos para interagir com a tabela 'users' no banco de dados.
 */
public class UserDAO {

    /**
     * Adiciona um novo usuário ao banco de dados.
     * @param user O objeto User a ser adicionado.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
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

    /**
     * Retorna uma lista de todos os usuários no banco de dados.
     * @return Uma lista de objetos User.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
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
    
    /**
     * Retorna um usuário específico pelo seu ID.
     * @param id O ID do usuário a ser buscado.
     * @return O objeto User correspondente ao ID, ou null se não for encontrado.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
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
    
    /**
     * Exclui um usuário do banco de dados.
     * @param id O ID do usuário a ser excluído.
     * @return true se o usuário foi excluído com sucesso, false caso contrário.
     * @throws UnsupportedOperationException Se o método não estiver implementado.
     */
    public static boolean deleteUser(int id) throws SQLException {
        String checkSql = "SELECT COUNT(*) as loan_count FROM loans WHERE user_id = ? AND return_date IS NULL";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next() && rs.getInt("loan_count") > 0) {
                return false; 
            }
            
            String deleteSql = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setInt(1, id);
                int affectedRows = deleteStmt.executeUpdate();
                return affectedRows > 0;
            }
        }
    }
    
    /**
     * Atualiza o status de empréstimo de um usuário.
     * @param userId O ID do usuário a ser atualizado.
     * @param hasLoan O novo status de empréstimo.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public static void updateUserLoanStatus(int userId, boolean hasLoan) throws SQLException {
        String sql = "UPDATE users SET has_loan = ? WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, hasLoan);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        }
    }
    
}