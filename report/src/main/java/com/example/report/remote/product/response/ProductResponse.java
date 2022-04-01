package com.example.report.remote.product.response;

import lombok.Data;

/**
 * @author Patryk Borchowiec
 */
@Data
public class ProductResponse {
    private Long id;
    private String name;
    private int amount;
    private Long companyId;
}
