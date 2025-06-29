package librarybooks.model;

/**
 * Representa um autor.
 * Contém informações como ID, nome, gênero principal e o número de livros na coleção.
 */
public class Author {
    private int id;
    private String name;
    private String mainGenre;
    private int booksInCollection;
    
    /**
     * Construtor para criar uma nova instância de Author.
     * O número de livros na coleção é inicializado como 0.
     * @param name O nome do autor.
     * @param mainGenre O gênero principal de escrita do autor.
     */
    public Author(String name, String mainGenre) {
        this.name = name;  
        this.mainGenre = mainGenre;
        this.booksInCollection = 0;
    }

    /**
     * Retorna o ID do autor.
     * @return O ID do autor.
     */
    public int getId() { 
        return id; 
    }

    /**
     * Define o ID do autor.
     * @param id O ID a ser definido para o autor.
     */
    public void setId(int id) {
        this.id = id; 
    }

    /**
     * Retorna o nome do autor.
     * @return O nome do autor.
     */
    public String getName() { 
        return name; 
    }

    /**
     * Define o nome do autor.
     * @param name O nome a ser definido para o autor.
     */
    public void setName(String name) {
        this.name = name; 
    }

    /**
     * Retorna o gênero principal do autor.
     * @return O gênero principal do autor.
     */
    public String getMainGenre() {
        return mainGenre; 
    }

    /**
     * Define o gênero principal do autor.
     * @param mainGenre O gênero principal a ser definido para o autor.
     */
    public void setMainGenre(String mainGenre) {
        this.mainGenre = mainGenre; 
    }
    
    /**
     * Retorna o número de livros do autor na coleção.
     * @return O número de livros na coleção.
     */
    public int getBooksInCollection() {
        return booksInCollection; 
    }

    /**
     * Define o número de livros do autor na coleção.
     * @param booksInCollection O número de livros a ser definido.
     */
    public void setBooksInCollection(int booksInCollection) { 
        this.booksInCollection = booksInCollection; 
    }
    
    /**
     * Incrementa a quantidade de livros na coleção do autor.
     */
    public void incrementBooksCount() { 
        this.booksInCollection++; 
    }
    
    /**
     * Retorna uma representação em String do objeto Author.
     * @return Uma String formatada com o nome, gênero principal e número de livros do autor.
     */
    @Override
    public String toString() {
        return name + " | Gênero: " + mainGenre + " | Livros: " + booksInCollection;
    }
}

