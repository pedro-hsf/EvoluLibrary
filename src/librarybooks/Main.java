package librarybooks;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import librarybooks.database.Database;
import librarybooks.service.MenuService;

/**
 * Classe principal responsável por iniciar o sistema da biblioteca.
 *
 * Inicializa o banco de dados, cria a conexão e inicia o menu principal para interação com o usuário.
 *
 * Gerencia também o tratamento de exceções e garante o fechamento adequado
 * de recursos como o scanner e a conexão com o banco.
 * 
 * @author Pedro
 */
public class Main {

    /**
     * Método principal que inicia a execução do sistema da biblioteca.
     * @param args Argumentos da linha de comando (não utilizados neste sistema).
     */
    public static void main(String[] args) {
        Scanner scanner = null;
        Connection conn = null;

        try {
            scanner = new Scanner(System.in);
            Database.initializeDatabase();
            conn = Database.getConnection();
            
            MenuService menuService = new MenuService(scanner);
            menuService.start();
            
        } catch (SQLException e) {
            System.err.println("Falha crítica no banco de dados: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        
            if (conn != null) {   
            System.out.println("Sistema encerrado.");
      }
    }
  }
}