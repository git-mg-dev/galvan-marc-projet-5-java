package com.safetynet.alerts.service;

import com.safetynet.alerts.DataRepositoryTest;
import com.safetynet.alerts.model.*;
import com.safetynet.alerts.repository.DataRepository;
import com.safetynet.alerts.service.FirestationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class FirestationServiceTest {
    @Autowired
    private FirestationService firestationService;
    @Autowired
    DataRepositoryTest dataRepositoryTest;

    @Test
    public void getAllPhoneNumberByStationNumber_Test() {
        // GIVEN
        int firestationNumber = 3;

        // WHEN
        List<String> result = firestationService.getAllPhoneNumberByStationNumber(firestationNumber);

        // THEN
        assertEquals(11, result.size());
    }

    @Test
    public void getPeopleByFirestationNumber_Test() {
        // GIVEN
        int firestationNumber = 3;

        // WHEN
        FirestationCoverageInfo result = firestationService.getPeopleByFirestationNumber(firestationNumber);

        // THEN
        assertEquals(11, result.getPersonList().size());
        assertEquals(8, result.getAdultNumber());
        assertEquals(3, result.getChildrenNumber());
    }

    @Test
    public void getPeopleByAddress_Test() {
        // GIVEN
        String address = "112 Steppes Pl";

        // WHEN
        FirestationFireInfo result = firestationService.getPeopleByAddress(address);

        // THEN
        assertEquals(2, result.getFirestationNumberList().size());
        assertEquals(3, result.getPersonList().size());
    }

    @Test
    public void getHouseholdByFirestationNumber_Test() {
        // GIVEN
        List<Integer> firestationNumbers = new ArrayList<>();
        firestationNumbers.add(1);
        firestationNumbers.add(3);

        // WHEN
        List<HouseholdIncident> result = firestationService.getHouseholdByFirestationNumber(firestationNumbers);

        // THEN
        assertEquals(7, result.size());
    }

    @Test
    public void addNewHouseholdToFirestation_Test() {
        // GIVEN
        Household household = new Household("100 Culver St", "97451", "Culver", new ArrayList<>());
        FirestationModifier firestation = new FirestationModifier();
        firestation.setId(4);
        firestation.setHousehold(household);

        // WHEN
        int numberOfStationBefore = dataRepositoryTest.getNumberOfFirestations();
        int numberOfHouseholdBefore = dataRepositoryTest.getNumberOfHouseholdInFirestation(4);
        Firestation result = firestationService.saveFirestation(firestation);
        int numberOfStationAfter = dataRepositoryTest.getNumberOfFirestations();
        int numberOfHouseholdAfter = dataRepositoryTest.getNumberOfHouseholdInFirestation(4);

        // THEN
        assertEquals(3, result.getHouseholdList().size());
        assertEquals(numberOfStationBefore, numberOfStationAfter);
        assertEquals(numberOfHouseholdBefore+1, numberOfHouseholdAfter);
    }

    @Test
    public void addNewFirestation_Test() {
        // GIVEN
        Household household = new Household("100 Culver St", "97451", "Culver", new ArrayList<>());
        FirestationModifier firestation = new FirestationModifier();
        firestation.setId(9);
        firestation.setHousehold(household);

        // WHEN
        int numberOfStationBefore = dataRepositoryTest.getNumberOfFirestations();
        Firestation result = firestationService.saveFirestation(firestation);
        int numberOfStationAfter = dataRepositoryTest.getNumberOfFirestations();

        // THEN
        assertEquals(1, result.getHouseholdList().size());
        assertEquals(numberOfStationBefore+1, numberOfStationAfter);
    }

    @Test
    public void deleteFirestation_Test() {
        // GIVEN
        Household household = new Household("100 Culver St", "97451", "Culver", new ArrayList<>());
        FirestationModifier firestation = new FirestationModifier();
        firestation.setId(9);
        firestation.setHousehold(household);
        firestationService.saveFirestation(firestation);

        // WHEN
        int numberOfHouseholdBefore = dataRepositoryTest.getNumberOfHouseholdInFirestation(9);
        boolean result = firestationService.deleteFirestation(firestation);
        int numberOfHouseholdAfter = dataRepositoryTest.getNumberOfHouseholdInFirestation(9);

        // THEN
        assertTrue(result);
        assertEquals(numberOfHouseholdBefore-1, numberOfHouseholdAfter);
    }

    @Test
    public void deleteFirestationFail_Test() {
        // GIVEN
        Household household = new Household("100 Culver St", "97451", "Culver", new ArrayList<>());
        FirestationModifier firestation = new FirestationModifier();
        firestation.setId(9);
        firestation.setHousehold(household);

        // WHEN
        int numberOfHouseholdBefore = dataRepositoryTest.getNumberOfHouseholdInFirestation(9);
        boolean result = firestationService.deleteFirestation(firestation);
        int numberOfHouseholdAfter = dataRepositoryTest.getNumberOfHouseholdInFirestation(9);

        // THEN
        assertFalse(result);
        assertEquals(numberOfHouseholdBefore, numberOfHouseholdAfter);
    }
}
