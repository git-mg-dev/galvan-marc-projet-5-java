package com.safetynet.alerts.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addMedicalRecord_Test() throws Exception {
        // GIVEN
        String requestBody = "{" +
                "\"firstName\":\"Roger\"," +
                "\"lastName\":\"Boyd\"," +
                "\"medicalRecord\":" +
                "{" +
                "\"allergies\":[\"peanut\"]," +
                "\"medications\":[\"tetracyclaz:250mg\"]" +
                "}" +
                "}";

        // WHEN + THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8080/medicalRecord")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void addMedicalRecord_Fail_Test() throws Exception {
        // GIVEN
        String requestBody = "{" +
                "\"firstName\":\"Paul\"," +
                "\"lastName\":\"Boyd\"," +
                "\"medicalRecord\":" +
                "{" +
                "\"allergies\":[\"peanut\"]," +
                "\"medications\":[\"tetracyclaz:250mg\"]" +
                "}" +
                "}";

        // WHEN + THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8080/medicalRecord")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void updateMedicalRecord_Test() throws Exception {
        // GIVEN
        String requestBody = "{" +
                "\"firstName\":\"Clive\"," +
                "\"lastName\":\"Ferguson\"," +
                "\"medicalRecord\":" +
                "{" +
                "\"allergies\":[\"peanut\"]," +
                "\"medications\":[\"tetracyclaz:250mg\"]" +
                "}" +
                "}";

        // WHEN + THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .put("http://localhost:8080/medicalRecord")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    public void updateMedicalRecord_Fail_Test() throws Exception {
        // GIVEN
        String requestBody = "{" +
                "\"firstName\":\"Paul\"," +
                "\"lastName\":\"Ferguson\"," +
                "\"medicalRecord\":" +
                "{" +
                "\"allergies\":[\"peanut\"]," +
                "\"medications\":[\"tetracyclaz:250mg\"]" +
                "}" +
                "}";

        // WHEN + THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .put("http://localhost:8080/medicalRecord")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void deleteMedicalRecord_Test() throws Exception {
        // GIVEN
        String requestBody = "{" +
                "\"firstName\":\"Tenley\"," +
                "\"lastName\":\"Boyd\"," +
                "\"medicalRecord\":" +
                "{" +
                "\"allergies\":[\"peanut\"]," +
                "\"medications\":[\"tetracyclaz:250mg\"]" +
                "}" +
                "}";

        // WHEN + THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("http://localhost:8080/medicalRecord")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    public void deleteMedicalRecord_Fail_Test() throws Exception {
        // GIVEN
        String requestBody = "{" +
                "\"firstName\":\"Henry\"," +
                "\"lastName\":\"Boyd\"," +
                "\"medicalRecord\":" +
                "{" +
                "\"allergies\":[\"peanut\"]," +
                "\"medications\":[\"tetracyclaz:250mg\"]" +
                "}" +
                "}";

        // WHEN + THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("http://localhost:8080/medicalRecord")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }
}
