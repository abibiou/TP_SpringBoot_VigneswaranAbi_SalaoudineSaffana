package tp.apirest_abisaffa.mmi.model;
import jakarta.persistence.*;

@Entity
@Table(name = "book")

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(nullable = false, unique = true)
    private String isbn;

    private int year;

    @Enumerated(EnumType.STRING)
    private Category category;
    public static enum Category {
        NOVEL,
        ESSAY,
        POETRY,
        OTHER
    }

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author; //lien

    public Book() {
        //constructeur vide jpa
    }

    public Book(String title, String isbn, int year, Category category, Author author) {
        this.title = title;
        this.isbn = isbn;
        this.year = year;
        this.category = category;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
