package librarybooks.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import librarybooks.database.Database;
import librarybooks.model.Loan;

/**
 * Classe DAO para a entidade Loan.
 * Fornece métodos para interagir com a tabela 'loans' no banco de dados.
 */
public class LoanDAO {

    /**
     * Adiciona um novo empréstimo ao banco de dados.
     * Também atualiza a disponibilidade do livro e o status de empréstimo do usuário.
     * @param loan Objeto Loan contendo o livro, o usuário e a data do empréstimo.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
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

    /**
     * Lista todos os empréstimos ativos do banco de dados.
     * @return Lista de objetos Loan representando os empréstimos em aberto.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public static List<Loan> getActiveLoans() throws SQLException {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans WHERE return_date IS NULL";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Loan loan = new Loan(
                    BookDAO.getBookById(rs.getInt("book_id")),
                    UserDAO.getUserById(rs.getInt("user_id"))
                );
                loan.setId(rs.getInt("id"));
                loan.setLoanDate(LocalDate.parse(rs.getString("loan_date")));
                loans.add(loan);
            }
        }
        return loans;
    }

    /**
     * Registra a devolução de um empréstimo no banco de dados, atualizando a data de retorno.
     * @param loanId ID do empréstimo que será devolvido.
     * @return true se a devolução foi registrada com sucesso, false caso contrário.
     * @throws SQLException Caso ocorra algum erro ao executar a atualização no banco de dados.
     */
    public static boolean returnLoan(int loanId) throws SQLException {
        String sql = "UPDATE loans SET return_date = ? WHERE id = ? AND return_date IS NULL";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, LocalDate.now().toString());
            pstmt.setInt(2, loanId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
