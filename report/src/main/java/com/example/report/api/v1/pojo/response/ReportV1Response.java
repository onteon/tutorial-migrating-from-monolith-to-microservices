package com.example.report.api.v1.pojo.response;

import lombok.Value;

/**
 * @author Patryk Borchowiec
 */
@Value
public class ReportV1Response {
    Long id;
    String name;
    String status;
}
