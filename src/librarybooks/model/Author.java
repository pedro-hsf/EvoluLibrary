package librarybooks.model;

public class Author {
    private int id;
    private String name;
    private String mainGenre;
    private int booksInCollection;
    
    public Author(String name, String mainGenre) {
        this.name = name;  
        this.mainGenre = mainGenre;
        this.booksInCollection = 0;
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

    public String getMainGenre() {
        return mainGenre; 
    }

    public void setMainGenre(String mainGenre) {
        this.mainGenre = mainGenre; 
    }
    
    public int getBooksInCollection() {
        return booksInCollection; 
    }

    public void setBooksInCollection(int booksInCollection) { 
        this.booksInCollection = booksInCollection; 
    }
    
    public void incrementBooksCount() { 
        this.booksInCollection++; 
    }
    
    @Override
    public String toString() {
        return name + " | Gênero: " + mainGenre + " | Livros: " + booksInCollection;
    }
}