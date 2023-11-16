package com.safetynet.alerts;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Household;
import com.safetynet.alerts.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DataRepositoryTest {
    @Autowired
    private DataRepository dataRepository;

    public int getNumberOfPeople() {
        int result = 0;
        for(Firestation firestation : dataRepository.getFirestationList()) {
            for(Household household : firestation.getHouseholdList()) {
                result += household.getPersonList().size();
            }
        }

        return result;
    }

    public int getNumberOfFirestations() {
        return dataRepository.getFirestationList().size();
    }

    public int getNumberOfHouseholdInFirestation(int firestationNumber) {
        int result = 0;
        for(Firestation firestation : dataRepository.getFirestationList()) {
            if(firestation.getId() == firestationNumber) {
                result += firestation.getHouseholdList().size();
            }
        }

        return result;
    }
}
