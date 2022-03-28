package com.example.monolith.report.service.dto;

import lombok.Value;

/**
 * @author Patryk Borchowiec
 */
@Value
public class ReportDTO {
    Long id;
    String name;
    byte[] pdfContent;
    ReportStatus status;
}
