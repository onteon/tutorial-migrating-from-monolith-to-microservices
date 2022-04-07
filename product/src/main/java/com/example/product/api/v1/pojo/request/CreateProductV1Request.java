package com.example.product.api.v1.pojo.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Patryk Borchowiec
 */
@Data
public class CreateProductV1Request {
    @NotBlank
    String name;

    @Min(0)
    int amount;

    @NotNull
    Long companyId;
}
