package ro.tuc.ds2020.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String DEVICE_QUEUE = "device_queue";
    public static final String DEVICE_EXCHANGE = "device_exchange";
    public static final String DEVICE_ROUTING_KEY = "device.Key";

    @Bean
    public Queue devicesQueue() {
        return new Queue(DEVICE_QUEUE, true);
    }

    @Bean
    public TopicExchange deviceExchange() {
        return new TopicExchange(DEVICE_EXCHANGE);
    }

    @Bean
    public Binding deviceBinding(Queue deviceQueue, TopicExchange deviceExchange) {
        return new Binding(DEVICE_QUEUE, Binding.DestinationType.QUEUE, DEVICE_EXCHANGE, DEVICE_ROUTING_KEY, null);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}

