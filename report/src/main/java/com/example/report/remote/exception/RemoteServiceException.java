package com.example.report.remote.exception;

/**
 * @author Patryk Borchowiec
 */
public class RemoteServiceException extends RuntimeException {
    public RemoteServiceException() {
    }

    public RemoteServiceException(String message) {
        super(message);
    }

    public RemoteServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
