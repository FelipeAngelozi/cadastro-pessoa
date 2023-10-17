package cadastro.pessoas.api.controller;

import cadastro.pessoas.api.model.dtos.PersonDTO;
import cadastro.pessoas.api.model.dtos.PersonRegisterDTO;
import cadastro.pessoas.api.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;
import java.net.URI;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PersonDTO> save(@RequestBody @Valid PersonRegisterDTO personRegisterDTO,
                                          UriComponentsBuilder uriComponentsBuilder) {
        PersonDTO personDTO = this.personService.save(personRegisterDTO);
        URI uri = uriComponentsBuilder.path("/{id}").buildAndExpand(personDTO.id()).toUri();
        return ResponseEntity.created(uri).body(personDTO);
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<Page<PersonDTO>> getAll(@PageableDefault(sort = {"name"}) Pageable pageable) {
        return ResponseEntity.ok(this.personService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<PersonDTO> getById(@PathVariable BigInteger id){
        return ResponseEntity.ok(this.personService.getById(id));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<PersonDTO> update(@RequestBody PersonDTO doctorUpdateDTO) {
        return ResponseEntity.ok(this.personService.update(doctorUpdateDTO));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable BigInteger id) {
        this.personService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
