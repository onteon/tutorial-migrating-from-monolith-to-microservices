package com.example.company.api.v1.controller;

import com.example.company.api.v1.pojo.converter.CompanyV1Converter;
import com.example.company.api.v1.pojo.request.CreateCompanyV1Request;
import com.example.company.api.v1.pojo.request.UpdateCompanyV1Request;
import com.example.company.api.v1.pojo.response.CompanyV1Response;
import com.example.company.service.exception.NotFoundCompanyException;
import com.example.company.service.interfaces.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Patryk Borchowiec
 */
@RestController
@RequestMapping(path = "/api/v1/companies", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyV1Controller {
    private final CompanyService companyService;
    private final CompanyV1Converter companyV1Converter;

    public CompanyV1Controller(final CompanyService companyService, final CompanyV1Converter companyV1Converter) {
        this.companyService = companyService;
        this.companyV1Converter = companyV1Converter;
    }

    @PostMapping
    public void create(final @RequestBody @Valid CreateCompanyV1Request request) {
        companyService.create(companyV1Converter.toDTO(request));
    }

    @GetMapping
    public List<CompanyV1Response> getAll() {
        return companyService.getAll().stream().map(companyV1Converter::toResponse).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public void update(final @RequestBody @Valid UpdateCompanyV1Request request, final @PathVariable long id) {
        try {
            companyService.update(companyV1Converter.toDTO(request, id));
        } catch (final NotFoundCompanyException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Cannot update company due to: %s", e.getMessage())
            );
        }
    }

    @DeleteMapping("/{id}")
    public void delete(final @PathVariable long id) {
        try {
            companyService.deleteById(id);
        } catch (final NotFoundCompanyException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Cannot delete company due to: %s", e.getMessage())
            );
        }
    }
}
