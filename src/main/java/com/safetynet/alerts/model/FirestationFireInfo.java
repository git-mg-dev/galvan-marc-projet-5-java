package com.safetynet.alerts.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FirestationFireInfo {
    private List<PersonIncidentInfo> personList;
    private List<Integer> firestationNumberList;
}
