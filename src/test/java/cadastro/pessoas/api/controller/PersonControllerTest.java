package cadastro.pessoas.api.controller;

import cadastro.pessoas.api.model.dtos.ContactsDTO;
import cadastro.pessoas.api.model.dtos.ContactsRegisterDTO;
import cadastro.pessoas.api.model.dtos.PersonDTO;
import cadastro.pessoas.api.model.dtos.PersonRegisterDTO;
import cadastro.pessoas.api.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class PersonControllerTest {
    private final MockMvc mockMvc;
    private final JacksonTester<PersonDTO> personDTOJacksonTester;
    private final JacksonTester<PersonRegisterDTO> personRegisterDTOJacksonTester;
    @MockBean
    private PersonService personService;
    private PersonDTO personDTO;
    private PersonRegisterDTO personRegisterDTO;

    @Autowired
    public PersonControllerTest(MockMvc mockMvc, JacksonTester<PersonDTO> personDTOJacksonTester, JacksonTester<PersonRegisterDTO> personRegisterDTOJacksonTester) {
        this.mockMvc = mockMvc;
        this.personDTOJacksonTester = personDTOJacksonTester;
        this.personRegisterDTOJacksonTester = personRegisterDTOJacksonTester;
    }

    @BeforeEach
    public void setUp(){
        LocalDate date = LocalDate.now().minusDays(1);
        ContactsDTO contactsDTO = new ContactsDTO(BigInteger.ONE, "Teste Contato", "987654321", "teste_contato@teste.com");
        this.personDTO = new PersonDTO(BigInteger.ONE, "Teste Pessoa", "106.410.210-70", date, new ArrayList<>(Collections.singleton(contactsDTO)));
        ContactsRegisterDTO contactsRegisterDTO = new ContactsRegisterDTO("Teste Contato", "987654321", "teste_contato@teste.com");
        this.personRegisterDTO = new PersonRegisterDTO("Teste", "106.410.210-70", date,  new ArrayList<>(Collections.singleton(contactsRegisterDTO)));
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 400 quando informações estão inválidas ao salvar")
    void case1() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/api/person"))
            .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 400 quando informações estão inválidas ao atualizar")
    void case2() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(put("/api/person/1"))
            .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 400 quando id está inválido ao buscar pelo id")
    void case3() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/api/person/teste"))
            .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 400 quando id está inválido ao deletar pelo id")
    void case4() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(delete("/api/person/teste"))
            .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 201 quando informações estão válidas ao buscar todos")
    void case5() throws Exception {
        Page<PersonDTO> page = new PageImpl<>(List.of(this.personDTO));

        when(this.personService.getAll(any())).thenReturn(page);

        MockHttpServletResponse response = mockMvc.perform(
                get("/api/person")
            )
            .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        String expectedJson = personDTOJacksonTester.write(this.personDTO).getJson();
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 201 quando informações estão válidas ao buscar por id")
    void case6() throws Exception {
        when(this.personService.getById(any())).thenReturn(this.personDTO);

        MockHttpServletResponse response = mockMvc.perform(
                get("/api/person/1")
            )
            .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        String expectedJson = personDTOJacksonTester.write(this.personDTO).getJson();
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 204 quando informações estão válidas ao deletar")
    void case7() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                delete("/api/person/1")
            )
            .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 201 quando informações estão válidas ao salvar")
    void case8() throws Exception {
        when(this.personService.save(any())).thenReturn(this.personDTO);

        MockHttpServletResponse response = mockMvc.perform(
                post("/api/person")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(personRegisterDTOJacksonTester.write(this.personRegisterDTO).getJson())
            )
            .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        String expectedJson = personDTOJacksonTester.write(this.personDTO).getJson();
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 200 quando informações estão válidas ao atualizar")
    void case9() throws Exception {
        when(this.personService.update(any())).thenReturn(this.personDTO);

        MockHttpServletResponse response = mockMvc.perform(
                put("/api/person/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(personDTOJacksonTester.write(this.personDTO).getJson())
            )
            .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        String expectedJson = personDTOJacksonTester.write(this.personDTO).getJson();
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

}