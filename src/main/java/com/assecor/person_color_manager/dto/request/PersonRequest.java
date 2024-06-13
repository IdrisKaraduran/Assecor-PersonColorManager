package com.assecor.person_color_manager.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequest {
    private int id;
    private String name;
    private String lastName;
    private String zipcode;
    private String city;
    private String color;
}
