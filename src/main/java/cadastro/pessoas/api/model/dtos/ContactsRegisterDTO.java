package cadastro.pessoas.api.model.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record ContactsRegisterDTO(
        @NotBlank
        String name,
        @NotBlank
        String phone,
        @NotBlank
        @Email
        String email
) {
}
