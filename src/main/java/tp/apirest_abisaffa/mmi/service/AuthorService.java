package tp.apirest_abisaffa.mmi.service;

import org.springframework.stereotype.Service;
import tp.apirest_abisaffa.mmi.model.Author;
import tp.apirest_abisaffa.mmi.repository.AuthorRepository;
import tp.apirest_abisaffa.mmi.dto.AuthorUpdateDto;
import tp.apirest_abisaffa.mmi.exception.ResourceNotFoundException; //import exception

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository aRepo;

    public AuthorService(AuthorRepository authorRepo) {
        this.aRepo = authorRepo;
    }

    //GET /authors : liste des auteurs
    public List<Author> getAllAuthors() {
        return aRepo.findAll();
    }

    //GET /authors/{id} : auteur par id
    public Author getAuthorById(Long id) {
        return aRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auteur non trouvé : " + id));
    }

    //POST /authors : ajouter un auteur
    public Author createAuthor(Author author) {
        return aRepo.save(author);
    }

    //PUT /authors/{id} : modifier un auteur
    public Author updateAuthor(Long id, AuthorUpdateDto updateDto) {
        //recherche l'auteur et verif son existance
        Author existingAuthor = aRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auteur non trouvé avec l'ID : " + id));

        //mets à jour tous les champs a partir du dto
        existingAuthor.setFirstName(updateDto.getFirstName());
        existingAuthor.setLastName(updateDto.getLastName());
        existingAuthor.setBirthYear(updateDto.getBirthYear());

        //sauvegarde
        return aRepo.save(existingAuthor);
    }

    //DELETE /authors/{id} : supprimer un auteur pour author
    public void deleteAuthor(Long id) {
        aRepo.deleteById(id);
    }
}
