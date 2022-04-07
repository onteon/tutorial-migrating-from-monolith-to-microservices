package com.example.report.api.v1.pojo.converter;

import com.example.report.api.v1.pojo.request.CreateReportV1Request;
import com.example.report.api.v1.pojo.request.UpdateReportV1Request;
import com.example.report.api.v1.pojo.response.ReportV1Response;
import com.example.report.service.dto.ReportDTO;
import com.example.report.service.dto.ReportInfoDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Patryk Borchowiec
 */
@Component
public class ReportV1Converter {
    public ReportDTO toDTO(final CreateReportV1Request request) {
        if (Objects.isNull(request)) {
            return null;
        }

        return new ReportDTO(null, request.getName(), null, null);
    }

    public ReportV1Response toResponse(final ReportInfoDTO reportInfoDTO) {
        if (Objects.isNull(reportInfoDTO)) {
            return null;
        }

        return new ReportV1Response(reportInfoDTO.getId(), reportInfoDTO.getName(), reportInfoDTO.getStatus().name());
    }

    public ReportDTO toDTO(final UpdateReportV1Request request, final long id) {
        if (Objects.isNull(request)) {
            return null;
        }

        return new ReportDTO(id, request.getName(), null, null);
    }
}
