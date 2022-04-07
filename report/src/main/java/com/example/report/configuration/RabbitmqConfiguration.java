package com.example.report.configuration;

import com.example.report.api.queue.receiver.CreateReportReceiver;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
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

    @Bean
    MessageListenerAdapter listenerAdapter(final CreateReportReceiver createReportReceiver) {
        return new MessageListenerAdapter(createReportReceiver, "receiveMessage");
    }

    @Bean
    SimpleMessageListenerContainer container(
            final ConnectionFactory connectionFactory,
            final MessageListenerAdapter listenerAdapter,
            final Queue queue
    ) {
        final SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queue.getName());
        container.setMessageListener(listenerAdapter);
        return container;
    }
}
