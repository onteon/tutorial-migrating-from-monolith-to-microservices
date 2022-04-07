package com.example.product.service.exception;

/**
 * @author Patryk Borchowiec
 */
public class NotFoundProductException extends RuntimeException {
    public NotFoundProductException() {
    }

    public NotFoundProductException(String message) {
        super(message);
    }
}
