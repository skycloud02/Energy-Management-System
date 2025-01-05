package ro.tuc.ds2020.dtos;


import java.util.Objects;
import java.util.UUID;

public class DeviceDTO {
    private UUID id;
    private String name;
    private String address;
    private String description;
    private int maxEnergy;
    private UUID userId;

    public DeviceDTO() {
    }

    public DeviceDTO(UUID id, String name, String address, String description, int maxEnergy, UUID userId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.maxEnergy = maxEnergy;
        this.userId = userId;
    }
    public DeviceDTO(String name, String address, String description, int maxEnergy, UUID userId) {
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
