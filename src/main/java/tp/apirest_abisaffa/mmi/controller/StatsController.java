package tp.apirest_abisaffa.mmi.controller;

import org.springframework.web.bind.annotation.*;
import tp.apirest_abisaffa.mmi.dto.BookCountByCategoryDto; //DTO pour les livres par catégorie
import tp.apirest_abisaffa.mmi.dto.TopAuthorDto; //DTO pour les meilleurs auteurs
import tp.apirest_abisaffa.mmi.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatsController {
    private final BookService bService;

    public StatsController(BookService bService) { //chemin pour toutes les statistiques
        this.bService = bService;
    }

    //GET /stats/books-per-category
    @GetMapping("/books-per-category")
    public List<BookCountByCategoryDto> getBooksCountByCategory() {
        return bService.countBooksByCategory();
    }

    //GET /stats/top-authors?limit=3
    @GetMapping("/top-authors")
    public List<TopAuthorDto> getTopAuthors(
            @RequestParam(defaultValue = "3") int limit) {
        return bService.findTopAuthors(limit);
    }
}
