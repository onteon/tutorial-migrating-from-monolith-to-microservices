package com.example.monolith.report.service.dto.converter;

import com.example.monolith.report.repository.entity.ReportEntity;
import com.example.monolith.report.service.dto.ReportDTO;
import com.example.monolith.report.service.dto.ReportInfoDTO;
import com.example.monolith.report.service.dto.ReportStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Patryk Borchowiec
 */
@Component
public class ReportConverter {
    public ReportEntity toNewEntity(final ReportDTO reportDTO) {
        if (Objects.isNull(reportDTO)) {
            return null;
        }

        final ReportEntity reportEntity = new ReportEntity();
        reportEntity.setStatus(ReportStatus.PREPARING.name());
        reportEntity.setName(reportDTO.getName());

        return reportEntity;
    }

    public ReportDTO toDTO(final ReportEntity reportEntity) {
        if (Objects.isNull(reportEntity)) {
            return null;
        }

        return new ReportDTO(
                reportEntity.getId(),
                reportEntity.getName(),
                reportEntity.getPdfContent(),
                ReportStatus.valueOf(reportEntity.getStatus())
        );
    }

    public ReportInfoDTO toInfoDTO(final ReportEntity reportEntity) {
        if (Objects.isNull(reportEntity)) {
            return null;
        }

        return new ReportInfoDTO(
                reportEntity.getId(),
                reportEntity.getName(),
                ReportStatus.valueOf(reportEntity.getStatus())
        );
    }
}
