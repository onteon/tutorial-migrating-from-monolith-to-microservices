package com.example.gateway.report.remote.impl;

import com.example.gateway.report.remote.message.CreateReportMessage;
import com.example.gateway.report.remote.interfaces.ReportRemoteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Patryk Borchowiec
 */
@Service
public class AsynchronousReportRemoteService implements ReportRemoteService {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;
    private final ObjectMapper objectMapper;

    public AsynchronousReportRemoteService(final RabbitTemplate rabbitTemplate, final Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void create(final @NonNull CreateReportMessage reportDTO) throws JsonProcessingException {
        rabbitTemplate.convertAndSend(
                queue.getName(),
                objectMapper.writeValueAsString(reportDTO)
        );
    }
}
