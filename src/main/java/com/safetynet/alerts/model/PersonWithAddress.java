package com.safetynet.alerts.model;

import lombok.Data;

@Data
public class PersonWithAddress extends Person {
    private String address;

    public PersonWithAddress(String address, Person person) {
        super(person.getFirstName(), person.getLastName(), person.getEmail(), person.getPhone(), person.getBirthDay(), person.getMedicalRecord());
        this.address = address;
    }
}
