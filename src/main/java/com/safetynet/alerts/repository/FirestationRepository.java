package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.*;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.*;

@Data
@Repository
public class FirestationRepository {

    private List<Firestation> firestations;

    public FirestationRepository() {
        firestations = DataLoader.LoadDataFromFile("src/main/resources/data.json");
    }

    /**
     * Get all phone numbers of the people linked to a firestation
     * @param firestationNumber id of firestation
     * @return phone numbers as a list of strings
     */
    public List<String> getAllPhoneNumberByStationNumber(int firestationNumber) {
        List<String> phoneNumbers = new ArrayList<>();

        for(Firestation firestation : firestations) {
            if(firestation.getId() == firestationNumber) {
                for (Household household : firestation.getHouseholdList()) {
                    for (Person person : household.getPersonList()) {
                        phoneNumbers.add(person.getPhone());
                    }
                }
            }
        }
        return phoneNumbers;
    }

    /**
     * Get people information of the people linked to a firestation
     * @param firestationNumber id of firestation
     * @return a list of contact info, number of adults and children
     */
    public FirestationCoverageInfo getPeopleByFirestationNumber(int firestationNumber) {
        FirestationCoverageInfo result = new FirestationCoverageInfo();
        result.setPersonList(new ArrayList<>());

        for (Firestation firestation : firestations) {
            if(firestation.getId() == firestationNumber) {
                for(Household household : firestation.getHouseholdList()) {
                    for (Person person : household.getPersonList()) {
                        result.getPersonList().add(new PersonContactInfo(person, household.getAddress()));
                        if(person.getAge() > 18) {
                            result.setAdultNumber(result.getAdultNumber()+1);
                        } else {
                            result.setChildrenNumber(result.getChildrenNumber()+1);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Get information on people and firestations for a given address
     * @param address address to look for
     * @return contact information and ids of firestations linked to this address
     */
    public FirestationFireInfo getPeopleByAddress(String address) {
        FirestationFireInfo result = new FirestationFireInfo();
        Set<PersonIncidentInfo> contactList = new HashSet<>();
        Set<Integer> firestationNumberList = new HashSet<>();

        for (Firestation firestation : firestations) {
            for (Household household : firestation.getHouseholdList()) {
                if(household.getAddress().equalsIgnoreCase(address)) {
                    for (Person person : household.getPersonList()) {
                        contactList.add(new PersonIncidentInfo(person));
                    }
                    firestationNumberList.add(firestation.getId());
                }
            }
        }

        result.setPersonList(contactList.stream().toList());
        result.setFirestationNumberList(firestationNumberList.stream().toList());
        return result;
    }

    /**
     * Get the list of households for several firestations
     * @param firestationNumbers list of firestations' id
     * @return households information
     */
    public List<HouseholdIncident> getHouseholdByFirestationNumber(List<Integer> firestationNumbers) {
        List<HouseholdIncident> result = new ArrayList<>();

        for(Integer firestationNumber : firestationNumbers) {
            for(Firestation firestation : firestations) {
                if(firestationNumber == firestation.getId()) {
                    for(Household household : firestation.getHouseholdList()) {
                        result.add(new HouseholdIncident(household));
                    }
                }
            }
        }
        return result;
    }
}
