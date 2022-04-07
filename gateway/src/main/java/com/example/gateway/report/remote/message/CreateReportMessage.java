package com.example.gateway.report.remote.message;

import lombok.Value;

/**
 * @author Patryk Borchowiec
 */
@Value
public class CreateReportMessage {
    Long id;
    String name;
}
