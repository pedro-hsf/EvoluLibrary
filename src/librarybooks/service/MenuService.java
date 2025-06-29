package librarybooks.service;

import java.util.Scanner;
import librarybooks.dao.*;
import librarybooks.database.Database;
import librarybooks.model.*;

import java.sql.SQLException;
import java.util.List;

public class MenuService {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        Database.initializeDatabase();
        showMainMenu();
    }

    private static void showMainMenu() throws SQLException {
        while (true) {
            System.out.println("\n======= BEM VINDO(A) A EVOLULIBRARY!!! =======\n");
            System.out.println("O QUE VAMOS FAZER HOJE?\n");
            System.out.println("1. Gerenciar Autores");
            System.out.println("2. Gerenciar Usuarios");
            System.out.println("3. Gerenciar Livros");
            System.out.println("4. Gerenciar Emprestimos");
            System.out.println("5. Visualizar Todos os Dados");
            System.out.println("0. Sair");
            System.out.println("==============================\n");

            System.out.print("Escolha uma opção: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    manageAuthors();
                    break;
                case 2:
                    manageUsers();
                    break;
                case 3:
                    manageBooks();
                    break;
                case 4:
                    manageLoans();
                    break;
                case 5:
                    viewAllData();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void manageAuthors() throws SQLException {
        while (true) {
            System.out.println("\n======= GERENCIAR AUTORES =======\n");
            System.out.println("1. Adicionar Autor");
            System.out.println("2. Listar Autores");
            System.out.println("3. Excluir Autor");
            System.out.println("4. Voltar");
            System.out.println("=================================\n");

            System.out.print("Escolha uma opção: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addAuthor();
                    break;
                case 2:
                    listAuthors();
                    break;
                case 3:
                    deleteAuthor();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void addAuthor() {
        System.out.println("\n-- ADICIONAR AUTOR --");
        System.out.print("Digite o nome do autor: ");
        String name = scanner.nextLine();
        System.out.print("Digite o genero principal: ");
        String genre = scanner.nextLine();

        try {
            Author author = new Author(name, genre);
            AuthorDAO.addAuthor(author);
            System.out.println("Autor adicionado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar autor: " + e.getMessage());
        }
    }

    private static void listAuthors() {
        System.out.println("\n-- LISTAR AUTORES --");
        System.out.println("ID | NOME | GENERO | LIVROS");
        
        try {
            List<Author> authors = AuthorDAO.getAllAuthors();
            for (Author author : authors) {
                System.out.println(author.getId() + " | " + author.getName() + " | " + author.getMainGenre() + " | " + author.getBooksInCollection());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar autores: " + e.getMessage());
        }
    }

    private static void deleteAuthor() throws SQLException {
        System.out.println("\n-- EXCLUIR AUTOR --\n");
        System.out.println("ID | NOME\n");

        AuthorDAO.getAllAuthors().forEach(author ->
            System.out.println(author.getId() + " | " + author.getName()));

        System.out.print("\nSelecione o ID do autor: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        try {
            boolean deleted = AuthorDAO.deleteAuthor(id);
            if (deleted) {
                System.out.println("Autor excluído com sucesso!");
            } else {
                System.out.println("Não é possível excluir autores com livros associados!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir autor: " + e.getMessage());
        }
    }

    private static void manageUsers() throws SQLException {
        while (true) {
            System.out.println("\n======= GERENCIAR USUARIOS =======\n");
            System.out.println("1. Adicionar Usuario");
            System.out.println("2. Listar Usuarios");
            System.out.println("3. Excluir Usuario");
            System.out.println("4. Voltar");
            System.out.println("=================================\n");

            System.out.print("Escolha uma opção: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    listUsers();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void addUser() {
        System.out.println("\n-- ADICIONAR USUARIO --\n");
        System.out.print("Digite o nome do usuario: ");
        String name = scanner.nextLine();

        try {
            User user = new User(name);
            UserDAO.addUser(user);
            System.out.println("Usuario adicionado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar usuário: " + e.getMessage());
        }
    }

    private static void listUsers() {
        System.out.println("\n-- LISTAR USUARIOS --");
        System.out.println("ID | NOME | EMPRESTIMO");
        
        try {
            List<User> users = UserDAO.getAllUsers();
            for (User user : users) {
                System.out.println(user.getId() + " | " + user.getName() + " | " + (user.hasLoan() ? "Sim" : "Não"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }
    }

    private static void deleteUser() throws SQLException {
        System.out.println("\n-- EXCLUIR USUARIO --\n");
        System.out.println("ID | NOME | TEM EMPRÉSTIMO\n");

        UserDAO.getAllUsers().forEach(user -> 
        System.out.println(user.getId() + " | " + user.getName() + " | " + (user.hasLoan() ? "Sim" : "Não")));
    
        System.out.print("\nSelecione o ID do usuario: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        try {
            User user = UserDAO.getUserById(id);
            if (user != null && user.hasLoan()) {
                System.out.println("Não é possível excluir usuarios com emprestimos ativos!");
                return;
            }
            
            boolean deleted = UserDAO.deleteUser(id);
            if (deleted) {
                System.out.println("Usuário excluído com sucesso!");
            } else {
                System.out.println("Erro ao excluir usuário!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir usuário: " + e.getMessage());
        }
    }   
}