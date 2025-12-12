package tp.apirest_abisaffa.mmi.dto;

public class TopAuthorDto {
    private Long authorId;
    private String authorName;
    private Long bookCount;

    //constructeur pour le mappage de la requête JPQL
    public TopAuthorDto(Long authorId, String firstName, String lastName, Long bookCount) {
        this.authorId = authorId;
        this.authorName = firstName + " " + lastName;
        this.bookCount = bookCount;
    }
}
