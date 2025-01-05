package ro.tuc.ds2020.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entities.UserDevice;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.UserDeviceRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserDeviceService {
    @Autowired
    private UserDeviceRepository userDeviceRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Value("${user.service.url}")
    private String userServiceUrl;

    public UUID addUserToUserDevice(UUID userId) {
        UserDevice userDevice = new UserDevice();
        userDevice.setUserId(userId);
        userDevice = userDeviceRepository.save(userDevice);
        return userDevice.getId(); // Returns ID of the entry in UserDevice
    }

    public void deleteDevicesByUserId(UUID userId) {
        // Delete all devices associated with the userId
        deviceRepository.deleteByUserId(userId);
        userDeviceRepository.deleteByUserId(userId);
    }

    public List<UUID> getDistinctUserIds() {
        return userDeviceRepository.findDistinctUserIds();
    }
}

