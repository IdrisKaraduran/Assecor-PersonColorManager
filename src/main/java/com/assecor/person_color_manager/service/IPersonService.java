package com.assecor.person_color_manager.service;

import com.assecor.person_color_manager.dto.request.PersonRequest;
import com.assecor.person_color_manager.dto.responses.PersonResponse;
import com.assecor.person_color_manager.model.Person;

import java.util.List;

public interface IPersonService {
    List<PersonResponse> getAllPersons();
    PersonResponse getPersonById(int id);
    List<PersonResponse> getPersonsByColor(String color);
    PersonResponse addPerson(PersonRequest request);
    String getColorById(Integer color);
}
