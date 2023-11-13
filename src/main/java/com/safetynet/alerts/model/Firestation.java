package com.safetynet.alerts.model;

import lombok.Data;

import java.util.List;

@Data
public class Firestation {
    private int id;
    private List<Household> householdList;
}
