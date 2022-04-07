package com.example.product.api.v1.controller;

import com.example.product.api.v1.pojo.converter.ProductV1Converter;
import com.example.product.api.v1.pojo.request.CreateProductV1Request;
import com.example.product.api.v1.pojo.request.UpdateProductV1Request;
import com.example.product.api.v1.pojo.response.ProductV1Response;
import com.example.product.service.exception.NotFoundCompanyException;
import com.example.product.service.exception.NotFoundProductException;
import com.example.product.service.interfaces.ProductService;
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
@RequestMapping(path = "/api/v1/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductV1Controller {
    private final ProductService productService;
    private final ProductV1Converter productV1Converter;

    public ProductV1Controller(final ProductService productService, final ProductV1Converter productV1Converter) {
        this.productService = productService;
        this.productV1Converter = productV1Converter;
    }

    @PostMapping
    public void create(final @RequestBody @Valid CreateProductV1Request request) {
        try {
            productService.create(productV1Converter.toDTO(request));
        } catch (final NotFoundCompanyException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Cannot create product, due to: %s", e.getMessage())
            );
        }
    }

    @GetMapping
    public List<ProductV1Response> getAll() {
        return productService.getAll().stream().map(productV1Converter::toResponse).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public void update(final @RequestBody @Valid UpdateProductV1Request request, final @PathVariable long id) {
        try {
            productService.update(productV1Converter.toDTO(request, id));
        } catch (final NotFoundCompanyException | NotFoundProductException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Cannot update product, due to: %s", e.getMessage())
            );
        }
    }

    @DeleteMapping("/{id}")
    public void delete(final @PathVariable long id) {
        try {
            productService.deleteById(id);
        } catch (final NotFoundProductException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Cannot delete product, due to: %s", e.getMessage())
            );
        }
    }
}
