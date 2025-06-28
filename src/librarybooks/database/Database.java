package librarybooks.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:sqlite:library.db";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
    
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute("CREATE TABLE IF NOT EXISTS authors (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "main_genre TEXT," +
                "books_in_collection INTEGER DEFAULT 0" +
                ")");
            
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "has_loan BOOLEAN DEFAULT FALSE" +
                ")");
            
            stmt.execute("CREATE TABLE IF NOT EXISTS books (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "author_id INTEGER NOT NULL," +
                "genre TEXT," + 
                "available BOOLEAN DEFAULT TRUE," +
                "FOREIGN KEY (author_id) REFERENCES authors(id)" +
                ")");
            
            stmt.execute("CREATE TABLE IF NOT EXISTS loans (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "book_id INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "loan_date TEXT NOT NULL," +
                "return_date TEXT," +
                "FOREIGN KEY (book_id) REFERENCES books(id)," +
                "FOREIGN KEY (user_id) REFERENCES users(id)" +
                ")");
            
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_author_genre ON authors(main_genre)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_author_name ON authors(name)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_book_title ON books(title)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_book_available ON books(available)");
            
        } catch (SQLException e) {
            System.err.println("Erro ao criar banco de dados: " + e.getMessage());
        }
    }

    public static void closeConnection(Connection conn) {
    try {
        if (conn != null) {
            conn.close();
        }
    } catch (SQLException e) {
        System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}