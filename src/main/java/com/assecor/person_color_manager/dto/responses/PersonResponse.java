package com.assecor.person_color_manager.dto.responses;

import com.assecor.person_color_manager.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponse {
    private int id;
    private String name;
    private String lastName;
    private String zipcode;
    private String city;
    private String color;//3

    public PersonResponse(Person person, String color){
      this.id=person.getId();
      this.name=person.getName();
      this.lastName=person.getLastName();
      this.zipcode=person.getZipcode();
      this.city= person.getCity();
      this.color=color;
    }

}
