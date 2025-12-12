package tp.apirest_abisaffa.mmi.dto;

import tp.apirest_abisaffa.mmi.model.Book.Category;

public class BookCountByCategoryDto {
    private Category category;
    private Long count;

    //constructeur pour que JPA puisse mapper les résultats de la requête GROUP BY (categorie)
    public BookCountByCategoryDto(Category category, Long count) {
        this.category = category;
        this.count = count;
    }
}
