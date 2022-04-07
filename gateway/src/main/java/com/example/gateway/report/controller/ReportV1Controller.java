package com.example.gateway.report.controller;

import com.example.gateway.report.controller.pojo.converter.ReportV1Converter;
import com.example.gateway.report.controller.pojo.request.CreateReportV1Request;
import com.example.gateway.report.remote.interfaces.ReportRemoteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Patryk Borchowiec
 */
@RestController
public class ReportV1Controller {
    private final ReportV1Converter reportV1Converter;
    private final ReportRemoteService reportRemoteService;

    public ReportV1Controller(final ReportV1Converter reportV1Converter, final ReportRemoteService reportRemoteService) {
        this.reportV1Converter = reportV1Converter;
        this.reportRemoteService = reportRemoteService;
    }

    @PostMapping("/reports")
    public void create(final @RequestBody @Valid CreateReportV1Request request) throws JsonProcessingException {
        reportRemoteService.create(reportV1Converter.toDTO(request));
    }
}
