package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalRecordRepository {

    @Autowired
    private DataRepository dataRepository;

    /**
     * Saves changes in medical records of a person
     * @param medicalRecordModifier medical records to change
     * @return changed medical records
     */
    public MedicalRecord saveMedicalRecord(MedicalRecordModifier medicalRecordModifier) {

        for (Firestation firestation : dataRepository.getFirestationList()) {
            for (Household household : firestation.getHouseholdList()) {
                for (Person person : household.getPersonList()) {
                    if(person.getFirstName().equalsIgnoreCase(medicalRecordModifier.getFirstName())
                            && person.getLastName().equalsIgnoreCase(medicalRecordModifier.getLastName())) {
                        person.getMedicalRecord().setAllergies(medicalRecordModifier.getAllergies());
                        person.getMedicalRecord().setMedications(medicalRecordModifier.getMedications());
                        return person.getMedicalRecord();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Delete medical records of a person
     * @param medicalRecordModifier medical records to be deleted
     * @return true if deleted, else false
     */
    public boolean deleteMedicalRecord(MedicalRecordModifier medicalRecordModifier) {
        medicalRecordModifier.setAllergies(new ArrayList<>());
        medicalRecordModifier.setMedications(new ArrayList<>());
        MedicalRecord result = saveMedicalRecord(medicalRecordModifier);

        if(result != null) {
            return (result.getMedications().isEmpty() && result.getAllergies().isEmpty());
        } else {
            return false;
        }
    }
}
