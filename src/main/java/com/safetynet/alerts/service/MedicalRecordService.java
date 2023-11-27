package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.MedicalRecordModifier;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordService {
    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    public MedicalRecord saveMedicalRecord(MedicalRecordModifier medicalRecordModifier) {
        return medicalRecordRepository.saveMedicalRecord(medicalRecordModifier);
    }

    public boolean deleteMedicalRecord(String firstName, String lastName) {
        return medicalRecordRepository.deleteMedicalRecord(firstName, lastName);
    }
}
