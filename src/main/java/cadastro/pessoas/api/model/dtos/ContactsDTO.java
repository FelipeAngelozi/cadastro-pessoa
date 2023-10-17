package cadastro.pessoas.api.model.dtos;

import cadastro.pessoas.api.model.Contacts;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

public record ContactsDTO(
    @NotNull
    BigInteger id,
    @NotBlank
    String name,
    @NotBlank
    String phone,
    @NotBlank
    @Email
    String email
) {

    public ContactsDTO(Contacts contacts) {
        this(contacts.getId(), contacts.getName(), contacts.getPhone(), contacts.getEmail());
    }
}
