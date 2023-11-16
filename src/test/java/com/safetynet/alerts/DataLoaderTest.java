package com.safetynet.alerts;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.DataLoader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
public class DataLoaderTest {
    private List<Firestation> firestations;

    @Test
    public void dataLoading_Test() {
        // GIVEN +  WHEN
        firestations = DataLoader.LoadDataFromFile("src/main/resources/data.json");

        // THEN
        assertEquals(4, firestations.size());
    }

    @Test
    public void dataLoadingNotWorking_Test() {
        // GIVEN +  WHEN
        firestations = DataLoader.LoadDataFromFile("src/main/resources/nodata.json");

        // THEN
        assertEquals(0, firestations.size());
    }

}
