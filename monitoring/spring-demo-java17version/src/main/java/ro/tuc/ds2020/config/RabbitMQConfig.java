package ro.tuc.ds2020.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String DEVICE_QUEUE = "device_queue";
    public static final String ENERGY_QUEUE = "energy_queue";

    @Bean
    public Queue deviceQueue() {
        return new Queue(DEVICE_QUEUE);
    }

    @Bean
    public Queue energyQueue() {
        return new Queue(ENERGY_QUEUE);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}

