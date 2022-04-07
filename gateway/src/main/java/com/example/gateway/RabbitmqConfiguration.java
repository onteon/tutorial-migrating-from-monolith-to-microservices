package com.example.gateway;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Patryk Borchowiec
 */
@Configuration
public class RabbitmqConfiguration {
    @Bean
    public Queue queue() {
        return new Queue("createReportQueue");
    }
}
