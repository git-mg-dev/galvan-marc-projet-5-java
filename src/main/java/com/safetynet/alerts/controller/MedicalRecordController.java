package com.safetynet.alerts.controller;

import com.safetynet.alerts.exceptions.PersonNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.MedicalRecordModifier;
import com.safetynet.alerts.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping("medicalRecord")
    public HttpEntity<MedicalRecordModifier> addMedicalRecord(@RequestBody MedicalRecordModifier medicalRecordModifier) throws URISyntaxException {
        MedicalRecord medicalRecordAdded = medicalRecordService.saveMedicalRecord(medicalRecordModifier);

        if(Objects.isNull(medicalRecordAdded)) {
            return ResponseEntity.noContent().build();
        } else {
            URI personInfoUri = new URI("personInfo");
            URI redirect = ServletUriComponentsBuilder
                    .fromUri(personInfoUri)
                    .query("firstName={firstName}")
                    .query("lastName={lastName}")
                    .buildAndExpand(medicalRecordModifier.getFirstName(), medicalRecordModifier.getLastName())
                    .toUri();
            return ResponseEntity.created(redirect).build();
        }
    }

    @PutMapping("medicalRecord")
    public void updateMedicalRecord(@RequestBody MedicalRecordModifier medicalRecordModifier) {
        MedicalRecord medicalRecordModified = medicalRecordService.saveMedicalRecord(medicalRecordModifier);

        if(Objects.isNull(medicalRecordModified)) {
            throw new PersonNotFoundException(medicalRecordModifier.getFirstName() + " " + medicalRecordModifier.getLastName() + " was not found.");
        }
    }

    @DeleteMapping("medicalRecord")
    public void deleteMedicalRecord(@RequestBody MedicalRecordModifier medicalRecordModifier) {
        boolean medicalRecordDeleted = medicalRecordService.deleteMedicalRecord(medicalRecordModifier);

        if(!medicalRecordDeleted) {
            throw new PersonNotFoundException(medicalRecordModifier.getFirstName() + " " + medicalRecordModifier.getLastName() + " was not found.");
        }
    }
}
