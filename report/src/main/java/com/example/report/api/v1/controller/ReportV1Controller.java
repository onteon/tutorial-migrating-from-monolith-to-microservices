package com.example.report.api.v1.controller;

import com.example.report.api.v1.pojo.converter.ReportV1Converter;
import com.example.report.api.v1.pojo.request.CreateReportV1Request;
import com.example.report.api.v1.pojo.request.UpdateReportV1Request;
import com.example.report.api.v1.pojo.response.ReportV1Response;
import com.example.report.service.dto.ReportDTO;
import com.example.report.service.exception.NotFoundReportException;
import com.example.report.service.interfaces.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Patryk Borchowiec
 */
@RestController
@RequestMapping("/api/v1/reports")
public class ReportV1Controller {
    private final ReportService reportService;
    private final ReportV1Converter reportV1Converter;

    public ReportV1Controller(final ReportService reportService, final ReportV1Converter reportV1Converter) {
        this.reportService = reportService;
        this.reportV1Converter = reportV1Converter;
    }

    @PostMapping
    public void create(final @RequestBody @Valid CreateReportV1Request request) {
        reportService.create(reportV1Converter.toDTO(request));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReportV1Response> getAll() {
        return reportService.getAll().stream().map(reportV1Converter::toResponse).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getById(final @PathVariable Long id) {
        final ReportDTO reportDTO;
        try {
            reportDTO = reportService.getById(id);
        } catch (final NotFoundReportException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Cannot return report due to: %s", e.getMessage())
            );
        }

        final String filename = reportDTO.getName().replaceAll(" ", "_") + ".pdf";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(reportDTO.getPdfContent(), headers, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public void update(final @RequestBody @Valid UpdateReportV1Request request, final @PathVariable long id) {
        try {
            reportService.update(reportV1Converter.toDTO(request, id));
        } catch (final NotFoundReportException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Cannot update report due to: %s", e.getMessage())
            );
        }
    }

    @DeleteMapping("/{id}")
    public void delete(final @PathVariable long id) {
        try {
            reportService.deleteById(id);
        } catch (final NotFoundReportException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Cannot delete report due to: %s", e.getMessage())
            );
        }
    }
}
