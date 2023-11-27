package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.FirestationModifier;
import com.safetynet.alerts.model.Household;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void firestation_Test() throws Exception {
        // GIVEN
        int firestationNumber = 2;

        // WHEN + THEN
        mockMvc.perform(get("http://localhost:8080/firestation?stationNumber="+firestationNumber))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Eric")))
                .andExpect(content().string(containsString("Cadigan")));
    }

    @Test
    public void flood_Test() throws Exception {
        // GIVEN
        int firestationNumber = 2;

        // WHEN + THEN
        mockMvc.perform(get("http://localhost:8080/flood/stations?stations="+firestationNumber))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("892 Downing Ct")))
                .andExpect(content().string(containsString("Cadigan")));
    }

    @Test
    public void phoneAlert_Test() throws Exception {
        // GIVEN
        int firestationNumber = 2;

        // WHEN + THEN
        mockMvc.perform(get("http://localhost:8080/phoneAlert?firestation="+firestationNumber))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("841-874-7458")));

    }

    @Test
    public void fire_Test() throws Exception {
        // GIVEN
        String address = "951 LoneTree Rd";

        // WHEN + THEN
        mockMvc.perform(get("http://localhost:8080/fire?address="+address))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Cadigan")))
                .andExpect(content().string(containsString("tradoxidine")));

    }

    @Test
    public void addFirestation_Test() throws Exception {
        // GIVEN
        Household household = new Household("100 Culver St", "97451", "Culver", new ArrayList<>());
        FirestationModifier firestationModifier = new FirestationModifier();
        firestationModifier.setId(9);
        firestationModifier.setHousehold(household);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(firestationModifier);

        // WHEN + THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("http://localhost:8080/firestation")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    public void addFirestation_Fail_Test() throws Exception {
        // GIVEN
        Household household = new Household("", "", "", new ArrayList<>());
        FirestationModifier firestationModifier = new FirestationModifier();
        firestationModifier.setId(9);
        firestationModifier.setHousehold(household);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(firestationModifier);

        // WHEN + THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8080/firestation")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    public void updateFirestation_Test() throws Exception {
        // GIVEN
        Household household = new Household("102 Culver St", "97451", "Culver", new ArrayList<>());
        FirestationModifier firestationModifier = new FirestationModifier();
        firestationModifier.setId(4);
        firestationModifier.setHousehold(household);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(firestationModifier);

        // WHEN + THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .put("http://localhost:8080/firestation")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    public void updateFirestation_Fail_Test() throws Exception {
        // GIVEN
        Household household = new Household("", "97451", "Culver", new ArrayList<>());
        FirestationModifier firestationModifier = new FirestationModifier();
        firestationModifier.setId(7);
        firestationModifier.setHousehold(household);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(firestationModifier);

        // WHEN + THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .put("http://localhost:8080/firestation")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void deleteFirestation_Test() throws Exception {
        // GIVEN
        String householdAddress = "489 Manchester St";
        int firestationNumber = 4;

        // WHEN + THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("http://localhost:8080/firestation?stationNumber=" + firestationNumber + "&address=" + householdAddress))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    public void deleteFirestation_Fail_Test() throws Exception {
        // GIVEN
        String householdAddress = "2 Manchester St";
        int firestationNumber = 4;

        // WHEN + THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("http://localhost:8080/firestation?stationNumber=" + firestationNumber + "&address=" + householdAddress))
                .andExpect(status().is4xxClientError());

    }
}
