package librarybooks.model;

public class User {

    private int id;
    private String name;
    private boolean hasLoan;

    public User(String name) {
        this.name = name; //talvez de algum problema nessa parte
        this.hasLoan = false;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean hasLoan() {
        return hasLoan;
    }

    public void setHasLoan(boolean hasLoan) {
        this.hasLoan = hasLoan;
    }

    @Override
    public String toString() {
        return super.toString() + (hasLoan ? " (Com Empréstimo)" : "Sem Empréstimos");
    }
}
