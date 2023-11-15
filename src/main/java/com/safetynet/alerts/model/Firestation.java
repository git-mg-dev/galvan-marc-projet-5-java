package com.safetynet.alerts.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Firestation {
    private int id;
    private Set<Household> householdList;
}
