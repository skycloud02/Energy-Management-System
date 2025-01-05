package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.rabbitmq.RabbitMQPublisher;
import ro.tuc.ds2020.repositories.DeviceRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;
    private RabbitMQPublisher rabbitMQPublisher;


    @Autowired
    public DeviceService(DeviceRepository deviceRepository, RabbitMQPublisher rabbitMQPublisher) {
        this.deviceRepository = deviceRepository;
        this.rabbitMQPublisher = rabbitMQPublisher;
    }

    public List<DeviceDetailsDTO> findDevices() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDetailsDTO)
                .collect(Collectors.toList());
    }

    public DeviceDetailsDTO findDeviceById(UUID id) {
        Device device = deviceRepository.findById(id).orElse(null);
        if(device == null) {
            LOGGER.error("Device with id {} not found", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
        return DeviceBuilder.toDeviceDetailsDTO(device);
    }

    public UUID insert(DeviceDetailsDTO deviceDetailsDTO) {
        Device device = DeviceBuilder.toEntityWithId(deviceDetailsDTO);
        device = deviceRepository.save(device);
        rabbitMQPublisher.sendDeviceEvent("INSERT", device.getId(), device.getMaxEnergy());
        LOGGER.debug("Device with id {} was inserted in db", device.getId());
        return device.getId();
    }

    public UUID update(DeviceDetailsDTO deviceDetailsDTO) {
        if(!deviceRepository.findById(deviceDetailsDTO.getId()).isPresent()) {
            LOGGER.error("Device with id {} not found", deviceDetailsDTO.getId());
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + deviceDetailsDTO.getId());
        }
        Device device = DeviceBuilder.toEntityWithId(deviceDetailsDTO);
        device = deviceRepository.save(device);
        LOGGER.debug("Device with id {} was updated in db", device.getId());
        return device.getId();
    }

    public void delete(UUID id) {

        try{
            deviceRepository.deleteById(id);
            LOGGER.debug("Device with id {} was deleted in db", id);
        }
        catch(Exception e){
            LOGGER.error("Device with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
    }
    public List<Device> getDevicesByUserId(UUID userId) {
        return deviceRepository.findByUserId(userId);
    }
}
