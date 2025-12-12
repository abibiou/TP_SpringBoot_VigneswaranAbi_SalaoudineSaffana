package tp.apirest_abisaffa.mmi.controller;

import org.springframework.web.bind.annotation.*;
import tp.apirest_abisaffa.mmi.dto.BookDto;
import tp.apirest_abisaffa.mmi.model.Book;
import tp.apirest_abisaffa.mmi.model.Book.Category;
import tp.apirest_abisaffa.mmi.service.BookService;
import jakarta.validation.Valid;

import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bService;

    public BookController(BookService service) {
        this.bService = service;
    }

    //GET /books : liste avec filtres
    //Ex: /books?title=misérables&authorId=1&sort=year,desc&page=0&size=10
    @GetMapping
    public Page<Book> getAllBooks(
            //api reçoit les filtres
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Integer yearFrom,
            @RequestParam(required = false) Integer yearTo,

            //Spring gère page et size et sort
            @PageableDefault(sort = {"id"}, size = 20) Pageable pageable
    ) {
        //le Controller transmet tout a Service
        return bService.getAllBooks(title, authorId, category, yearFrom, yearTo, pageable);
    }

    //GET /books/{id} : détail du livre
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bService.getBookById(id);
    }

    // POST /books : créer un livre
    @PostMapping
    public Book createBook(
            @Valid @RequestBody BookDto bookDto
    ) {
        //converti en entité
        Book newBook = bookDto.toEntity();

        return bService.createBook(newBook, bookDto.getAuthorId());
    }

    // PUT /books/{id} : modifier un livre
    @PutMapping("/{id}")
    public Book updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookDto updateDto
    ) {
        return bService.updateBook(id, updateDto);
    }

    // DELETE /books/{id}
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bService.deleteBook(id);
    }
}
