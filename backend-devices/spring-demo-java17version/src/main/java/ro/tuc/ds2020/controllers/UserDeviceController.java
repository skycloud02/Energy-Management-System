package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.services.UserDeviceService;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/userDevice")
public class UserDeviceController {
    @Autowired
    private UserDeviceService userDeviceService;

    @PostMapping(value = "/addUser", consumes = "text/plain")
    public ResponseEntity<UUID> addUserToUserDevice(@RequestBody String userId) {
        UUID parsedUserId = UUID.fromString(userId); // Parse the string into a UUID
        UUID userDeviceId = userDeviceService.addUserToUserDevice(parsedUserId);
        return new ResponseEntity<>(userDeviceId, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteByUser/{userId}")
    public ResponseEntity<Void> deleteDevicesByUserId(@PathVariable UUID userId) {
        userDeviceService.deleteDevicesByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getUserIds")
    public ResponseEntity<List<UUID>> getUserIds() {
        List<UUID> userIds = userDeviceService.getDistinctUserIds();
        return ResponseEntity.ok(userIds);
    }
}

