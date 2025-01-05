package ro.tuc.ds2020.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("timestamp")
    @Column(name = "timestamp")
    private long timestamp;

    @JsonProperty("device_id")
    @Column(name = "device_id")
    private String deviceId;

    @JsonProperty("measurement_value")
    @Column(name = "measurement_value")
    private double measurementValue;


    public Message() {
    }

    // Constructor
    public Message(long timestamp, String deviceId, double measurementValue) {
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.measurementValue = measurementValue;
    }

    // Getters and setters
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public double getMeasurementValue() {
        return measurementValue;
    }

    public void setMeasurementValue(double measurementValue) {
        this.measurementValue = measurementValue;
    }
}
