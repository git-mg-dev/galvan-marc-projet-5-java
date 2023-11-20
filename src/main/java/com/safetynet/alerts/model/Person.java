package com.safetynet.alerts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Log4j2
@Data
@AllArgsConstructor
public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String birthDay;
    private MedicalRecord medicalRecord;

    /**
     * Calculates age from date of birth as string with pattern MM/dd/yyyy
     * @return age
     */
    public int getAge() {
        int age = -1;

        try {
            LocalDate dob = LocalDate.parse(birthDay, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            LocalDate currentDate = LocalDate.now();
            age = Period.between(dob, currentDate).getYears();
        } catch (DateTimeException e) {
            log.error("Failed to calculate age for " + firstName + " " + lastName + " (dob: "+ birthDay +")");
            log.debug(e.getMessage());
        }

        return age;
    }

}
