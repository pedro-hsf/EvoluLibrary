package librarybooks.model;

import java.time.LocalDate;

/**
 * Representa um empréstimo de livro.
 * Contém informações sobre o livro emprestado, o usuário que o emprestou, a data do empréstimo e a data de devolução.
 */
public class Loan {
    private int id;
    private Book book;
    private User user;
    private LocalDate loanDate;
    private LocalDate returnDate;
    
    /**
     * Construtor para criar uma nova instância de Loan.
     * A data do empréstimo é definida como a data atual.
     * A data de retorno do empréstimo está definida como null pois será alterada posteriormente.
     * @param book O objeto Book que está sendo emprestado.
     * @param user O objeto User que está realizando o empréstimo.
     */
    public Loan(Book book, User user) {
        this.book = book;
        this.user = user;
        this.loanDate = LocalDate.now();
        this.returnDate = null;
    }
    
    /**
     * Retorna o ID do empréstimo.
     * @return O ID do empréstimo.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID do empréstimo.
     * @param id O ID a ser definido para o empréstimo.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o livro associado ao empréstimo.
     * @return O objeto Book associado.
     */
    public Book getBook() {
        return book;
    }

    /**
     * Define o livro associado ao empréstimo.
     * @param book O objeto Book a ser associado.
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Retorna o usuário associado ao empréstimo.
     * @return O objeto User associado.
     */
    public User getUser() {
        return user;
    }

    /**
     * Define o usuário associado ao empréstimo.
     * @param user O objeto User a ser associado.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Retorna a data em que o empréstimo foi realizado.
     * @return A data do empréstimo.
     */
    public LocalDate getLoanDate() {
        return loanDate;
    }

    /**
     * Define a data em que o empréstimo foi realizado.
     * @param loanDate A data do empréstimo a ser definida.
     */
    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    /**
     * Retorna a data em que o livro foi devolvido.
     * @return A data de devolução, ou null se o livro ainda não foi devolvido.
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Define a data em que o livro foi devolvido.
     * @param returnDate A data de devolução a ser definida.
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

}

