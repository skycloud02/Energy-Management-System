package ro.tuc.ds2020.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "user_devices")
public class UserDevice {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name="id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    public UserDevice() {}

    public UserDevice(UUID userId, UUID deviceId) {
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
