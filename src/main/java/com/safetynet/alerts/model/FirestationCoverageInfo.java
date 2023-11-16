package com.safetynet.alerts.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FirestationCoverageInfo {
    private List<PersonContactInfo> personList;
    private int adultNumber;
    private int childrenNumber;
}
