package com.safetynet.alerts.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Child {
    private String firstName;
    private String lastName;
    private int age;
    private List<Person> familyMembers;

    public Child(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.age = person.getAge();
        familyMembers = new ArrayList<>();
    }
}
