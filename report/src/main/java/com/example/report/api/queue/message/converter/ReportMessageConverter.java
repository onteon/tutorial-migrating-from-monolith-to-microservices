package com.example.report.api.queue.message.converter;

import com.example.report.api.queue.message.CreateReportMessage;
import com.example.report.service.dto.ReportDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Patryk Borchowiec
 */
@Component
public class ReportMessageConverter {
    public ReportDTO toDTO(final CreateReportMessage message) {
        if (Objects.isNull(message)) {
            return null;
        }

        return new ReportDTO(null, message.getName(), null, null);
    }
}
