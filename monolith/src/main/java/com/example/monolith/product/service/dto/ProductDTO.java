package com.example.monolith.product.service.dto;

import lombok.Value;

/**
 * @author Patryk Borchowiec
 */
@Value
public class ProductDTO {
    Long id;

    String name;

    int amount;

    Long companyId;
}
