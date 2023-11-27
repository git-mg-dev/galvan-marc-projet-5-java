package com.safetynet.alerts.controller;

import com.safetynet.alerts.exceptions.PersonNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.MedicalRecordModifier;
import com.safetynet.alerts.service.MedicalRecordService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Log4j2
@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping("medicalRecord")
    public HttpEntity<MedicalRecordModifier> addMedicalRecord(@RequestBody MedicalRecordModifier medicalRecordModifier) throws URISyntaxException {
        MedicalRecord medicalRecordAdded = medicalRecordService.saveMedicalRecord(medicalRecordModifier);

        if(Objects.isNull(medicalRecordAdded)) {
            log.error("Medical record adding failed for " + medicalRecordModifier.getFirstName() + " "
                    + medicalRecordModifier.getLastName());
            return ResponseEntity.noContent().build();
        } else {
            log.info("Medical record was added with success for " + medicalRecordModifier.getFirstName() + " "
                    + medicalRecordModifier.getLastName());

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
            String message = "Medical record update failed, " + medicalRecordModifier.getFirstName() + " "
                    + medicalRecordModifier.getLastName() + " was not found.";
            log.error(message);
            throw new PersonNotFoundException(message);
        } else {
            log.info("Medical record was updated with success for " + medicalRecordModifier.getFirstName() + " "
                    + medicalRecordModifier.getLastName());
        }
    }

    @DeleteMapping("medicalRecord")
    public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        boolean medicalRecordDeleted = medicalRecordService.deleteMedicalRecord(firstName, lastName);

        if(!medicalRecordDeleted) {
            String message = "Medical record delete failed, " + firstName + " " + lastName + " was not found.";
            log.error(message);
            throw new PersonNotFoundException(message);
        } else {
            log.info("Medical record was deleted with success for " + firstName + " " + lastName);
        }
    }
}
