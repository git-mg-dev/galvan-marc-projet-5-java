package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PersonRepository {

    @Autowired
    private DataRepository dataRepository;

    /**
     * Get emails of all the people living in a given city
     * @param city from which to get the emails
     * @return a list of emails as strings
     */
    public List<String> getAllEmailByCity(String city) {
        List<String> emails = new ArrayList<>();

        for (Firestation firestation : dataRepository.getFirestationList()) {
            for(Household household : firestation.getHouseholdList()) {
                if(household.getCity().equalsIgnoreCase(city)) {
                    for (Person person : household.getPersonList()) {
                        emails.add(person.getEmail());
                    }
                }
            }
        }
        return emails;
    }

    /**
     * Find children for a given address
     * @param address to search into
     * @return found children
     */
    public List<Child> getChildrenByAddress(String address) {

        for (Firestation firestation : dataRepository.getFirestationList()) {
            for(Household household : firestation.getHouseholdList()) {
                if(household.getAddress().equalsIgnoreCase(address)) {
                    Map<String, Child> childrenMap = new HashMap<>();

                    // Look for children in household
                    for (Person person : household.getPersonList()) {
                        int age = person.getAge();
                        if(age < 18 && age > -1) {
                            Child child = new Child(person);
                            childrenMap.put(child.getFirstName()+child.getLastName(), child);
                        }
                    }

                    // Add members of family for each child in household
                    for(Child child : childrenMap.values()) {
                        for(Person person : household.getPersonList()) {
                            if (!child.getFirstName().equalsIgnoreCase(person.getFirstName())) {
                                child.getFamilyMembers().add(person);
                            }
                        }
                    }
                    return new ArrayList<>(childrenMap.values());
                }
            }
        }

        return new ArrayList<>();
    }

    /**
     * Looks for information on a person based on first and last names, if several people have
     * matching names it returns their information too
     * @param firstName first name of the person to look for
     * @param lastName last name of the person to look for
     * @return list of people
     */
    public List<PersonInfo> getPersonInfo(String firstName, String lastName) {
        Set<PersonInfo> personInfoSet = new HashSet<>();

        for (Firestation firestation : dataRepository.getFirestationList()) {
            for(Household household : firestation.getHouseholdList()) {
                for (Person person : household.getPersonList()) {
                    if(person.getFirstName().equalsIgnoreCase(firstName)
                            && person.getLastName().equalsIgnoreCase(lastName)) {
                        PersonInfo personInfo = new PersonInfo(person, household.getAddress());
                        personInfoSet.add(personInfo);
                    }
                }
            }
        }

        return personInfoSet.stream().toList();
    }

    /**
     * Add a person in the household at a specific address
     * @param personWithAddress person to add and address to add it in
     * @return
     */
    public Person addPerson(PersonWithAddress personWithAddress) {

        for (Firestation firestation : dataRepository.getFirestationList()) {
            for (Household household : firestation.getHouseholdList()) {
                if (household.getAddress().equalsIgnoreCase(personWithAddress.getAddress())) {
                    Person newPerson = personWithAddress;
                    household.getPersonList().add(newPerson);
                    return newPerson;
                }
            }
        }
        return null;
    }

    /**
     * Update a person data
     * @param personToUpdate person to update
     * @return person updated
     */
    public Person updatePerson(Person personToUpdate) {

        for (Firestation firestation : dataRepository.getFirestationList()) {
            for (Household household : firestation.getHouseholdList()) {
                for (Person person : household.getPersonList()) {
                    if(person.getFirstName().equalsIgnoreCase(personToUpdate.getFirstName()) &&
                    person.getLastName().equalsIgnoreCase(personToUpdate.getLastName())) {
                        person.setEmail(personToUpdate.getEmail());
                        person.setPhone(personToUpdate.getPhone());
                        person.setBirthDay(personToUpdate.getBirthDay());
                        person.setMedicalRecord(personToUpdate.getMedicalRecord());
                        return person;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Remove a person from a household
     * @param personToDelete person to remove
     * @return true if removed, else false
     */
    public boolean deletePerson(Person personToDelete) {

        for (Firestation firestation : dataRepository.getFirestationList()) {
            for (Household household : firestation.getHouseholdList()) {
                for (Person person : household.getPersonList()) {
                    if(person.getFirstName().equalsIgnoreCase(personToDelete.getFirstName()) &&
                            person.getLastName().equalsIgnoreCase(personToDelete.getLastName())) {
                        household.getPersonList().remove(person);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
