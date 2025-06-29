package librarybooks.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import librarybooks.database.Database;
import librarybooks.model.Author;

/**
 * Classe DAO para a entidade Author.
 * Fornece métodos para interagir com a tabela 'authors' no banco de dados.
 */
public class AuthorDAO {
    
    /**
     * Adiciona um novo autor ao banco de dados.
     * O ID do autor é gerado automaticamente pelo banco de dados e definido no objeto Author.
     * @param author O objeto Author a ser adicionado.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public static void addAuthor(Author author) throws SQLException {
        String sql = "INSERT INTO authors(name, main_genre, books_in_collection) VALUES(?, ?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, author.getName());
            pstmt.setString(2, author.getMainGenre());
            pstmt.setInt(3, author.getBooksInCollection());
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    author.setId(rs.getInt(1));
                }
            }
        }
    }
    
    /**
     * Retorna uma lista de todos os autores presentes no banco de dados.
     * @return Uma lista de objetos Author.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public static List<Author> getAllAuthors() throws SQLException {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM authors";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Author author = new Author(
                    rs.getString("name"),
                    rs.getString("main_genre")
                );
                author.setId(rs.getInt("id"));
                author.setBooksInCollection(rs.getInt("books_in_collection"));
                authors.add(author);
            }
        }
        return authors;
    }
    
    /**
     * Retorna um autor específico pelo seu ID.
     * @param id O ID do autor a ser buscado.
     * @return O objeto Author correspondente ao ID, ou null se não for encontrado.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public static Author getAuthorById(int id) throws SQLException {
        String sql = "SELECT * FROM authors WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Author author = new Author(
                    rs.getString("name"),
                    rs.getString("main_genre")
                );
                author.setId(id);
                author.setBooksInCollection(rs.getInt("books_in_collection"));
                return author;
            }
        }
        return null;
    }
    
    /**
     * Atualiza a quantidade de livros para um autor específico.
     * @param authorId O ID do autor.
     * @param increment O valor a ser adicionado na quantidade de livros.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public static void updateBooksCount(int authorId, int increment) throws SQLException {
        String sql = "UPDATE authors SET books_in_collection = books_in_collection + ? WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, increment);
            pstmt.setInt(2, authorId);
            pstmt.executeUpdate();
        }
    }
    
    /**
     * Exclui um autor do banco de dados.
     * Um autor só pode ser excluído se não tiver livros associados na coleção.
     * @param authorId O ID do autor a ser excluído.
     * @return true se o autor foi excluído com sucesso, false caso contrário
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public static boolean deleteAuthor(int authorId) throws SQLException {
        
        Author author = getAuthorById(authorId);
        if (author != null && author.getBooksInCollection() > 0) {
            return false; 
        }
        
        String sql = "DELETE FROM authors WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, authorId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}

