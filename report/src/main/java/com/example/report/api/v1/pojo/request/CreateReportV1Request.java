package com.example.report.api.v1.pojo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Patryk Borchowiec
 */
@Data
public class CreateReportV1Request {
    @NotBlank
    private String name;
}
