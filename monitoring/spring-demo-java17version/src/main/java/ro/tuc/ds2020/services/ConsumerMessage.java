package ro.tuc.ds2020.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.tuc.ds2020.config.RabbitMQConfig;
import ro.tuc.ds2020.entities.Message;
import ro.tuc.ds2020.repositories.MessageRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ConsumerMessage {

    @Autowired
    private HourlyConsumptionService hourlyConsumptionService;

    private static final int MESSAGES_PER_INTERVAL = 6; // Every 6 minutes
    private final MessageRepository messageRepository;
    private final List<Message> messageBatch = new ArrayList<>();

    public ConsumerMessage(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // Clear the table at startup
    @PostConstruct
    public void init() {
        messageRepository.truncateTable();
    }

    @RabbitListener(queues = {RabbitMQConfig.ENERGY_QUEUE})
    public void consume(String message) {
        try {
            System.out.println("Received message from energy_queue: " + message);
            ObjectMapper objectMapper = new ObjectMapper();
            Message newMessage = objectMapper.readValue(message, Message.class);

            // Save the message to the database
            messageRepository.save(newMessage);

            // Add to batch for interval processing
            messageBatch.add(newMessage);
            if (messageBatch.size() >= MESSAGES_PER_INTERVAL) {
                processBatch();
            }
        } catch (Exception e) {
            System.err.println("Error processing energy_queue message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void processBatch() {
        if (!messageBatch.isEmpty()) {
            Message firstMessage = messageBatch.get(0);
            Message lastMessage = messageBatch.get(messageBatch.size() - 1);

            try {
                double consumptionDifference = lastMessage.getMeasurementValue() - firstMessage.getMeasurementValue();

                System.out.println("Device ID: " + lastMessage.getDeviceId() +
                        " | Sum of measurements: " + consumptionDifference);

                // Save to hourly consumption
                hourlyConsumptionService.saveHourlyConsumption(
                        lastMessage.getDeviceId(), // Use String deviceId
                        consumptionDifference
                );

                messageBatch.clear();
            } catch (Exception e) {
                System.err.println("Error during batch processing: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
