package com.example.monolith.report.service.dto;

import lombok.Value;

/**
 * @author Patryk Borchowiec
 */
@Value
public class ReportInfoDTO {
    Long id;
    String name;
    ReportStatus status;
}
