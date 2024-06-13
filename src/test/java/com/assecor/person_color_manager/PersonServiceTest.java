package com.assecor.person_color_manager;
import com.assecor.person_color_manager.dto.request.PersonRequest;
import com.assecor.person_color_manager.dto.responses.PersonResponse;

import com.assecor.person_color_manager.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;


class PersonServiceTest {

    private PersonService personService;

    @BeforeEach
    void setUp() {
        personService = new PersonService();
        personService.loadCsvData();

    }

    @Test
    void testGetAllPersons() {
        List<PersonResponse> allPersons = personService.getAllPersons();
        assertNotNull(allPersons);
        assertFalse(allPersons.isEmpty());
    }

    @Test
    void testGetPersonById() {
        PersonResponse person = personService.getPersonById(1);
        assertNotNull(person);
        assertEquals(1, person.getId());
        assertEquals("Hans", person.getName());
        assertEquals("Müller", person.getLastName());
        assertEquals("67742", person.getZipcode());
        assertEquals("Lauterecken", person.getCity());
        assertEquals("blau", person.getColor());
    }

    @Test
    void testGetPersonsByColor() {
        List<PersonResponse> persons = personService.getPersonsByColor("grün");
        assertNotNull(persons);
        assertFalse(persons.isEmpty());
        assertEquals(2, persons.get(0).getId());
        assertEquals("Peter", persons.get(0).getName());
        assertEquals("Petersen", persons.get(0).getLastName());
        assertEquals("18439", persons.get(0).getZipcode());
        assertEquals("Stralsund", persons.get(0).getCity());
        assertEquals("grün", persons.get(0).getColor());
    }

    @Test
    void testAddPerson() {
        PersonRequest request = new PersonRequest(12, "John", "Doe", "12345", "SampleCity", "violett");
        PersonResponse response = personService.addPerson(request);

        assertNotNull(response);
        assertEquals(12, response.getId());
        assertEquals("John", response.getName());
        assertEquals("Doe", response.getLastName());
        assertEquals("12345", response.getZipcode());
        assertEquals("SampleCity", response.getCity());
        assertEquals("violett", response.getColor());


        List<PersonResponse> persons = personService.getAllPersons();
        boolean personExists = persons.stream()
                .anyMatch(p -> p.getId() == 12 &&
                        p.getName().equals("John") &&
                        p.getLastName().equals("Doe") &&
                        p.getZipcode().equals("12345") &&
                        p.getCity().equals("SampleCity") &&
                        p.getColor().equals("violett"));
        assertTrue(personExists, "The person should be added to the list");
    }
}