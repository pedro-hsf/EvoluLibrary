package librarybooks.model;

/**
 * Representa um livro.
 * Contém informações como ID, título, autor e status de disponibilidade.
 */
public class Book {
    private int id;
    private String title;
    private Author author;
    private String genre;
    private boolean available;
    
    /**
     * Construtor para criar uma nova instância de Book.
     * O livro é inicializado como disponível.
     * @param title O título do livro.
     * @param author O objeto Author do autor do livro.
     * @param genre O gênero do livro.
     */
    public Book(String title, Author author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.available = true;
    }

    /**
     * Retorna o ID do livro.
     * @return O ID do livro.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID do livro.
     * @param id O ID a ser definido para o livro.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o título do livro.
     * @return O título do livro.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Define o título do livro.
     * @param title O título a ser definido para o livro.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retorna o autor do livro.
     * @return O objeto Author do autor do livro.
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Define o autor do livro.
     * @param author O objeto Author a ser definido para o autor do livro.
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * Retorna o gênero do livro.
     * @return O gênero do livro.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Define o gênero do livro.
     * @param genre O gênero a ser definido para o livro.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Verifica se o livro está disponível.
     * @return true se o livro estiver disponível, false caso contrário.
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Define o status de disponibilidade do livro.
     * @param available true para disponível, false para em empréstimo.
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Retorna uma representação em String do objeto Book.
     * @return Uma String formatada com o título, autor e status de disponibilidade do livro.
     */
    @Override
    public String toString() {
        return title + " do(a) " + author.getName() + (available ? " Disponível!" : " Em Empréstimo!");
    }
}

