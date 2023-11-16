package com.safetynet.alerts.controller;

import com.safetynet.alerts.exceptions.PersonNotFoundException;
import com.safetynet.alerts.model.*;
import com.safetynet.alerts.service.PersonService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@Log4j2
@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("communityEmail")
    @ResponseBody
    public List<String> communityEmail(@RequestParam String city) {
        return personService.getAllEmailByCity(city);
    }

    @GetMapping("childAlert")
    @ResponseBody
    public List<Child> childAlert(@RequestParam String address) {
        return personService.getChildrenByAddress(address);
    }

    @GetMapping("personInfo")
    @ResponseBody
    public List<PersonInfo> personInfo(@RequestParam String firstName, @RequestParam String lastName) {
        return personService.getPersonInfo(firstName, lastName);
    }

    @PostMapping("person")
    public HttpEntity<Person> addPerson(@RequestBody PersonWithAddress personWithAddress) throws URISyntaxException {
        Person personAdded = personService.addPerson(personWithAddress);

        if(Objects.isNull(personAdded)) {
            log.error("Person info adding failed for " + personWithAddress.getFirstName() + " " + personWithAddress.getLastName()
                    + " because address of household is not found");
            return ResponseEntity.noContent().build();
        } else {
            log.info("Person info was added with success for " + personWithAddress.getFirstName() + " "
                    + personWithAddress.getLastName());

            URI personInfoUri = new URI("personInfo");
            URI redirect = ServletUriComponentsBuilder
                    .fromUri(personInfoUri)
                    .query("firstName={firstName}")
                    .query("lastName={lastName}")
                    .buildAndExpand(personAdded.getFirstName(), personAdded.getLastName())
                    .toUri();
            return ResponseEntity.created(redirect).build();
        }
    }

    @PutMapping("person")
    public void updatePerson(@RequestBody Person person) {
        Person personModified = personService.updatePerson(person);

        if(Objects.isNull(personModified)) {
            String message = "Person info update failed for " + person.getFirstName() + " " + person.getLastName()
                    + ", this person was not found.";
            log.error(message);
            throw new PersonNotFoundException(message);
        } else {
            log.info("Person info was updated with success for " + person.getFirstName() + " " + person.getLastName());
        }
    }

    @DeleteMapping("person")
    public void deletePerson(@RequestBody Person person) {
        boolean medicalRecordDeleted = personService.deletePerson(person);

        if(!medicalRecordDeleted) {
            String message = "Person info delete failed for " + person.getFirstName() + " " + person.getLastName()
                    + ", this person was not found.";
            log.error(message);
            throw new PersonNotFoundException(message);
        } else {
            log.info("Person info was deleted with success for " + person.getFirstName() + " " + person.getLastName());
        }
    }
}
