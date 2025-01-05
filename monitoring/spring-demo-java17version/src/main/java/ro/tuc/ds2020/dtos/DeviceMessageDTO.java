package ro.tuc.ds2020.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeviceMessageDTO {
    private String operation;

    @JsonProperty("device_id") // Mapare cu câmpul din JSON
    private String deviceId;

    @JsonProperty("max_hourly_consumption") // Mapare cu câmpul din JSON
    private Double maxHourlyConsumption;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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
