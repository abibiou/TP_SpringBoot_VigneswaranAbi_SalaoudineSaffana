package tp.apirest_abisaffa.mmi.controller;

import org.springframework.web.bind.annotation.*;
import tp.apirest_abisaffa.mmi.model.Author;
import tp.apirest_abisaffa.mmi.service.AuthorService;
import tp.apirest_abisaffa.mmi.dto.AuthorUpdateDto; //pour le PUT
import jakarta.validation.Valid;

import java.util.List;

@RestController //contrôleur REST
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService aService;

    //injection de dépendances
    public AuthorController(AuthorService service) {
        this.aService = service;
    }

    // GET /authors : récupère la liste de tous les auteurs ; accès en lecture public
    @GetMapping
    public List<Author> getAllAuthors() {
        return aService.getAllAuthors();
    }

    // GET /authors/{id} : récupère les détails d'un auteur par son id
    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id) {
        return aService.getAuthorById(id);
    }

    // POST /authors : crée un nouvel auteur
    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        return aService.createAuthor(author);
    }

    // PUT /authors/{id} : modifie un auteur (validation requise)
    @PutMapping("/{id}")
    public Author updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody AuthorUpdateDto updateDto
    ) {
        return aService.updateAuthor(id, updateDto);
    }

    // DELETE /authors/{id} : supprime un auteur par son ID
    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) { //void pour une réponse HTTP 204
        aService.deleteAuthor(id);
    }
}
