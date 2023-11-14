package com.safetynet.alerts.model;

import lombok.Data;

import java.util.List;

@Data
public class FirestationCoverageInfo {
    private List<PersonContactInfo> personList;
    private int adultNumber;
    private int childrenNumber;
}
