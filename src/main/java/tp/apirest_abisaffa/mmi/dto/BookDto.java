package tp.apirest_abisaffa.mmi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tp.apirest_abisaffa.mmi.model.Book;

public class BookDto {
    @NotBlank(message = "Le titre ne peut pas être vide.")
    private String title;

    //isbn sans validation car le Service gère son unicité (via bRepo.findByIsbn)
    private String isbn;

    @Min(value = 1450, message = "L'année doit être au moins 1450.")
    @Max(value = 2025, message = "L'année ne peut pas être future.")
    @NotNull(message = "L'année est obligatoire.")
    private Integer year;

    @NotNull(message = "La catégorie est obligatoire.")
    private Book.Category category;

    //optionnel ; permet de changer l'id de l'auteur du livre
    private Long authorId;

    //getters et setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Book.Category getCategory() { return category; }
    public void setCategory(Book.Category category) { this.category = category; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }

    public Book toEntity() {
        Book book = new Book();
        book.setTitle(this.title);
        book.setIsbn(this.isbn);
        book.setYear(this.year);
        book.setCategory(this.category);

        return book;
    }
}
