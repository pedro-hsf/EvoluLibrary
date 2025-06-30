package librarybooks.service;

import java.util.Scanner;
import librarybooks.dao.*;
import librarybooks.model.*;

import java.sql.SQLException;
import java.util.List;

/**
 * A classe MenuService é responsável por gerenciar a interface de usuário
 * e a interação com o sistema da EvoluLibrary.
 *
 * Ela fornece menus para gerenciar autores, usuários, livros e empréstimos,
 * além de permitir a visualização de todos os dados.
 */
public class MenuService {
    private final Scanner scanner;

    /**
     * Construtor da classe MenuService. 
     * @param scanner Scanner utilizado para entrada do usuário.
     */
    public MenuService(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Inicia o menu principal do sistema.
     * @throws SQLException se houver erro ao acessar o banco de dados.
     */
    public void start() throws SQLException {
        showMainMenu();
    }

    /**
     * Exibe o menu principal da aplicação e gerencia as opções do usuário.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados durante as operações.
     */
    private void showMainMenu() throws SQLException {
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
                    System.out.println("Opcao invalida!");
            }
        }
    }

    /**
     * Gerencia as operações relacionadas a autores, como adicionar, listar e excluir.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados durante as operações com autores.
     */
    private void manageAuthors() throws SQLException {
        while (true) {
            System.out.print("\n======= GERENCIAR AUTORES =======\n");
            System.out.println("1. Adicionar Autor");
            System.out.println("2. Listar Autores");
            System.out.println("3. Excluir Autor");
            System.out.println("4. Voltar");
            System.out.println("=================================");

            System.out.print("Escolha uma opcao: ");
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
                    System.out.println("Opcao invalida!");
            }
        }
    }

    /**
     * Adiciona um novo autor ao sistema.
     * Solicita o nome e o gênero principal do autor ao usuário e o adiciona ao banco de dados.
     */
    private void addAuthor() {
        System.out.print("\n---- ADICIONAR AUTOR ----\n");
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

    /**
     * Lista todos os autores cadastrados no sistema.
     * Exibe o ID, nome, gênero principal e o número de livros na coleção de cada autor.
     */
    private void listAuthors() {
        System.out.println("\n---- LISTA DE AUTORES ----\n");
        System.out.printf("%-3s | %-20s | %-10s | %-6s%n", "ID", "NOME", "GENERO", "LIVROS");
        
        try {
            List<Author> authors = AuthorDAO.getAllAuthors();
            for (Author author : authors) {
                System.out.printf("%-3d | %-20s | %-10s | %-6d%n", author.getId(), author.getName(), author.getMainGenre(), author.getBooksInCollection());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar autores: " + e.getMessage());
        }
    }

    /**
     * Exclui um autor do sistema.
     * Solicita o ID do autor a ser excluído e verifica se o autor possui livros associados antes de realizar a exclusão.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados durante a exclusão.
     */
    private void deleteAuthor() throws SQLException {
        System.out.println("\n---- EXCLUIR AUTOR ----\n");
        System.out.printf("%-3s | %-20s%n", "ID", "NOME");

        AuthorDAO.getAllAuthors().forEach(author ->
            System.out.printf("%-3d | %-20s%n", author.getId(), author.getName()));

        System.out.print("\nSelecione o ID do autor: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        try {
            boolean deleted = AuthorDAO.deleteAuthor(id);
            if (deleted) {
                System.out.println("Autor excluido com sucesso!");
            } else {
                System.out.println("Nao e possivel excluir autores com livros associados!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir autor: " + e.getMessage());
        }
    }

    /**
     * Gerencia as operações relacionadas a usuários, como adicionar, listar e excluir.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados durante as operações com usuários.
     */
    private void manageUsers() throws SQLException {
        while (true) {
            System.out.print("\n======= GERENCIAR USUARIOS =======\n");
            System.out.println("1. Adicionar Usuario");
            System.out.println("2. Listar Usuarios");
            System.out.println("3. Excluir Usuario");
            System.out.println("4. Voltar");
            System.out.println("=================================");

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

    /**
     * Adiciona um novo usuário ao sistema.
     * Solicita o nome do usuário e o adiciona ao banco de dados.
     */
    private void addUser() {
        System.out.print("\n---- ADICIONAR USUARIO ----\n");
        System.out.print("Digite o nome do usuario: ");
        String name = scanner.nextLine();

        try {
            User user = new User(name);
            UserDAO.addUser(user);
            System.out.println("\nUsuario adicionado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar usuario: " + e.getMessage());
        }
    }

    /**
     * Lista todos os usuários cadastrados no sistema.
     * Exibe o ID, nome e se o usuário possui empréstimos ativos.
     */
    private void listUsers() {
        System.out.println("\n---- LISTA DE USUARIOS ----\n");
        System.out.printf("%-3s | %-40s | %-10s%n", "ID", "NOME", "EMPRESTIMO");
        
        try {
            List<User> users = UserDAO.getAllUsers();
            for (User user : users) {
                System.out.printf("%-3s | %-40s | %-10s%n", user.getId(), user.getName(), (user.hasLoan() ? "Sim" : "Nao"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar usuarios: " + e.getMessage());
        }
    }

    /**
     * Exclui um usuário do sistema.
     * Solicita o ID do usuário a ser excluído e verifica se o usuário possui empréstimos ativos antes de realizar a exclusão.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados durante a exclusão.
     */
    private void deleteUser() throws SQLException {
        System.out.println("\n---- EXCLUIR USUARIO ----\n");
        System.out.printf("%-3s | %-20s | %-10s%n\n", "ID", "NOME", "TEM EMPRESTIMO");

        UserDAO.getAllUsers().forEach(user -> 
        System.out.printf("%-3s | %-20s | %-10s%n", user.getId(), user.getName(), (user.hasLoan() ? "Sim" : "Não")));
    
        System.out.print("\nSelecione o ID do usuario: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        try {
            User user = UserDAO.getUserById(id);
            if (user != null && user.hasLoan()) {
                System.out.println("Nao e possivel excluir usuarios com emprestimos ativos!");
                return;
            }
            
            boolean deleted = UserDAO.deleteUser(id);
            if (deleted) {
                System.out.println("Usuario excluido com sucesso!");
            } else {
                System.out.println("Erro ao excluir usuário!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir usuario: " + e.getMessage());
        }
    }   

    /**
     * Gerencia as operações relacionadas a livros, como adicionar, listar e excluir.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados durante as operações com livros.
     */
    private void manageBooks() throws SQLException {
        while (true) {
            System.out.print("\n======= GERENCIAR LIVROS =======\n");
            System.out.println("1. Adicionar Livro");
            System.out.println("2. Listar Livros");
            System.out.println("3. Excluir Livro");
            System.out.println("4. Voltar");
            System.out.println("=================================");

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
                    System.out.println("Opcao invalida!");
            }
        }
    }

    /**
     * Adiciona um novo livro ao sistema.
     * Solicita o título, ID do autor e gênero do livro ao usuário e o adiciona ao banco de dados.
     * Atualiza a contagem de livros do autor.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados durante a adição do livro.
     */
    private void addBook() throws SQLException {
        System.out.print("\n---- ADICIONAR LIVRO ----\n");
        
        System.out.print("Digite o titulo do livro: ");
        String title = scanner.nextLine();
         
        System.out.printf("\n%-3s | %-20s%n", "ID", "NOME");
        AuthorDAO.getAllAuthors().forEach(author ->
            System.out.printf("%-3s | %-20s%n", author.getId(), author.getName()));

        System.out.print("\nDigite o ID do autor: ");
        int authorId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o genero: ");
        String genre = scanner.nextLine();

        try {
            Author author = AuthorDAO.getAuthorById(authorId);
            if (author == null) {
                System.out.println("Autor nao encontrado!");
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

    /**
     * Lista todos os livros cadastrados no sistema.
     * Exibe o ID, título, autor e disponibilidade de cada livro.
     */
    private void listBooks() {
        System.out.println("\n---- LISTA DE LIVROS ----\n");
        System.out.printf("%-3s | %-40s | %-30s | %-15s | %-10s%n", "ID", "TITULO", "AUTOR", "GENERO", "DISPONIVEL");
        
        try {
            List<Book> books = BookDAO.getAllBooks();
            for (Book book : books) {
                System.out.printf("%-3d | %-40s | %-30s | %-15s | %-10s%n", book.getId(), book.getTitle(), book.getAuthor().getName(), book.getGenre(), (book.isAvailable() ? "Sim" : "Não"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar livros: " + e.getMessage());
        }
    }

    /**
     * Exclui um livro do sistema.
     * Solicita o ID do livro a ser excluído e verifica se o livro está emprestado antes de realizar a exclusão.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados durante a exclusão.
     */
    private void deleteBook() throws SQLException {
        System.out.println("\n---- EXCLUIR LIVRO ----\n");
        System.out.printf("%-3s | %-40s%n", "ID", "TITULO");

        BookDAO.getAllBooks().forEach(book ->
            System.out.printf("%-3d | %-40s%n", book.getId(), book.getTitle()));

        System.out.print("\nSelecione o ID do livro: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        try {
            Book book = BookDAO.getBookById(id);
            if (book != null && !book.isAvailable()) {
                System.out.println("Nao e possivel excluir livros emprestados!");
                return;
            }
            
            BookDAO.deleteBook(id);
            System.out.println("Livro excluido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir livro: " + e.getMessage());
        }
    }

    /**
     * Gerencia as operações relacionadas a empréstimos, como realizar empréstimos, devolver livros e listar empréstimos ativos.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados durante as operações com empréstimos.
     */
    private void manageLoans() throws SQLException {
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

    /**
     * Realiza um empréstimo de livro.
     * Solicita o ID do usuário e o ID do livro, verifica a disponibilidade e o status do usuário, e registra o empréstimo.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados durante a realização do empréstimo.
     */
    private void makeLoan() throws SQLException {
        System.out.println("\n==== REALIZAR EMPRESTIMO ====\n");
        UserDAO.getAllUsers().forEach(user ->
            System.out.printf("%2d | %s%n", user.getId(), user.getName()));
    
        System.out.print("\nDigite o ID do usuario: ");
        int userId = scanner.nextInt();

        System.out.println("\nID | TITULO");
        BookDAO.getAllBooks().forEach(book ->
            System.out.printf("%2d | %s%n", book.getId(), book.getTitle()));

        System.out.print("\nDigite o ID do livro: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        
        try {
            User user = UserDAO.getUserById(userId);
            Book book = BookDAO.getBookById(bookId);
            
            if (user == null || book == null) {
                System.out.println("\nUsuario ou livro nao encontrado!");
                return;
            }
            
            if (user.hasLoan()) {
                System.out.println("\nUsuario ja possui emprestimo ativo!");
                return;
            }
            
            if (!book.isAvailable()) {
                System.out.println("\nLivro indisponivel para emprestimo!");
                return;
            }
            
            Loan loan = new Loan(book, user);
            LoanDAO.addLoan(loan);
            System.out.println("\nEmprestimo realizado com sucesso!");

        } catch (SQLException e) {
            System.out.println("\nErro ao realizar emprestimo: " + e.getMessage());
        }
    }

    /**
     * Realiza a devolução de um livro.
     * Lista os empréstimos ativos, solicita o ID do empréstimo a ser devolvido e atualiza o status do livro e do usuário.
     */
    private void returnBook() {
        System.out.println("\n==== DEVOLVER LIVRO ====\n");
        
        try {
            List<Loan> activeLoans = LoanDAO.getActiveLoans();
        
            if (activeLoans.isEmpty()) {
            System.out.println("Nao ha emprestimos ativos no momento.");
            return;
        }
        
            System.out.println("\nEMPRESTIMOS ATIVOS:\n");
            System.out.printf("%-4s | %-25s | %-40s | %-15s%n", "ID", "USUARIO", "LIVRO", "DATA EMPRESTIMO");
        
            for (Loan loan : activeLoans) {
                System.out.printf("%-4d | %-25s | %-40s | %-15s%n", loan.getId(), loan.getUser().getName(), loan.getBook().getTitle(), loan.getLoanDate());
        }
        
            System.out.print("\nDigite o ID do emprestimo para devolucao: ");
            int loanId = scanner.nextInt();
            scanner.nextLine(); 
        
            boolean success = LoanDAO.returnLoan(loanId);
        
            if (success) {
                Loan loan = activeLoans.stream().filter(l -> l.getId() == loanId).findFirst().orElse(null);
            
                if (loan != null) {
                    BookDAO.updateBookAvailability(loan.getBook().getId(), true);
                    UserDAO.updateUserLoanStatus(loan.getUser().getId(), false);
            }
            
            System.out.println("\nLivro devolvido com sucesso!");
        }
        
          else {
            System.out.println("\nNao foi possivel devolver o livro. Verifique o ID do emprestimo.");
        }
        
    } catch (SQLException e) {
        System.out.println("\nErro ao processar devolucao: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("\nErro inesperado: " + e.getMessage());
    }
}

    /**
     * Lista todos os empréstimos ativos no sistema.
     * Exibe o ID, usuário, livro e data do empréstimo para cada empréstimo ativo.
     */
    private void listActiveLoans() {
        System.out.println("\n---- LISTA DE EMPRESTIMOS ATIVOS ----\n");
        
        try {
            List<Loan> activeLoans = LoanDAO.getActiveLoans();

            if (activeLoans.isEmpty()){
                System.out.println("Nenhum emprestimo ativo no momento!");
                return;
            }

            System.out.printf("%-4s | %-25s | %-40s | %-15s%n", "ID", "USUARIO", "LIVRO", "DATA EMPRESTIMO");

            for (Loan loan : activeLoans)  {
                System.out.printf("%-4d | %-25s | %-40s | %-15s%n", loan.getId(), loan.getUser().getName(), loan.getBook().getTitle(), loan.getLoanDate());
            }
        }   catch (SQLException e) {
            System.out.println("Erro ao listar emprestimos ativos: " + e.getMessage());
        }
    }

    /**
     * Visualiza todos os dados do sistema, incluindo autores, usuários, livros e empréstimos ativos.
     * Chama os métodos de listagem para cada entidade.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados durante a visualização dos dados.
     */
    private void viewAllData() {
        System.out.println("\n======= VISUALIZAR TODOS OS DADOS =======");
        listAuthors();
        listBooks();
        listUsers();
        listActiveLoans();
        System.out.println("\n=================================");
    }
}