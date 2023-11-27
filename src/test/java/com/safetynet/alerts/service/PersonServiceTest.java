package com.safetynet.alerts.service;

import com.safetynet.alerts.DataRepositoryTest;
import com.safetynet.alerts.model.*;
import com.safetynet.alerts.repository.DataRepository;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersonServiceTest {
    @Autowired
    PersonService personService;
    @Autowired
    DataRepositoryTest dataRepositoryTest;

    @Test
    public void getAllEmailByCity_Test() {
        // GIVEN
        String city = "Culver";

        // WHEN
        List<String> result = personService.getAllEmailByCity(city);

        // THEN
        assertEquals(dataRepositoryTest.getNumberOfPeople(), result.size());
    }

    @Test
    public void getChildrenByAddress_Test() {
        // GIVEN
        String address = "1509 Culver St";

        // WHEN
        List<Child> result = personService.getChildrenByAddress(address);

        // THEN
        assertEquals(2, result.size());
    }

    @Test
    public void getPersonInfo_Test() {
        // GIVEN
        String firstName = "Roger";
        String lastName = "Boyd";

        // WHEN
        List<PersonInfo> result = personService.getPersonInfo(firstName, lastName);

        // THEN
        assertEquals(1, result.size());
        assertEquals(lastName, result.get(0).getLastName());
    }

    @Test
    public void addPerson_Test() {
        // GIVEN
        Person newPerson = new Person("Joan", "Peters", "joanpeter@email.com",
                "841-874-7463", "03/05/1992", new MedicalRecord(new ArrayList<>(), new ArrayList<>()));
        PersonWithAddress personWithAddress = new PersonWithAddress("908 73rd St", newPerson);

        // WHEN
        int numberOfPeopleBefore = dataRepositoryTest.getNumberOfPeople();
        Person result = personService.addPerson(personWithAddress);
        int numberOfPeopleAfter = dataRepositoryTest.getNumberOfPeople();

        // THEN
        assertNotNull(result);
        assertEquals(numberOfPeopleBefore+1, numberOfPeopleAfter);
    }

    @Test
    public void addPersonFail_Test() {
        // GIVEN
        Person newPerson = new Person("Joan", "Peters", "joanpeter@email.com",
                "841-874-7463", "03/05/1992", new MedicalRecord(new ArrayList<>(), new ArrayList<>()));
        PersonWithAddress personWithAddress = new PersonWithAddress("Non existing St", newPerson);

        // WHEN
        int numberOfPeopleBefore = dataRepositoryTest.getNumberOfPeople();
        Person result = personService.addPerson(personWithAddress);
        int numberOfPeopleAfter = dataRepositoryTest.getNumberOfPeople();

        // THEN
        assertNull(result);
        assertEquals(numberOfPeopleBefore, numberOfPeopleAfter);
    }

    @Test
    public void updatePerson_Test() {
        // GIVEN
        String newEmail = "rogerBoyd@email.com";
        Person person = new Person("Roger", "Boyd", newEmail,
                "841-874-6512", "09/06/2017", new MedicalRecord(new ArrayList<>(), new ArrayList<>()));
        person.getMedicalRecord().getAllergies().add("peanuts");

        // WHEN
        Person result = personService.updatePerson(person);

        // THEN
        assertNotNull(result);
        assertEquals(newEmail, result.getEmail());
        assertEquals(1, result.getMedicalRecord().getAllergies().size());
    }

    @Test
    public void updatePersonFail_Test() {
        // GIVEN
        Person person = new Person("Robert", "Boyd", "", "", "", null);

        // WHEN
        Person result = personService.updatePerson(person);

        // THEN
        assertNull(result);
    }

    @Test
    public void deletePerson_Test() {
        // GIVEN
        String firstName = "Eric";
        String lastName = "Cadigan";

        // WHEN
        boolean result = personService.deletePerson(firstName, lastName);

        // THEN
        assertTrue(result);
    }

    @Test
    public void deletePersonFail_Test() {
        // GIVEN
        String firstName = "Georges";
        String lastName = "Cadigan";

        // WHEN
        boolean result = personService.deletePerson(firstName, lastName);

        // THEN
        assertFalse(result);
    }
}
