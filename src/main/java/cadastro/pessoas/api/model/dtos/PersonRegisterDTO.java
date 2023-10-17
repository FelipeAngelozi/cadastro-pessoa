package cadastro.pessoas.api.model.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public record PersonRegisterDTO(
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
        List<ContactsRegisterDTO> contactsList
) {
}
