package ro.tuc.ds2020.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.DeviceInfo;

import java.util.Optional;

@Repository
public interface DeviceSyncRepository extends JpaRepository<DeviceInfo, Long> {
    Optional<DeviceInfo> findByDeviceId(String deviceId);
    void deleteByDeviceId(String deviceId);
}