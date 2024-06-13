package com.assecor.person_color_manager.controller;


import com.assecor.person_color_manager.dto.request.PersonRequest;
import com.assecor.person_color_manager.dto.responses.PersonResponse;

import com.assecor.person_color_manager.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/persons")
public class PersonController {
    @Autowired
    private IPersonService personService;

    @GetMapping
    public List<PersonResponse> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public PersonResponse getPersonById(@PathVariable int id) {
        return personService.getPersonById(id);
    }

    @GetMapping("/color/{color}")
    public List<PersonResponse> getPersonsByColor(@PathVariable String color) {
        return personService.getPersonsByColor(color);
    }


    @PostMapping("/person")
    public PersonResponse addPerson(@RequestBody PersonRequest request) {
        return personService.addPerson(request);
    }
}


