package com.example.gateway.report.controller.pojo.converter;

import com.example.gateway.report.controller.pojo.request.CreateReportV1Request;
import com.example.gateway.report.remote.message.CreateReportMessage;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Patryk Borchowiec
 */
@Component
public class ReportV1Converter {
    public CreateReportMessage toDTO(final CreateReportV1Request request) {
        if (Objects.isNull(request)) {
            return null;
        }

        return new CreateReportMessage(null, request.getName());
    }
}
