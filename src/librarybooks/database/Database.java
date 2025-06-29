package librarybooks.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe responsável pela conexão e estruturação do banco de dados.
 * Utiliza SQLite para armazenar informações sobre autores, usuários, livros e empréstimos.
 */
public class Database {

    /** URL de conexão com o banco de dados. */
    private static final String URL = "jdbc:sqlite:library.db";
    
    /**
     * Estabelece e retorna uma conexão com o banco de dados.
     *
     * @return Objeto Connection representando a conexão com o banco de dados.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
    
     /**
     * Inicializa o banco de dados, criando as tabelas e índices caso ainda não existam.
     */
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
            
            // Índices para melhorar o desempenho das consultas
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_author_genre ON authors(main_genre)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_author_name ON authors(name)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_book_title ON books(title)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_book_available ON books(available)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_loan_return_date ON loans(return_date)");
            
        } catch (SQLException e) {
            System.err.println("Erro ao criar banco de dados: " + e.getMessage());
        }
    }

    /**
     * Fecha a conexão com o banco de dados.
     *
     * @param conn Objeto Connection que será fechado.
     */
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