package ro.tuc.ds2020.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "hourly_consumption")
@Data
public class HourlyConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("device_id")
    @Column(name = "device_id", nullable = false, unique = false)
    private String deviceId;

    @JsonProperty("hourly_consumption")
    @Column(name = "hourly_consumption", nullable = false)
    private Double hourlyConsumption;

    public HourlyConsumption() {
    }

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

    public Double getHourlyConsumption() {
        return hourlyConsumption;
    }

    public void setHourlyConsumption(Double hourlyConsumption) {
        this.hourlyConsumption = hourlyConsumption;
    }

}
