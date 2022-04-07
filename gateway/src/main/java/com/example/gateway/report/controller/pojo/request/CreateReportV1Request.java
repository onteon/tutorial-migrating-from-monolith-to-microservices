package com.example.gateway.report.controller.pojo.request;

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
