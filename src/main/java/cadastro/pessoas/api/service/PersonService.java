package cadastro.pessoas.api.service;

import cadastro.pessoas.api.model.Contacts;
import cadastro.pessoas.api.model.Person;
import cadastro.pessoas.api.model.dtos.PersonDTO;
import cadastro.pessoas.api.model.dtos.PersonRegisterDTO;
import cadastro.pessoas.api.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Objects;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final ContactsService contactsService;

    @Autowired
    public PersonService(PersonRepository personRepository, ContactsService contactsService) {
        this.personRepository = personRepository;
        this.contactsService = contactsService;
    }

    @Transactional
    public PersonDTO save(PersonRegisterDTO personRegisterDTO) {
        return new PersonDTO(this.personRepository.save(new Person(personRegisterDTO)));
    }

    @Transactional(readOnly = true)
    public Page<PersonDTO> getAll(Pageable pageable) {
        return this.personRepository.findAll(pageable).map(PersonDTO::new);
    }

    @Transactional(readOnly = true)
    public PersonDTO getById(BigInteger id) {
        Person person = this.personRepository.findById(id).orElse(null);
        if(Objects.nonNull(person)) return new PersonDTO(person);
        return null;
    }

    @Transactional
    public PersonDTO update(PersonDTO personDTO) {
        try {
            Person person = this.personRepository.getReferenceById(personDTO.id());
            person.updateFields(personDTO);
            person.setContacts(this.contactsService.updateContacts(personDTO.contactsList()));
            return new PersonDTO(person);
        } catch (Exception e) {
            throw new EntityNotFoundException("Não foi encontrado pessoa com o id " + personDTO.id() + ".");
        }
    }

    @Transactional
    public void delete(BigInteger id) {
        this.personRepository.deleteById(id);
    }
}
