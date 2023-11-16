package com.safetynet.alerts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MedicalRecord {
    private List<String> allergies;
    private List<String> medications;
}
