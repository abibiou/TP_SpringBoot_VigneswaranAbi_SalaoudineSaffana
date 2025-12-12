package tp.apirest_abisaffa.mmi.repository;

import java.util.*; //importe Optional et List
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor; //interface pour filtres dynamiques
import tp.apirest_abisaffa.mmi.model.Book;
import tp.apirest_abisaffa.mmi.dto.BookCountByCategoryDto;
import org.springframework.data.jpa.repository.Query; //annotation pour requêtes personnalisées (JPQL)
import tp.apirest_abisaffa.mmi.dto.TopAuthorDto;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    // Héritage multiple : JpaRepository et JpaSpecificationExecutor

    //verif l'existence d'un livre via isbn
    Optional<Book> findByIsbn(String isbn);

    //JPQL pour regrouper et compter par catégorie.
    //l'instruction "SELECT new ..." appelle le constructeur du DTO.
    @Query("SELECT new tp.apirest_abisaffa.mmi.dto.BookCountByCategoryDto(b.category, COUNT(b)) " +
            "FROM Book b GROUP BY b.category ORDER BY COUNT(b) DESC")
    List<BookCountByCategoryDto> countBooksByCategory();

    //JPQL pour trouver les auteurs avec le plus de livres (Top Authors)
    @Query(value = "SELECT new tp.apirest_abisaffa.mmi.dto.TopAuthorDto(b.author.id, b.author.firstName, b.author.lastName, COUNT(b)) " +
            "FROM Book b " +
            "GROUP BY b.author " +
            "ORDER BY COUNT(b) DESC " +
            "LIMIT :limit") //utilisation d'un paramètre nommé pour limit (passe la valeur demandée par l'utilisateur)
    List<TopAuthorDto> findTopAuthorsByBookCount(int limit);
}
