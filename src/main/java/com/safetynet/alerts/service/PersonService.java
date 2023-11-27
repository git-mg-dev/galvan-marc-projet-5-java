package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Child;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonInfo;
import com.safetynet.alerts.model.PersonWithAddress;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public List<String> getAllEmailByCity(String city) {
        return personRepository.getAllEmailByCity(city);
    }

    public List<Child> getChildrenByAddress(String address) {
        return personRepository.getChildrenByAddress(address);
    }

    public List<PersonInfo> getPersonInfo(String firstName, String lastName) {
        return personRepository.getPersonInfo(firstName, lastName);
    }

    public Person addPerson(PersonWithAddress personWithAddress) {
        return personRepository.addPerson(personWithAddress);
    }

    public Person updatePerson(Person person) {
        return personRepository.updatePerson(person);
    }

    public boolean deletePerson(String firstName, String lastName) {
        return personRepository.deletePerson(firstName, lastName);
    }

}
