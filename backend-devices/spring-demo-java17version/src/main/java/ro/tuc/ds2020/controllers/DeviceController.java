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

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/device")
public class DeviceController {

    private final DeviceService deviceService;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public DeviceController(DeviceService deviceService, RabbitTemplate rabbitTemplate) {
        this.deviceService = deviceService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping()
    public ResponseEntity<List<DeviceDetailsDTO>> getDevices() {
        List<DeviceDetailsDTO> dtos = deviceService.findDevices();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
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

    @PutMapping()
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
    public List<Device> getDevicesByUserId(@PathVariable UUID userId) {
        return deviceService.getDevicesByUserId(userId);
    }
}
