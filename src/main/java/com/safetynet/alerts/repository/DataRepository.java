package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataRepository {
    private static List<Firestation> firestationList;
    private String dataFilePath = "src/main/resources/data.json";

    public DataRepository() {
        if(firestationList == null) {
            firestationList = DataLoader.LoadDataFromFile(dataFilePath);
        }
    }

    public List<Firestation> getFirestationList() {
        return firestationList;
    }
}
