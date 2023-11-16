package com.safetynet.alerts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class Firestation {
    private int id;
    private Set<Household> householdList;
}
