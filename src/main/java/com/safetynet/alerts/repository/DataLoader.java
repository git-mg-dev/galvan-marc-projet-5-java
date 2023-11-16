package com.safetynet.alerts.repository;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.safetynet.alerts.model.Household;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Log4j2
public class DataLoader {
    public static List<Firestation> LoadDataFromFile(String filePath) {
        Map<Integer, Firestation> firestationMap = new HashMap<>();
        Map<String, Household> householdMap = new HashMap<>();

        if(!filePath.isEmpty()) {
            try {
                log.debug("Starting loading data from file " + filePath);
                byte[] fileContent = Files.readAllBytes(new File(filePath).toPath());

                JsonIterator jsonIterator = JsonIterator.parse(fileContent);
                Any any = jsonIterator.readAny();

                // Load people & households
                Any personAny = any.get("persons");
                personAny.forEach(anyPerson -> {
                        Person person = new Person(anyPerson.get("firstName").toString(),
                                anyPerson.get("lastName").toString(),
                                anyPerson.get("email").toString(),
                                anyPerson.get("phone").toString(),
                                "",
                                new MedicalRecord(new ArrayList<>(), new ArrayList<>())
                        );

                        Household household = new Household(anyPerson.get("address").toString(),
                                anyPerson.get("zip").toString(),
                                anyPerson.get("city").toString(),
                                new ArrayList<>()
                        );

                        if(householdMap.containsKey(household.getAddress())) {
                            householdMap.get(household.getAddress()).getPersonList().add(person);
                        } else {
                            List<Person> personList = new ArrayList<>();
                            personList.add(person);
                            household.setPersonList(personList);
                            householdMap.put(household.getAddress(), household);
                        }
                    }
                );

                // Load medical records
                Any medicalRecordAny = any.get("medicalrecords");
                medicalRecordAny.forEach(anyMedicalRecord -> {
                            String firstName = anyMedicalRecord.get("firstName").toString();
                            String lastName = anyMedicalRecord.get("lastName").toString();
                            String birthDate = anyMedicalRecord.get("birthdate").toString();

                            // Medications
                            Any medicationAny = anyMedicalRecord.get("medications");
                            List<String> medications = new ArrayList<>();
                            medicationAny.forEach(anyMedication -> medications.add(anyMedication.toString()));

                            // Allergies
                            Any allergyAny = anyMedicalRecord.get("allergies");
                            List<String> allergies = new ArrayList<>();
                            allergyAny.forEach(anyAllergy -> allergies.add(anyAllergy.toString()));

                            // Add medical records to people
                            for(String address : householdMap.keySet()) {
                                for(Person person : householdMap.get(address).getPersonList()) {
                                    if(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                                        person.setBirthDay(birthDate);
                                        MedicalRecord medicalRecord = new MedicalRecord(allergies, medications);
                                        medicalRecord.setAllergies(allergies);
                                        medicalRecord.setMedications(medications);
                                        person.setMedicalRecord(medicalRecord);
                                    }
                                }
                            }
                        }
                );

                // Load firestations
                Any firestationAny = any.get("firestations");
                firestationAny.forEach(anyStation -> {
                        int firestationId = anyStation.get("station").toInt();
                        String householdAddress = anyStation.get("address").toString();

                        if(householdMap.containsKey(householdAddress)) {
                            if(firestationMap.containsKey(firestationId)) {
                                firestationMap.get(firestationId).getHouseholdList().add(householdMap.get(householdAddress));
                            } else {
                                Firestation firestation = new Firestation(firestationId, new HashSet<>());
                                firestation.getHouseholdList().add(householdMap.get(householdAddress));

                                firestationMap.put(firestationId, firestation);
                            }
                        } else {
                            log.error("Firestation " + firestationId + " can't be attached to address " + householdAddress);
                        }
                    }
                );

            } catch (IOException e) {
                log.error("Couldn't read file " + filePath);
                log.debug(e.getMessage());
            }
        }

        return new ArrayList<Firestation>(firestationMap.values());
    }
}
