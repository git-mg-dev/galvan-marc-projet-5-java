package com.safetynet.alerts.repository;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.safetynet.alerts.model.Household;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class DataLoader {
    public static List<Firestation> LoadDataFromFile(String filePath) {
        Map<Integer, Firestation> firestationMap = new HashMap<>();
        Map<String, Household> householdMap = new HashMap<>();

        if(!filePath.isEmpty()) {
            try {
                byte[] fileContent = Files.readAllBytes(new File(filePath).toPath());

                JsonIterator jsonIterator = JsonIterator.parse(fileContent);
                Any any = jsonIterator.readAny();

                // Load people & households
                Any personAny = any.get("persons");
                personAny.forEach(anyPerson -> {
                        Person person = new Person();
                        person.setFirstName(anyPerson.get("firstName").toString());
                        person.setLastName(anyPerson.get("lastName").toString());
                        person.setEmail(anyPerson.get("email").toString());
                        person.setPhone(anyPerson.get("phone").toString());

                        Household household = new Household();
                        household.setAddress(anyPerson.get("address").toString());
                        household.setZipCode(anyPerson.get("zip").toString());
                        household.setCity(anyPerson.get("city").toString());

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
                            String birthDate = anyMedicalRecord.get("birthdate").toString(); // format: mm/dd/AAAA

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
                                        MedicalRecord medicalRecord = new MedicalRecord();
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
                                Firestation firestation = new Firestation();
                                firestation.setId(firestationId);
                                firestation.setHouseholdList(new ArrayList<>());
                                firestation.getHouseholdList().add(householdMap.get(householdAddress));

                                firestationMap.put(firestationId, firestation);
                            }
                        } else {
                            // TODO : log message
                            System.err.println("Firestation " + firestationId + " can't be attached to address " + householdAddress);
                        }
                    }
                );

            } catch (IOException e) {
                System.err.println(e.getMessage());
                // TODO: log message
            }
        }

        return new ArrayList<Firestation>(firestationMap.values());
    }
}
