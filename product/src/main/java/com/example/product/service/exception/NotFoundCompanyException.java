package com.example.product.service.exception;

/**
 * @author Patryk Borchowiec
 */
public class NotFoundCompanyException extends RuntimeException {
    public NotFoundCompanyException() {
    }

    public NotFoundCompanyException(String message) {
        super(message);
    }
}
