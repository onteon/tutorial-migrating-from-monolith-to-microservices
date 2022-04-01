package com.example.product.remote.company.pojo.response;

import lombok.Data;

/**
 * @author Patryk Borchowiec
 */
@Data
public class ErrorResponse {
    private String timestamp;
    private String path;
    private int status;
    private String error;
    private String message;
    private String requestId;
}
