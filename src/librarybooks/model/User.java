package librarybooks.model;

/**
 * Representa um usuário.
 * Contém informações como ID, nome e se o usuário possui um empréstimo ativo.
 */
public class User {

    private int id;
    private String name;
    private boolean hasLoan;

    /**
     * Construtor para criar uma nova instância de User.
     * O status de empréstimo é inicializado como falso.
     * @param name O nome do usuário.
     */
    public User(String name) {
        this.name = name;
        this.hasLoan = false;
    }
    
    /**
     * Retorna o ID do usuário.
     * @return O ID do usuário.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID do usuário.
     * @param id O ID a ser definido para o usuário.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o nome do usuário.
     * @return O nome do usuário.
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do usuário.
     * @param name O nome a ser definido para o usuário.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Verifica se o usuário possui um empréstimo ativo.
     * @return true se o usuário tiver um empréstimo, false caso contrário.
     */
    public boolean hasLoan() {
        return hasLoan;
    }

    /**
     * Define o status de empréstimo do usuário.
     * @param hasLoan true se o usuário tiver um empréstimo, false caso contrário.
     */
    public void setHasLoan(boolean hasLoan) {
        this.hasLoan = hasLoan;
    }

    /**
     * Retorna uma representação em String do objeto User.
     * @return Uma String formatada com o status de empréstimo do usuário.
     */
    @Override
    public String toString() {
        return super.toString() + (hasLoan ? " (Com Empréstimo)" : "Sem Empréstimos");
    }
}

