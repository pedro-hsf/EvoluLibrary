package librarybooks.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import librarybooks.database.Database;
import librarybooks.model.Author;
import librarybooks.model.Book;

public class BookDAO {

    public static void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books(title, author_id, genre, available) VALUES(?, ?, ?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setInt(2, book.getAuthor().getId());
            pstmt.setString(3, book.getGenre()); 
            pstmt.setBoolean(4, book.isAvailable());
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    book.setId(rs.getInt(1));
                }
            }
        }
    }

    public static List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, a.name as author_name, a.main_genre as author_genre " +
                     "FROM books b JOIN authors a ON b.author_id = a.id";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Author author = new Author(
                    rs.getString("author_name"),
                    rs.getString("author_genre") 
                );
                author.setId(rs.getInt("author_id"));
                
                Book book = new Book(
                    rs.getString("title"),
                    author,
                    rs.getString("genre") 
                );
                book.setId(rs.getInt("id"));
                book.setAvailable(rs.getBoolean("available"));
                books.add(book);
            }
        }
        return books;
    }

    public static Book getBookById(int id) throws SQLException {
        String sql = "SELECT b.*, a.name as author_name, a.main_genre as author_genre " +
                     "FROM books b JOIN authors a ON b.author_id = a.id WHERE b.id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Author author = new Author(
                        rs.getString("author_name"),
                        rs.getString("author_genre")
                    );
                    author.setId(rs.getInt("author_id"));
                    
                    Book book = new Book(
                        rs.getString("title"),
                        author,
                        rs.getString("genre")
                    );
                    book.setId(id);
                    book.setAvailable(rs.getBoolean("available"));
                    return book;
                }
            }
        }
        return null;
    }
    
    public static void updateBookAvailability(int bookId, boolean available) throws SQLException {
        String sql = "UPDATE books SET available = ? WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, available);
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();
        }
    }

    public static void deleteBook(int id) throws SQLException {
    String sql = "DELETE FROM books WHERE id = ?";
    
    try (Connection conn = Database.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }
}
}