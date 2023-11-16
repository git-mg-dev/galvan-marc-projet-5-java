package com.safetynet.alerts.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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
                .andExpect(content().string(containsString("reg@email.com")))
                .andExpect(content().string(containsString("lily@email.com")));

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
}
