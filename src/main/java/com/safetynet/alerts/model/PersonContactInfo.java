package com.safetynet.alerts.model;

import lombok.Data;

@Data
public class PersonContactInfo {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public PersonContactInfo(Person person, String address) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.phone = person.getPhone();
        this.address = address;
    }
}
