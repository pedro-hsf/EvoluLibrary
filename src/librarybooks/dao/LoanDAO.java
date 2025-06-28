package librarybooks.dao;

import java.sql.*;
import librarybooks.database.Database;
import librarybooks.model.Loan;

public class LoanDAO {

    public static void addLoan(Loan loan) throws SQLException {
        String sql = "INSERT INTO loans(book_id, user_id, loan_date) VALUES(?, ?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                
            pstmt.setInt(1, loan.getBook().getId());
            pstmt.setInt(2, loan.getUser().getId());
            pstmt.setString(3, loan.getLoanDate().toString());
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    loan.setId(rs.getInt(1));
                }
            }
            
            
            BookDAO.updateBookAvailability(loan.getBook().getId(), false);
            UserDAO.updateUserLoanStatus(loan.getUser().getId(), true);
        }
    }
}