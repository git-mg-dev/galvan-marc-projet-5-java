package com.safetynet.alerts.model;

import lombok.Data;

@Data
public class PersonIncidentInfo {
    private String lastName;
    private String phone;
    private int age;
    private MedicalRecord medicalRecord;

    public PersonIncidentInfo(Person person) {
        this.lastName = person.getLastName();
        this.phone = person.getPhone();
        this.age = person.getAge();
        this.medicalRecord = person.getMedicalRecord();
    }
}
