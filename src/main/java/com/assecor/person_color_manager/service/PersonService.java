package com.assecor.person_color_manager.service;

import com.assecor.person_color_manager.dto.request.PersonRequest;
import com.assecor.person_color_manager.dto.responses.PersonResponse;
import com.assecor.person_color_manager.model.Person;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonService implements IPersonService {
    private List<Person> persons = new ArrayList<>();
    private Map<Integer, String> colorMap = new HashMap<>();

    public PersonService() {
        colorMap.put(1, "blau");
        colorMap.put(2, "grün");
        colorMap.put(3, "violett");
        colorMap.put(4, "rot");
        colorMap.put(5, "gelb");
        colorMap.put(6, "türkis");
        colorMap.put(7, "weiß");
    }

    @PostConstruct
    private void loadCsvData() {

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/assecor/person_color_manager/sample-input.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 6) {
                    int id = Integer.parseInt(data[0].trim());
                    String lastName = data[1].trim();
                    String name = data[2].trim();
                    String zipcode = data[3].trim();
                    String city = data[4].trim();

                    Integer color = Integer.parseInt(data[5].trim());

                    Person person = new Person(id, name, lastName, zipcode, city, color);
                    persons.add(person);

                } else {
                    // Handle invalid lines
                    System.err.println("Invalid data format: " + line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PersonResponse> getAllPersons() {

        List<PersonResponse> resultList =   persons.stream().map(x ->{
          return new PersonResponse(x,colorMap.get(x.getColor()));
        }).collect(Collectors.toList());
        return resultList;
    }

    @Override
    public PersonResponse getPersonById(int id) {

        for (Person person : persons) {
            if (person.getId() == id) {
                return new PersonResponse(person,getColorById(person.getColor()));
            }
        }
        return null;
    }

    @Override
    public List<PersonResponse> getPersonsByColor(String color) {
        List<PersonResponse> list = new ArrayList<>();
        for (Person person : persons) {
            if (color.equalsIgnoreCase(getColorById(person.getColor()))) {
                PersonResponse response = new PersonResponse(person, getColorById(person.getColor()));
                list.add(response);
            }
        }
        return list;
    }

    @Override
    public PersonResponse addPerson(PersonRequest request) {

        Person maxPerson = persons.get(0);
        for (Person person : persons) {
            if (person.getId() > maxPerson.getId()) {
                maxPerson = person;
            }
        }

       int newId=maxPerson.getId()+1;
        Integer colorId = colorMap.entrySet().stream()
                .filter(entry -> entry.getValue().equalsIgnoreCase(request.getColor()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
        if (colorId == null) {
             colorId= 0;
        }

        Person newPerson = new Person(newId, request.getName(), request.getLastName(), request.getZipcode(), request.getCity(), colorId);
        persons.add(newPerson);

        // CSV dosyasına yeni kişi ekleme
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/com/assecor/person_color_manager/sample-input.csv", true))) {
            bw.newLine();
            bw.write(newPerson.getId() + ","
                    + newPerson.getName() + ","
                    + newPerson.getLastName() + ","
                    + newPerson.getZipcode() + ","
                    + newPerson.getCity() + ","
                    + newPerson.getColor());
        } catch (IOException e) {
            persons.remove(newPerson);
            e.printStackTrace();
        }
        return new PersonResponse(newPerson, colorMap.get(newPerson.getColor()));
    }


    @Override
    public String getColorById(Integer color) {
        return colorMap.get(color);
    }



}