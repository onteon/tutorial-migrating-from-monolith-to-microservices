package com.example.monolith.report.service.exception;

/**
 * @author Patryk Borchowiec
 */
public class NotFoundReportException extends RuntimeException {
    public NotFoundReportException() {
    }

    public NotFoundReportException(String message) {
        super(message);
    }
}
