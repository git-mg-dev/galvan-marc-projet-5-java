package com.safetynet.alerts.controller;

import com.safetynet.alerts.exceptions.FirestationNotFoundException;
import com.safetynet.alerts.model.*;
import com.safetynet.alerts.service.FirestationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@Log4j2
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
            log.error("Mapping adding failed for firestation " + firestationModifier.getId() + ", household address is missing");
            return ResponseEntity.noContent().build();
        } else {
            log.info("Mapping was added with success for firestation " + firestationModifier.getId() + " and household "
                    + firestationModifier.getHousehold().getAddress());

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
    public void updateFirestation(@RequestBody FirestationModifier firestationModifier) {
        Firestation updatedFirestation = firestationService.saveFirestation(firestationModifier);

        if(Objects.isNull(updatedFirestation)) {
            String message = "Mapping update failed for firestation " + firestationModifier.getId() + ", household address is missing";
            log.error(message);
            throw new FirestationNotFoundException(message);
        } else {
            log.info("Mapping updated with success for firestation " + firestationModifier.getId() + " and household "
                    + firestationModifier.getHousehold().getAddress());
        }
    }

    @DeleteMapping("firestation")
    public void deleteFirestation(@RequestBody FirestationModifier firestationModifier) {
        boolean firestationDeleted = firestationService.deleteFirestation(firestationModifier);

        if(!firestationDeleted) {
            String message = "Mapping delete failed for firestation " + firestationModifier.getId() + " and household "
                    + firestationModifier.getHousehold().getAddress();
            log.error(message);
            throw new FirestationNotFoundException(message);
        } else {
            log.info("Mapping deleted with success for firestation " + firestationModifier.getId() + " and household "
                    + firestationModifier.getHousehold().getAddress());
        }
    }
}
