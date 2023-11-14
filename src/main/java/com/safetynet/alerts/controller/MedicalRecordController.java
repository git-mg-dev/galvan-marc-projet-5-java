package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    // C(R)UD operations
    // TODO
}
