package ro.tuc.ds2020.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "device_info")
@Data
public class DeviceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("device_id")
    @Column(name = "device_id", nullable = false, unique = true)
    private String deviceId;

    @JsonProperty("max_hourly_consumption")
    @Column(name = "max_hourly_consumption", nullable = false)
    private Double maxHourlyConsumption;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Double getMaxHourlyConsumption() {
        return maxHourlyConsumption;
    }

    public void setMaxHourlyConsumption(Double maxHourlyConsumption) {
        this.maxHourlyConsumption = maxHourlyConsumption;
    }
}
