package ro.tuc.ds2020.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RabbitMQPublisher {

    private final AmqpTemplate amqpTemplate;
    private static final String EXCHANGE_NAME = "device_exchange";
    private static final String ROUTING_KEY = "device.Key";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Constructor with AmqpTemplate dependency injection
    public RabbitMQPublisher(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    // Send message to RabbitMQ
    public void sendDeviceEvent(String operation, UUID deviceId, int maxHourlyConsumption) {
        ObjectNode messageJson = objectMapper.createObjectNode();
        messageJson.put("operation", operation);
        messageJson.put("device_id", String.valueOf(deviceId));
        messageJson.put("max_hourly_consumption", maxHourlyConsumption);

        String message = messageJson.toString();
        amqpTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, message);
        System.out.println("Message sent: " + message);
    }
}
