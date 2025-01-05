package ro.tuc.ds2020.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.HourlyConsumption;

import java.util.Optional;

@Repository
public interface HourlyConsumptionRepository extends JpaRepository<HourlyConsumption, Long> {
    Optional<HourlyConsumption> findByDeviceId(Long deviceId);
    void deleteByDeviceId(Long deviceId);
}