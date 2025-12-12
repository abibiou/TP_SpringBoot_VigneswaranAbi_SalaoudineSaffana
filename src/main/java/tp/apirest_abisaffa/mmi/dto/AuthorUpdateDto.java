package tp.apirest_abisaffa.mmi.dto;

import jakarta.validation.constraints.*; //importe annotations de validation

public class AuthorUpdateDto {
    //validation des champs pour PUT
    @NotBlank(message = "Le prénom ne peut pas être vide.")
    private String firstName;

    @NotBlank(message = "Le nom ne peut pas être vide.")
    private String lastName;

    @Min(value = 0, message = "L'année de naissance ne peut pas être négative.")
    @Max(value = 2025, message = "L'année ne peut pas être future.")
    @NotNull(message = "L'année de naissance est obligatoire.")
    private Integer birthYear;

    //getters et setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Integer getBirthYear() { return birthYear; }
    public void setBirthYear(Integer birthYear) { this.birthYear = birthYear; }
}
