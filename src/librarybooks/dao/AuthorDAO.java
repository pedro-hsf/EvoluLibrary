package librarybooks.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import librarybooks.database.Database;
import librarybooks.model.Author;

public class AuthorDAO {
    
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
    
    
    public static void updateBooksCount(int authorId, int increment) throws SQLException {
        String sql = "UPDATE authors SET books_in_collection = books_in_collection + ? WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, increment);
            pstmt.setInt(2, authorId);
            pstmt.executeUpdate();
        }
    }
    
    
    public static List<Author> getAuthorsByGenre(String genre) throws SQLException {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM authors WHERE main_genre = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, genre);
            ResultSet rs = pstmt.executeQuery();
            
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
    
    
    public static void updateAuthorGenre(int authorId, String newGenre) throws SQLException {
        String sql = "UPDATE authors SET main_genre = ? WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, newGenre);
            pstmt.setInt(2, authorId);
            pstmt.executeUpdate();
        }
    }
    
    
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