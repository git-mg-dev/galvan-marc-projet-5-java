package com.safetynet.alerts.model;

import lombok.Builder;
import lombok.Data;

@Data
public class MedicalRecordModifier extends MedicalRecord{
    private String firstName;
    private String lastName;

    public MedicalRecordModifier (String firstName, String lastName, MedicalRecord medicalRecord) {
        super(medicalRecord.getAllergies(), medicalRecord.getMedications());
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
