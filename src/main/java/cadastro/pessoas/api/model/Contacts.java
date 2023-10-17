package cadastro.pessoas.api.model;

import cadastro.pessoas.api.model.dtos.ContactsDTO;
import cadastro.pessoas.api.model.dtos.ContactsRegisterDTO;
import cadastro.pessoas.api.model.dtos.PersonDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.List;


@Entity
@Table(name = "contacts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Contacts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;

    public Contacts(ContactsRegisterDTO contactsRegisterDTO) {
        this.name = contactsRegisterDTO.name();
        this.phone = contactsRegisterDTO.phone();
        this.email = contactsRegisterDTO.email();
    }
    public void updateFields(ContactsDTO contactsDTO) {
        this.name = contactsDTO.name();
        this.phone = contactsDTO.phone();
        this.email = contactsDTO.email();
    }

}
