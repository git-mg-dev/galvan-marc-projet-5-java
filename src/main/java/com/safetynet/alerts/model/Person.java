package com.safetynet.alerts.model;

import lombok.Data;
import java.util.Date;

@Data
public class Person {
    private String firstName;
    private String lastName;
    private String email;
    protected String phone;
    private String birthDay;
    private MedicalRecord medicalRecord;
}
