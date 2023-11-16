package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FirestationRepository {

    @Autowired
    private DataRepository dataRepository;

    /**
     * Get all phone numbers of the people linked to a firestation
     * @param firestationNumber id of firestation
     * @return phone numbers as a list of strings
     */
    public List<String> getAllPhoneNumberByStationNumber(int firestationNumber) {
        List<String> phoneNumbers = new ArrayList<>();

        for(Firestation firestation : dataRepository.getFirestationList()) {
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

        for (Firestation firestation : dataRepository.getFirestationList()) {
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

        for (Firestation firestation : dataRepository.getFirestationList()) {
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
            for(Firestation firestation : dataRepository.getFirestationList()) {
                if(firestationNumber == firestation.getId()) {
                    for(Household household : firestation.getHouseholdList()) {
                        result.add(new HouseholdIncident(household));
                    }
                }
            }
        }
        return result;
    }

    /**
     * Add a new firestation or add a new household to an existing firestation
     * @param firestationToSave firestation with household to add
     * @return added firestation
     */
    public Firestation saveFirestation(FirestationModifier firestationToSave) {

        if(firestationToSave.getHousehold().getAddress() != null
                && !firestationToSave.getHousehold().getAddress().isEmpty()) {
            // Checks if firestation already exists and add a new household
            for (Firestation firestation : dataRepository.getFirestationList()) {
                if (firestation.getId() == firestationToSave.getId()) {
                    firestation.getHouseholdList().add(firestationToSave.getHousehold());
                    return firestation;
                }
            }

            // Creates new firestation
            Firestation newFirestation = new Firestation(firestationToSave.getId(), new HashSet<>());
            newFirestation.getHouseholdList().add(firestationToSave.getHousehold());
            dataRepository.getFirestationList().add(newFirestation);
            return newFirestation;
        } else {
            return null;
        }
    }

    /**
     * Remove a household from a firestation
     * @param firestationToDelete firestation and household to remove
     * @return true if removed, else false
     */
    public boolean deleteFirestation(FirestationModifier firestationToDelete) {

        for (Firestation firestation : dataRepository.getFirestationList()) {
            if(firestation.getId() == firestationToDelete.getId()) {
                for(Household household : firestation.getHouseholdList()) {
                    if(household.getAddress().equalsIgnoreCase(firestationToDelete.getHousehold().getAddress())) {
                        firestation.getHouseholdList().remove(household);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
