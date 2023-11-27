package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

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
     * @param firstName of the person whose medical record will be deleted
     * @param lastName of the person whose medical record will be deleted
     * @return true if deleted, else false
     */
    public boolean deleteMedicalRecord(String firstName, String lastName) {
        boolean result = false;

        if(!firstName.isEmpty() && !lastName.isEmpty()) {
            for (Firestation firestation : dataRepository.getFirestationList()) {
                for (Household household : firestation.getHouseholdList()) {
                    for (Person person : household.getPersonList()) {
                        if (person.getFirstName().equalsIgnoreCase(firstName)
                                && person.getLastName().equalsIgnoreCase(lastName)) {
                            person.getMedicalRecord().setAllergies(new ArrayList<>());
                            person.getMedicalRecord().setMedications(new ArrayList<>());
                            result = true;
                        }
                    }
                }
            }
        }
        return result;
    }
}
