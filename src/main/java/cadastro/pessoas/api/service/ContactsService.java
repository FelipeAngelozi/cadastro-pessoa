package cadastro.pessoas.api.service;

import cadastro.pessoas.api.model.Contacts;
import cadastro.pessoas.api.model.dtos.ContactsDTO;
import cadastro.pessoas.api.repository.ContactRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactsService {

    public final ContactRepository contactRepository;

    @Autowired
    public ContactsService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Transactional(readOnly = true)
    public List<Contacts> updateContacts(List<ContactsDTO> contactsDTOList) {
        try {
            return contactsDTOList.stream().map((contactsDTO -> {
                Contacts contacts = this.contactRepository.getReferenceById(contactsDTO.id());
                contacts.updateFields(contactsDTO);
                return contacts;
            })).toList();
        } catch (Exception e) {
            throw new EntityNotFoundException("Não possível encontrar contato com o ID informado.");
        }

    }
}
