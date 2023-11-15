package com.safetynet.alerts.controller;

import com.safetynet.alerts.exceptions.FirestationNotFoundException;
import com.safetynet.alerts.model.*;
import com.safetynet.alerts.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@RestController
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

    @GetMapping("firestation")
    @ResponseBody
    public FirestationCoverageInfo firestation(@RequestParam int stationNumber) {
        return firestationService.getPeopleByFirestationNumber(stationNumber);
    }

    @GetMapping("phoneAlert")
    @ResponseBody
    public List<String> phoneAlert(@RequestParam int firestation) {
        return firestationService.getAllPhoneNumberByStationNumber(firestation);
    }

    @GetMapping("fire")
    @ResponseBody
    public FirestationFireInfo fire(@RequestParam String address) {
        return firestationService.getPeopleByAddress(address);
    }

    @GetMapping("flood/stations")
    @ResponseBody
    public List<HouseholdIncident> floodStations(@RequestParam List<Integer> stations) {
        return firestationService.getHouseholdByFirestationNumber(stations);
    }

    @PostMapping("firestation")
    public HttpEntity<Firestation> addFirestation(@RequestBody FirestationModifier firestationModifier) throws URISyntaxException {
        Firestation addedFirestation = firestationService.saveFirestation(firestationModifier);

        if(Objects.isNull(addedFirestation)) {
            return ResponseEntity.noContent().build();
        } else {
            URI firestationUri = new URI("firestation");
            URI redirect = ServletUriComponentsBuilder
                    .fromUri(firestationUri)
                    .query("stationNumber={stationNumber}")
                    .buildAndExpand(addedFirestation.getId())
                    .toUri();
            return ResponseEntity.created(redirect).build();
        }
    }

    @PutMapping("firestation")
    public void updateFirestation(@RequestBody FirestationModifier firestation) {
        Firestation updatedFirestation = firestationService.saveFirestation(firestation);

        if(Objects.isNull(updatedFirestation)) {
            throw new FirestationNotFoundException("Firestation with id " + firestation.getId() +" was not found, it has not been updated");
        }
    }

    @DeleteMapping("firestation")
    public void deleteFirestation(@RequestBody FirestationModifier firestation) {
        boolean firestationDeleted = firestationService.deleteFirestation(firestation);

        if(!firestationDeleted) {
            throw new FirestationNotFoundException("Firestation with id " + firestation.getId() +" was not found.");
        }
    }
}
