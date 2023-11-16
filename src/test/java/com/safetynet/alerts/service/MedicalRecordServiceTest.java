package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.MedicalRecordModifier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

@SpringBootTest
public class MedicalRecordServiceTest {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @Test
    public void saveMedicalRecord_Test() {
        // GIVEN
        MedicalRecord medicalRecord = new MedicalRecord(new ArrayList<>(), new ArrayList<>());
        medicalRecord.getAllergies().add("peanuts");
        medicalRecord.getMedications().add("tetracyclaz:250mg");
        MedicalRecordModifier medicalRecordModifier = new MedicalRecordModifier("Roger", "Boyd", medicalRecord);

        // WHEN
        MedicalRecord result = medicalRecordService.saveMedicalRecord(medicalRecordModifier);

        // THEN
        assertEquals(1, result.getAllergies().size());
        assertEquals(1, result.getMedications().size());
    }

    @Test
    public void saveMedicalRecordFail_Test() {
        // GIVEN
        MedicalRecord medicalRecord = new MedicalRecord(new ArrayList<>(), new ArrayList<>());
        MedicalRecordModifier medicalRecordModifier = new MedicalRecordModifier("Bob", "Boyd", medicalRecord);

        // WHEN
        MedicalRecord result = medicalRecordService.saveMedicalRecord(medicalRecordModifier);

        // THEN
        assertNull(result);
    }

    @Test
    public void deleteMedicalRecord_Test() {
        // GIVEN
        MedicalRecord medicalRecord = new MedicalRecord(new ArrayList<>(), new ArrayList<>());
        MedicalRecordModifier medicalRecordModifier = new MedicalRecordModifier("Roger", "Boyd", medicalRecord);

        // WHEN
        boolean result = medicalRecordService.deleteMedicalRecord(medicalRecordModifier);

        // THEN
        assertTrue(result);
    }

    @Test
    public void deleteMedicalRecordFail_Test() {
        // GIVEN
        MedicalRecord medicalRecord = new MedicalRecord(new ArrayList<>(), new ArrayList<>());
        MedicalRecordModifier medicalRecordModifier = new MedicalRecordModifier("Bob", "Boyd", medicalRecord);

        // WHEN
        boolean result = medicalRecordService.deleteMedicalRecord(medicalRecordModifier);

        // THEN
        assertFalse(result);
    }
}
