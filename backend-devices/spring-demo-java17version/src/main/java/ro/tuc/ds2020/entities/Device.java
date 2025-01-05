package ro.tuc.ds2020.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class Device implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name="id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "maxEnergy", nullable = false)
    private int maxEnergy;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    public Device() {
    }

    public Device(String name, String address, String description, int maxEnergy, UUID userId) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.maxEnergy = maxEnergy;
        this.userId = userId;
    }

    public Device(UUID id, String name, String address, String description, int maxEnergy, UUID userId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.maxEnergy = maxEnergy;
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
