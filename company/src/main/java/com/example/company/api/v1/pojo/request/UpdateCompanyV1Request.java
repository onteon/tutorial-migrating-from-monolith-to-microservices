package com.example.company.api.v1.pojo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Patryk Borchowiec
 */
@Data
public class UpdateCompanyV1Request {
    @NotBlank
    private String name;
}
