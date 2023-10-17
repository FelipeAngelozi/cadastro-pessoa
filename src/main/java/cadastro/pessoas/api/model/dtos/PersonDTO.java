package cadastro.pessoas.api.model.dtos;

import cadastro.pessoas.api.model.Person;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public record PersonDTO(
    @NotNull
    BigInteger id,
    @NotBlank
    String name,
    @NotBlank
    @CPF
    String cpf,
    @NotNull
    @Past
    LocalDate birthdate,
    @NotEmpty
    @Valid
    List<ContactsDTO> contactsList
) {

    public PersonDTO(Person person) {
        this(person.getId(), person.getName(), person.getCpf(), person.getBirthdate(), person.getContactsList().stream().map(ContactsDTO::new).toList());
    }
}
