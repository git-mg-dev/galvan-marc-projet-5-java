package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Child;
import com.safetynet.alerts.model.PersonInfo;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
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

    // C(R)UD operations
    // TODO

}
