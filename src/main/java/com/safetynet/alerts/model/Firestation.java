package com.safetynet.alerts.model;

import lombok.Data;

import java.util.Set;

@Data
public class Firestation {
    private int id;
    private Set<Household> householdList;
}
