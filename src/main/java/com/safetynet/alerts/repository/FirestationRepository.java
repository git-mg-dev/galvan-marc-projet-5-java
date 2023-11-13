package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.List;

@Data
@Repository
public class FirestationRepository {

    private List<Firestation> firestations;

    public FirestationRepository() {
        firestations = DataLoader.LoadDataFromFile("src/main/resources/data.json");
    }


}
