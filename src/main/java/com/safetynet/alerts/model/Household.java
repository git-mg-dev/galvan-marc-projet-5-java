package com.safetynet.alerts.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Household {
    private String address;
    private String zipCode;
    private String city;
    private List<Person> personList;
}
