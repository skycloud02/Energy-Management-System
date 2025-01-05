package ro.tuc.ds2020.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entities.DeviceInfo;
import ro.tuc.ds2020.repositories.DeviceSyncRepository;

import java.util.Optional;

@Service
public class DeviceSyncService {

    private final DeviceSyncRepository deviceSyncRepository;

    public DeviceSyncService(DeviceSyncRepository deviceSyncRepository) {
        this.deviceSyncRepository = deviceSyncRepository;
    }

    public void saveOrUpdateDevice(DeviceInfo deviceInfo) {
        // Check if device exists in the database
        DeviceInfo existingDevice = deviceSyncRepository.findByDeviceId(deviceInfo.getDeviceId())
                .orElse(new DeviceInfo());

        // Update fields
        existingDevice.setDeviceId(deviceInfo.getDeviceId());
        existingDevice.setMaxHourlyConsumption(deviceInfo.getMaxHourlyConsumption());

        // Save to the database
        deviceSyncRepository.save(existingDevice);
    }

    public void deleteDevice(String deviceId) {
        Optional<DeviceInfo> deviceInfo = deviceSyncRepository.findByDeviceId(deviceId);
        if (deviceInfo.isPresent()) {
            deviceSyncRepository.delete(deviceInfo.get());
            System.out.println("Device with ID " + deviceId + " deleted successfully");
        } else {
            System.out.println("Device with ID " + deviceId + " not found, cannot delete");
        }
    }
}
