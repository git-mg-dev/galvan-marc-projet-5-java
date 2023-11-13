package com.safetynet.alerts.model;

import lombok.Data;

import java.util.List;

@Data
public class MedicalRecord {
    private List<String> allergies;
    private List<String> medications;
}
