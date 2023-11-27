package com.safetynet.alerts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void communityEmail_Test() throws Exception {
        // GIVEN
        String city = "Culver";

        // WHEN + THEN
        mockMvc.perform(get("http://localhost:8080/communityEmail?city="+city))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("bstel@email.com")))
                .andExpect(content().string(containsString("aly@imail.com")));

    }

    @Test
    public void childAlert_Test() throws Exception {
        // GIVEN
        String address = "892 Downing Ct";

        // WHEN + THEN
        mockMvc.perform(get("http://localhost:8080/childAlert?address="+address))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Zach")))
                .andExpect(content().string(containsString("Zemicks")));

    }

    @Test
    public void personInfo_Test() throws Exception {
        // GIVEN
        String firstName = "Felicia";
        String lastName = "Boyd";

        // WHEN + THEN
        mockMvc.perform(get("http://localhost:8080/personInfo?firstName="+firstName+"&lastName="+lastName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("jaboyd@email.com")))
                .andExpect(content().string(containsString("tetracyclaz")))
                .andExpect(content().string(containsString("xilliathal")));

    }

    @Test
    public void addPerson_Test() throws Exception {
        // GIVEN
        String requestBody = "{" +
                "\"address\":\"908 73rd St\"," +
                "\"person\":{" +
                    "\"firstName\":\"Joan\"," +
                    "\"lastName\":\"Peters\"," +
                    "\"email\":\"joanpeter@email.com\"," +
                    "\"phone\":\"841-874-7463\"," +
                    "\"birthDay\":\"03/05/1992\"," +
                    "\"medicalRecord\":{\"allergies\":[],\"medications\":[]}" +
                "}" +
                "}";

        // WHEN + THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8080/person")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void addPerson_Fail_Test() throws Exception {
        // GIVEN
        String requestBody = "{" +
                "\"address\":\"9080 73rd St\"," +
                "\"person\":{" +
                "\"firstName\":\"Alicia\"," +
                "\"lastName\":\"Peters\"," +
                "\"email\":\"aliciapeter@email.com\"," +
                "\"phone\":\"841-874-7464\"," +
                "\"birthDay\":\"03/10/1994\"," +
                "\"medicalRecord\":{\"allergies\":[],\"medications\":[]}" +
                "}" +
                "}";

        // WHEN + THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8080/person")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void updatePerson_Test() throws Exception {
        // GIVEN
        Person personToUpdate = new Person("Jamie","Peters","jpeter@email.com",
                "841-874-7462","03/06/1982", new MedicalRecord(new ArrayList<>(), new ArrayList<>()));
        personToUpdate.getMedicalRecord().getAllergies().add("peanuts");

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(personToUpdate);

        // WHEN + THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .put("http://localhost:8080/person")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void updatePerson_Fail_Test() throws Exception {
        // GIVEN
        Person personToUpdate = new Person("Wallas","Peters","wpeter@email.com",
                "841-874-7462","03/06/1982", new MedicalRecord(new ArrayList<>(), new ArrayList<>()));
        personToUpdate.getMedicalRecord().getAllergies().add("peanuts");

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(personToUpdate);

        // WHEN + THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .put("http://localhost:8080/person")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deletePerson_Test() throws Exception {
        // GIVEN
        String firstName = "Shawna";
        String lastName = "Stelzer";

        // WHEN + THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("http://localhost:8080/person?firstName=" + firstName + "&lastName=" + lastName))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    public void deletePerson_Fail_Test() throws Exception {
        // GIVEN
        String firstName = "Charles";
        String lastName = "Stelzer";

        // WHEN + THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("http://localhost:8080/person?firstName=" + firstName + "&lastName=" + lastName))
                .andExpect(status().is4xxClientError());

    }
}
