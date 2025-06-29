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

            System.out.print("Escolha uma opçao: ");
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
                    System.out.println("Opçao inválida!");
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
                    System.out.println("Opçao invalida!");
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

    private static void manageBooks() throws SQLException {
        while (true) {
            System.out.println("\n======= GERENCIAR LIVROS =======");
            System.out.println("1. Adicionar Livro");
            System.out.println("2. Listar Livros");
            System.out.println("3. Excluir Livro");
            System.out.println("4. Voltar");
            System.out.println("=================================\n");

            System.out.print("Escolha uma opção: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    listBooks();
                    break;
                case 3:
                    deleteBook();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void addBook() throws SQLException {
        System.out.println("\n-- ADICIONAR LIVRO --\n");
        
        System.out.print("Digite o titulo do livro: ");
        String title = scanner.nextLine();
         
        System.out.println("\nID | NOME");
        AuthorDAO.getAllAuthors().forEach(author ->
            System.out.printf("%2d | %s%n", author.getId(), author.getName()));

        System.out.print("\nDigite o ID do autor: ");
        int authorId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o genero: ");
        String genre = scanner.nextLine();

        try {
            Author author = AuthorDAO.getAuthorById(authorId);
            if (author == null) {
                System.out.println("Autor não encontrado!");
                return;
            }

            Book book = new Book(title, author, genre);
            BookDAO.addBook(book);

            AuthorDAO.updateBooksCount(authorId, 1);
            System.out.println("Livro adicionado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar livro: " + e.getMessage());
        }
    }

    private static void listBooks() {
        System.out.println("\n-- LISTAR LIVROS --");
        System.out.println("ID | TITULO | AUTOR | DISPONIVEL");
        
        try {
            List<Book> books = BookDAO.getAllBooks();
            for (Book book : books) {
                System.out.println(book.getId() + " | " + book.getTitle() + " | " + book.getAuthor().getName() + " | " + (book.isAvailable() ? "Sim" : "Não"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar livros: " + e.getMessage());
        }
    }

    private static void deleteBook() throws SQLException {
        System.out.println("\n-- EXCLUIR LIVRO --\n");
        System.out.println("ID | TITULO");

        BookDAO.getAllBooks().forEach(book ->
            System.out.printf("%2d | %s%n", book.getId(), book.getTitle()));

        System.out.print("\nSelecione o ID do livro: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        try {
            Book book = BookDAO.getBookById(id);
            if (book != null && !book.isAvailable()) {
                System.out.println("Não é possível excluir livros emprestados!");
                return;
            }
            
            BookDAO.deleteBook(id);
            System.out.println("Livro excluído com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir livro: " + e.getMessage());
        }
    }

    private static void manageLoans() throws SQLException {
        while (true) {
            System.out.println("\n======= GERENCIAR EMPRESTIMOS =======\n");
            System.out.println("1. Realizar Emprestimo");
            System.out.println("2. Devolver Livro");
            System.out.println("3. Listar Emprestimos Ativos");
            System.out.println("4. Voltar");
            System.out.println("=================================\n");

            System.out.print("Escolha uma opção: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    makeLoan();
                    break;
                case 2:
                    returnBook();
                    break;
                case 3:
                    listActiveLoans();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void makeLoan() throws SQLException {
        System.out.println("\n==== REALIZAR EMPRESTIMO ====\n");
        UserDAO.getAllUsers().forEach(user ->
            System.out.printf("%2d | %s%n", user.getId(), user.getName()));
    
        System.out.print("\nDigite o ID do usuario: ");
        int userId = scanner.nextInt();

        System.out.println("ID | TITULO");
        BookDAO.getAllBooks().forEach(book ->
            System.out.printf("%2d | %s%n", book.getId(), book.getTitle()));

        System.out.print("\nDigite o ID do livro: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        
        try {
            User user = UserDAO.getUserById(userId);
            Book book = BookDAO.getBookById(bookId);
            
            if (user == null || book == null) {
                System.out.println("Usuário ou livro não encontrado!");
                return;
            }
            
            if (user.hasLoan()) {
                System.out.println("Usuario ja possui emprestimo ativo!");
                return;
            }
            
            if (!book.isAvailable()) {
                System.out.println("Livro indisponivel para emprestimo!");
                return;
            }
            
            Loan loan = new Loan(book, user);
            LoanDAO.addLoan(loan);
            System.out.println("Emprestimo realizado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao realizar empréstimo: " + e.getMessage());
        }
    }

    private static void returnBook() {
        System.out.println("\n==== DEVOLVER LIVRO ====\n");
        System.out.println("Listar emprestimos ativos:");
        
        try {
            List<Loan> activeLoans = LoanDAO.getActiveLoans();
        
            if (activeLoans.isEmpty()) {
            System.out.println("Não há empréstimos ativos no momento.");
            return;
        }
        
            System.out.println("\nEMPRÉSTIMOS ATIVOS:");
            System.out.println("ID | USUÁRIO | LIVRO | DATA EMPRÉSTIMO");
            System.out.println("--------------------------------------");
        
            for (Loan loan : activeLoans) {
                System.out.printf("%2d | %-15s | %-20s | %s%n", loan.getId(), loan.getUser().getName(), loan.getBook().getTitle(), loan.getLoanDate());
        }
        
            System.out.print("\nDigite o ID do empréstimo para devolução: ");
            int loanId = scanner.nextInt();
            scanner.nextLine(); 
        
            boolean success = LoanDAO.returnLoan(loanId);
        
            if (success) {
                Loan loan = activeLoans.stream().filter(l -> l.getId() == loanId).findFirst().orElse(null);
            
                if (loan != null) {
                    BookDAO.updateBookAvailability(loan.getBook().getId(), true);
                    UserDAO.updateUserLoanStatus(loan.getUser().getId(), false);
            }
            
            System.out.println("Livro devolvido com sucesso!");
        }
        
          else {
            System.out.println("Não foi possível devolver o livro. Verifique o ID do empréstimo.");
        }
        
    } catch (SQLException e) {
        System.out.println("Erro ao processar devolução: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("Erro inesperado: " + e.getMessage());
    }
}

    private static void listActiveLoans() {
        System.out.println("\n==== LISTAR EMPRESTIMOS ATIVOS ====");
        
        try {
            List<Loan> activeLoans = LoanDAO.getActiveLoans();

            if (activeLoans.isEmpty()){
                System.out.println("Nenhum emprestimo ativo no momento!");
                return;
            }

            System.out.println("ID | USUÁRIO | LIVRO | DATA EMPRÉSTIMO");

            for (Loan loan : activeLoans)  {
                System.out.printf("%2d | %-15s | %-20s | %s%n", loan.getId(), loan.getUser().getName(), loan.getBook().getTitle(), loan.getLoanDate());
            }
        }   catch (SQLException e) {
            System.out.println("Erro ao listar emprestimos ativos: " + e.getMessage());
        }
    }

    private static void viewAllData() {
        System.out.println("\n======= VISUALIZAR TODOS OS DADOS =======");
        
        System.out.println("\nLista de Autores");
        listAuthors();
        
        System.out.println("\nLista de Usuarios");
        listUsers();
        
        System.out.println("\nLista de Livros");
        listBooks();
        
        System.out.println("\nLista de Emprestimos Ativos");
        listActiveLoans();
        
        System.out.println("\n=================================");
    }
}
