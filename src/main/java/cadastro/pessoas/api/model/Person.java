package cadastro.pessoas.api.model;

import cadastro.pessoas.api.model.dtos.PersonDTO;
import cadastro.pessoas.api.model.dtos.PersonRegisterDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    @Column(name = "name")
    private String name;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "birthdate")
    private LocalDate birthdate;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "person_id")
    private List<Contacts> contactsList;

    public Person(PersonRegisterDTO personRegisterDTO) {
        this.name = personRegisterDTO.name();
        this.cpf = personRegisterDTO.cpf();
        this.birthdate = personRegisterDTO.birthdate();
        this.contactsList = personRegisterDTO.contactsList().stream().map(Contacts::new).toList();
    }

    public void updateFields(PersonDTO personDTO) {
        this.name = personDTO.name();
        this.cpf = personDTO.cpf();
        this.birthdate = personDTO.birthdate();

    }

    public void setContacts(List<Contacts> contacts) {
        this.contactsList.clear();
        this.getContactsList().addAll(contacts);
    }
}
