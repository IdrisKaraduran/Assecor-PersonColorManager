package com.assecor.person_color_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private int id;
    private String name;
    private String lastName;
    private String zipcode;
    private String city;
    private Integer color;


}
