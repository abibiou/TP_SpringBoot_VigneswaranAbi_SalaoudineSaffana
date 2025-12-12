package tp.apirest_abisaffa.mmi.service;

import org.springframework.stereotype.Service;
import tp.apirest_abisaffa.mmi.dto.BookDto;
import tp.apirest_abisaffa.mmi.model.Author;
import tp.apirest_abisaffa.mmi.model.Book;
import tp.apirest_abisaffa.mmi.repository.AuthorRepository;
import tp.apirest_abisaffa.mmi.repository.BookRepository;
import tp.apirest_abisaffa.mmi.model.Book.Category;

import tp.apirest_abisaffa.mmi.dto.BookCountByCategoryDto;
import tp.apirest_abisaffa.mmi.dto.TopAuthorDto;

import org.springframework.data.jpa.domain.Specification; //classe qui créé la requête where de manière dynamique
import org.springframework.data.domain.Page; //requête de pagination
import org.springframework.data.domain.Pageable; //permet de gérer page et les métadonnées de pagination

import tp.apirest_abisaffa.mmi.exception.DuplicateIsbnException;
import tp.apirest_abisaffa.mmi.exception.ResourceNotFoundException;

import java.util.*;

@Service
public class BookService {
    private final BookRepository bRepo;
    private final AuthorRepository aRepo;

    public BookService(BookRepository bookRepo, AuthorRepository authorRepo) {
        this.bRepo = bookRepo;
        this.aRepo = authorRepo;
    }

    //GET /books : liste paginée des livres avec filtres : title, authorId, category, yearFrom, yearTo, tri possible (sort=year,desc)
    //méthode de service qui prend tous les filtres optionnels, utilise Spring Data JPA Specification pour construire dynamiquement
    public Page<Book> getAllBooks(
            String title,
            Long authorId,
            Category category,
            Integer yearFrom,
            Integer yearTo,
            Pageable pageable //gère page, size et sort=year,desc
    ) {
        //requête dynamique.
        //initialise une specification "vide" toujours vraie (WHERE 1=1)
        Specification<Book> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        //ajouts clauses WHERE seulement si les paramètres sont non nuls

        //filtre par titre
        if (title != null && !title.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }

        //filtre par id auteur
        if (authorId != null) {
            // Note: 'author' est le nom de l'attribut dans l'entité Book
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("author").get("id"), authorId));
        }

        //filtre par catégorie
        if (category != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("category"), category));
        }

        //filtre par année (plage : from et to)
        if (yearFrom != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("year"), yearFrom));
        }
        if (yearTo != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("year"), yearTo));
        }

        //exécuter la requête
        return bRepo.findAll(spec, pageable);
    }

    //GET /books/{id} : détail d’un livre
    public Book getBookById(Long id) {
        return bRepo.findById(id)
                //erreur 404
                .orElseThrow(() -> new ResourceNotFoundException("Livre non trouvé avec l'ID : " + id));
    }

    //POST /books : créer un livre (validation obligatoire)
    public Book createBook(Book book, Long authorId) {
        //verif unicité isbn
        if (bRepo.findByIsbn(book.getIsbn()).isPresent()) {
            throw new DuplicateIsbnException("L'ISBN " + book.getIsbn() + " existe déjà.");
        }
        //verif existence auteur
        Author author = aRepo.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Auteur non trouvé avec l'ID : " + authorId));

        book.setAuthor(author);

        return bRepo.save(book);
    }

    //PUT /books/{id} : modifier un livre
    public Book updateBook(Long id, BookDto updateDto) {
        //trouve le livre existant sinon erreur
        Book existingBook = bRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livre non trouvé avec l'ID : " + id));

        //mets à jour l'isbn avec vérification d'unicité
        if (updateDto.getIsbn() != null && !updateDto.getIsbn().isEmpty()) {
            String newIsbn = updateDto.getIsbn();
            Optional<Book> bookWithSameIsbn = bRepo.findByIsbn(newIsbn);

            //conflit si l'isbn existe deja et n'est pas celui du livre que l'on modifie
            if (bookWithSameIsbn.isPresent() && !bookWithSameIsbn.get().getId().equals(id)) {
                throw new DuplicateIsbnException("L'ISBN " + newIsbn + " existe déjà chez un autre livre.");
            }
            existingBook.setIsbn(newIsbn);
        }

        //mets à jour les autres champs
        existingBook.setTitle(updateDto.getTitle());
        existingBook.setYear(updateDto.getYear());
        existingBook.setCategory(updateDto.getCategory());

        //mets à jour l'auteur si l'id est fourni
        if (updateDto.getAuthorId() != null) {
            Author newAuthor = aRepo.findById(updateDto.getAuthorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Nouvel auteur non trouvé avec l'ID : " + updateDto.getAuthorId()));
            existingBook.setAuthor(newAuthor);
        }

        //sauvegarde
        return bRepo.save(existingBook);
    }

    //DELETE /books/{id} : supprimer un livre
    public void deleteBook(Long id) {
        bRepo.deleteById(id);
    }

    // GET /stats/books-per-category
    public List<BookCountByCategoryDto> countBooksByCategory() {
        return bRepo.countBooksByCategory();
    }

    // GET /stats/top-authors?limit=X
    public List<TopAuthorDto> findTopAuthors(int limit) {
        if (limit <= 0) {
            //gére le cas où la limite est non valide, retourne une liste vide
            return new ArrayList<>();
        }
        return bRepo.findTopAuthorsByBookCount(limit);
    }
}
