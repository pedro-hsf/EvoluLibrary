package librarybooks.model;

public class Book {
    private int id;
    private String title;
    private Author author;
    private boolean available;
    
    public Book(String title, Author author) {
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return title + " do(a) " + author.getName() + (available ? " Disponível!" : " Em Empréstimo!");
    }

}