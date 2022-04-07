package com.example.product.api.v1.pojo.response;

import lombok.Value;

/**
 * @author Patryk Borchowiec
 */
@Value
public class ProductV1Response {
    Long id;
    String name;
    int amount;
    Long companyId;
}
