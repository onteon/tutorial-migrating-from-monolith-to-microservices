package com.example.report.api.queue.receiver;

import com.example.report.api.queue.message.CreateReportMessage;
import com.example.report.api.queue.message.converter.ReportMessageConverter;
import com.example.report.service.interfaces.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 * @author Patryk Borchowiec
 */
@Component
public class CreateReportReceiver {
    private final ReportService reportService;
    private final ReportMessageConverter reportMessageConverter;
    private final ObjectMapper objectMapper;

    public CreateReportReceiver(
            final ReportService reportService,
            final ReportMessageConverter reportMessageConverter
    ) {
        this.reportService = reportService;
        this.reportMessageConverter = reportMessageConverter;
        this.objectMapper = new ObjectMapper();
    }

    public void receiveMessage(final String message) throws JsonProcessingException {
        final CreateReportMessage reportMessage = objectMapper.readValue(message, CreateReportMessage.class);
        reportService.create(reportMessageConverter.toDTO(reportMessage));
    }
}
