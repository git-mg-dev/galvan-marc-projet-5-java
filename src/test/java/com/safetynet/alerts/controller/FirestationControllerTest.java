package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.FirestationModifier;
import com.safetynet.alerts.model.Household;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.http.RequestEntity.post;
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
/*
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
        mockMvc.perform(post("http://localhost:8080/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated());

    }
*/
}
