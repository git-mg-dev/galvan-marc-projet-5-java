package com.safetynet.alerts.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HouseholdIncident {
    private String address;
    private List<PersonIncidentInfo> personList;

    public HouseholdIncident(Household household) {
        this.address = household.getAddress();
        this.personList = new ArrayList<>();
        for(Person person : household.getPersonList()) {
            personList.add(new PersonIncidentInfo(person));
        }
    }
}
