package ro.tuc.ds2020.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entities.HourlyConsumption;
import ro.tuc.ds2020.repositories.HourlyConsumptionRepository;

@Service
public class HourlyConsumptionService {

    @Autowired
    private HourlyConsumptionRepository hourlyConsumptionRepository;

    public void saveHourlyConsumption(String deviceId, Double consumption) {
        HourlyConsumption hourlyConsumption = new HourlyConsumption();
        hourlyConsumption.setDeviceId(deviceId);
        hourlyConsumption.setHourlyConsumption(consumption);

        hourlyConsumptionRepository.save(hourlyConsumption);

        System.out.println("Saved to database: " + hourlyConsumption);
    }

}
