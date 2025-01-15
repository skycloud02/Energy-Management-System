package ro.tuc.ds2020.controllers;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.rabbitmq.RabbitMQConfig;
import ro.tuc.ds2020.rabbitmq.RabbitMQPublisher;
import ro.tuc.ds2020.services.DeviceService;
import ro.tuc.ds2020.services.JwtUtils;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/device")
public class DeviceController {

    private final DeviceService deviceService;
    private final RabbitTemplate rabbitTemplate;
    private JwtUtils jwtUtils;

    @Autowired
    public DeviceController(DeviceService deviceService, RabbitTemplate rabbitTemplate, JwtUtils jwtUtils) {
        this.deviceService = deviceService;
        this.rabbitTemplate = rabbitTemplate;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<DeviceDetailsDTO>> getDevices() {
        List<DeviceDetailsDTO> dtos = deviceService.findDevices();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping(value = "/addDevice")
    public ResponseEntity<UUID> insertDevice(@Valid @RequestBody DeviceDetailsDTO deviceDetailsDTO) {
        UUID deviceID = deviceService.insert(deviceDetailsDTO);
        //RabbitMQPublisher message = new RabbitMQPublisher(deviceID, deviceDetailsDTO.getName(), deviceDetailsDTO.getMaxEnergy(), deviceDetailsDTO.getUserId(), false);
        //rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message);
        return new ResponseEntity<>(deviceID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DeviceDetailsDTO> getDevice(@PathVariable("id") UUID deviceId) {
        DeviceDetailsDTO dto = deviceService.findDeviceById(deviceId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<UUID> updateDevice(@RequestBody DeviceDetailsDTO deviceDetailsDTO) {
        UUID deviceId = deviceService.update(deviceDetailsDTO);
        return new ResponseEntity<>(deviceId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UUID> deleteDevice(@PathVariable("id") UUID deviceId) {
        deviceService.delete(deviceId);
        return new ResponseEntity<>(deviceId, HttpStatus.OK);
    }

    @GetMapping("/getByUserId/{userId}")
    public List<Device> getDevicesByUserId(@PathVariable UUID userId, @RequestHeader("Authorization") String token) {
        String extractedUserId = jwtUtils.extractUserId(token); // Extract user ID from JWT
        System.out.println("Extracted UserId: " + extractedUserId);
        System.out.println("Requested UserId: " + userId);

        return deviceService.getDevicesByUserId(userId);
    }

}
