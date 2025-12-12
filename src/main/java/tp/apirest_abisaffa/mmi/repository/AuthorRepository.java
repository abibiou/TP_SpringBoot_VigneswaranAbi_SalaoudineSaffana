package tp.apirest_abisaffa.mmi.repository;

import org.springframework.data.jpa.repository.JpaRepository; //classe mère Spring Data
import tp.apirest_abisaffa.mmi.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    //aucune méthode personnalisée nécessaire car JpaRepository fournit :
    // - findAll(), findById(Long id), save(Author author), deleteById(Long id)
}
