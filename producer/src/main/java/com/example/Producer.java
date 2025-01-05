package com.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Producer {

    private static final String QUEUE_NAME = "energy_queue";
    private static final String EXCHANGE_NAME = "energy_exchange";
    private static final String ROUTING_KEY = "energy.routing.key";
    private static final String CSV_FILE_PATH = "sensor.csv";  // Path to the CSV file

    private static ConnectionFactory factory;
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java Producer <deviceId>");
            System.exit(1);
        }

        // Get deviceId from the first command-line argument
        String deviceId = args[0];

        // Initialize RabbitMQ Connection
        setupRabbitMQConnection();

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Declare the queue and exchange in RabbitMQ
            channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

            // Read from the CSV and send messages
            readCsvAndSendMessages(channel, deviceId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setupRabbitMQConnection() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
    }

    private static void readCsvAndSendMessages(Channel channel, String deviceId) {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            long timestamp = System.currentTimeMillis(); // Starting timestamp

            while ((line = br.readLine()) != null) {
                try {
                    // Parse the single value from the CSV line
                    double measurementValue = Double.parseDouble(line.trim());

                    // Dynamically assign a device ID (e.g., "device_1")
                    //String deviceId = "device_1";

                    // Create a JSON message with the value, device ID, and timestamp
                    ObjectNode messageJson = objectMapper.createObjectNode();
                    messageJson.put("timestamp", timestamp);
                    messageJson.put("device_id", deviceId);
                    messageJson.put("measurement_value", measurementValue);

                    // Convert JSON object to string
                    String message = messageJson.toString();

                    // Send message to RabbitMQ
                    channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes("UTF-8"));
                    System.out.println("Sent message: " + message);

                    // Increment timestamp (e.g., 10 seconds between each reading)
                    //timestamp += 10 * 1000; // Add 10 seconds in milliseconds

                    // Wait before sending the next message
                    TimeUnit.SECONDS.sleep(10);

                } catch (NumberFormatException e) {
                    System.err.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
