package com.safetynet.alerts.service;

import com.safetynet.alerts.model.*;
import com.safetynet.alerts.repository.FirestationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirestationService {

    @Autowired
    FirestationRepository firestationRepository;

    public List<Firestation> getFirestations() {
        return firestationRepository.getFirestations();
    }

    public List<String> getAllPhoneNumberByStationNumber(int firestationNumber) {
        return firestationRepository.getAllPhoneNumberByStationNumber(firestationNumber);
    }

    public FirestationCoverageInfo getPeopleByFirestationNumber(int firestationNumber) {
        return firestationRepository.getPeopleByFirestationNumber(firestationNumber);
    }

    public FirestationFireInfo getPeopleByAddress(String address) {
        return firestationRepository.getPeopleByAddress(address);
    }

    public List<HouseholdIncident> getHouseholdByFirestationNumber(List<Integer> firestationNumbers) {
        return firestationRepository.getHouseholdByFirestationNumber(firestationNumbers);
    }

    public Firestation saveFirestation(FirestationModifier firestationModifier) {
        return firestationRepository.saveFirestation(firestationModifier);
    }

    public boolean deleteFirestation(FirestationModifier firestationModifier) {
        return firestationRepository.deleteFirestation(firestationModifier);
    }
}
