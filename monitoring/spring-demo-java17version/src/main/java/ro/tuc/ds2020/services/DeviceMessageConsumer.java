package ro.tuc.ds2020.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ro.tuc.ds2020.config.RabbitMQConfig;
import ro.tuc.ds2020.dtos.DeviceMessageDTO;
import ro.tuc.ds2020.entities.DeviceInfo;

@Slf4j
@Component

public class DeviceMessageConsumer {

    private final DeviceSyncService deviceSyncService;
    private final ObjectMapper objectMapper;

    public DeviceMessageConsumer(DeviceSyncService deviceSyncService, ObjectMapper objectMapper) {
        this.deviceSyncService = deviceSyncService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = {RabbitMQConfig.DEVICE_QUEUE})
    public void consumeDeviceMessage(String message) {
        try {
            // Log received message
            System.out.println("Received message from device_queue: " + message);

            // Parse the message
            DeviceMessageDTO deviceMessage = objectMapper.readValue(message, DeviceMessageDTO.class);

            // Log parsed DTO
            System.out.println("Parsed message: " + deviceMessage);

            // Create DeviceInfo from the DTO
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setDeviceId(deviceMessage.getDeviceId());
            deviceInfo.setMaxHourlyConsumption(deviceMessage.getMaxHourlyConsumption());

            // Perform operation based on the message's "operation" field
            switch (deviceMessage.getOperation().toUpperCase()) {
                case "INSERT":
                case "UPDATE":
                    System.out.println("Saving/Updating device: " + deviceInfo);
                    deviceSyncService.saveOrUpdateDevice(deviceInfo);
                    break;
                case "DELETE":
                    System.out.println("Deleting device with ID: " + deviceMessage.getDeviceId());
                    deviceSyncService.deleteDevice(deviceMessage.getDeviceId());
                    break;
                default:
                    System.out.println("Unknown operation: " + deviceMessage.getOperation());
            }
        } catch (Exception e) {
            // Log error
            System.err.println("Error processing device_queue message: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
