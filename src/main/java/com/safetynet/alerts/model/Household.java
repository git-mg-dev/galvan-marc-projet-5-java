package com.safetynet.alerts.model;

import lombok.Data;

import java.util.List;

@Data
public class Household {
    private String address;
    private String zipCode;
    private String city;
    private List<Person> personList;
}
