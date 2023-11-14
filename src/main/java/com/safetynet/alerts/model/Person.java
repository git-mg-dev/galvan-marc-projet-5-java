package com.safetynet.alerts.model;

import lombok.Data;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Data
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
            System.err.println(e.getMessage());
            // TODO log message
        }

        return age;
    }

}
