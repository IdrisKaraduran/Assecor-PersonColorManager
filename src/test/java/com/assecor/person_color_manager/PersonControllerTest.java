package com.assecor.person_color_manager;

import com.assecor.person_color_manager.controller.PersonController;
import com.assecor.person_color_manager.dto.request.PersonRequest;
import com.assecor.person_color_manager.dto.responses.PersonResponse;
import com.assecor.person_color_manager.service.IPersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPersonService personService;

    @Test
    public void testGetAllPersons() throws Exception {
        List<PersonResponse> persons = Arrays.asList(
                new PersonResponse(1, "Hans", "Müller", "67742", "Lauterecken", "blau"),
                new PersonResponse(2, "Peter", "Petersen", "18439", "Stralsund", "grün")
        );

        when(personService.getAllPersons()).thenReturn(persons);

        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Hans")))
                .andExpect(jsonPath("$[0].lastName", is("Müller")))
                .andExpect(jsonPath("$[0].zipcode", is("67742")))
                .andExpect(jsonPath("$[0].city", is("Lauterecken")))
                .andExpect(jsonPath("$[0].color", is("blau")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Peter")))
                .andExpect(jsonPath("$[1].lastName", is("Petersen")))
                .andExpect(jsonPath("$[1].zipcode", is("18439")))
                .andExpect(jsonPath("$[1].city", is("Stralsund")))
                .andExpect(jsonPath("$[1].color", is("grün")));
    }



    @Test
    public void testGetPersonById() throws Exception {
        PersonResponse person = new PersonResponse(1, "Hans", "Müller", "67742", "Lauterecken", "blau");

        when(personService.getPersonById(1)).thenReturn(person);

        mockMvc.perform(get("/persons/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Hans")))
                .andExpect(jsonPath("$.lastName", is("Müller")))
                .andExpect(jsonPath("$.zipcode", is("67742")))
                .andExpect(jsonPath("$.city", is("Lauterecken")))
                .andExpect(jsonPath("$.color", is("blau")));
    }

    @Test
    public void testGetPersonsByColor() throws Exception {
        List<PersonResponse> persons = Arrays.asList(
                new PersonResponse(1, "Hans", "Müller", "67742", "Lauterecken", "blau")
        );

        when(personService.getPersonsByColor("blau")).thenReturn(persons);

        mockMvc.perform(get("/persons/color/blau"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Hans")))
                .andExpect(jsonPath("$[0].lastName", is("Müller")))
                .andExpect(jsonPath("$[0].zipcode", is("67742")))
                .andExpect(jsonPath("$[0].city", is("Lauterecken")))
                .andExpect(jsonPath("$[0].color", is("blau")));
    }

    @Test
    public void testAddPerson() throws Exception {
        PersonRequest request = new PersonRequest(12, "John", "Doe", "12345", "SampleCity", "violett");
        PersonResponse response = new PersonResponse(12, "John", "Doe", "12345", "SampleCity", "violett");

        when(personService.addPerson(any(PersonRequest.class))).thenReturn(response);

        mockMvc.perform(post("/persons/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(12)))
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.zipcode", is("12345")))
                .andExpect(jsonPath("$.city", is("SampleCity")))
                .andExpect(jsonPath("$.color", is("violett")));
    }
}
