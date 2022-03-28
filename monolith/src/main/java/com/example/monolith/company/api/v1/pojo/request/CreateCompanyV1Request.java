package com.example.monolith.company.api.v1.pojo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Patryk Borchowiec
 */
@Data
public class CreateCompanyV1Request {
    @NotBlank
    private String name;
}
