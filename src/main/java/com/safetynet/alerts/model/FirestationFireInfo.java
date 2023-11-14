package com.safetynet.alerts.model;

import lombok.Data;

import java.util.List;

@Data
public class FirestationFireInfo {
    private List<PersonIncidentInfo> personList;
    private List<Integer> firestationNumberList;
}
