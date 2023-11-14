package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FirestationCoverageInfo;
import com.safetynet.alerts.model.FirestationFireInfo;
import com.safetynet.alerts.model.HouseholdIncident;
import com.safetynet.alerts.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
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

    // C(R)UD operations
    // TODO
}
