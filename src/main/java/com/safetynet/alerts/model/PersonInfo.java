package com.safetynet.alerts.model;

import lombok.Data;

@Data
public class PersonInfo {
    private String lastName;
    private String address;
    private int age;
    private String email;
    private MedicalRecord medicalRecord;

    public PersonInfo(Person person, String address) {
        this.lastName = person.getLastName();
        this.address = address;
        this.age = person.getAge();
        this.email = person.getEmail();
        this.medicalRecord = person.getMedicalRecord();
    }
}
