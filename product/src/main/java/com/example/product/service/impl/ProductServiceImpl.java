package com.example.product.service.impl;

import com.example.product.remote.company.interfaces.CompanyRemoteService;
import com.example.product.remote.company.pojo.response.CompanyResponse;
import com.example.product.repository.entity.ProductEntity;
import com.example.product.repository.interfaces.ProductRepository;
import com.example.product.service.dto.ProductDTO;
import com.example.product.service.dto.converter.ProductConverter;
import com.example.product.service.exception.NotFoundCompanyException;
import com.example.product.service.exception.NotFoundProductException;
import com.example.product.service.interfaces.ProductService;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Patryk Borchowiec
 */
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final CompanyRemoteService companyRemoteService;

    public ProductServiceImpl(
            final ProductRepository productRepository,
            final ProductConverter productConverter,
            CompanyRemoteService companyRemoteService) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.companyRemoteService = companyRemoteService;
    }

    @Override
    public void create(final @NonNull ProductDTO productDTO) {
        final CompanyResponse companyResponse;
        try {
            companyResponse = companyRemoteService.getById(productDTO.getCompanyId());
        } catch (final ResponseStatusException e) {
            if (e.getStatus().equals(HttpStatus.NOT_FOUND)) {
                throw new NotFoundCompanyException(
                        String.format("Not found company of id %s.", productDTO.getCompanyId()),
                        e
                );
            }
            throw e;
        }

        final ProductEntity productEntity = productConverter.toNewEntity(productDTO, companyResponse.getId());
        productRepository.save(productEntity);
    }

    @Override
    public void update(final @NonNull ProductDTO productDTO) {
        final CompanyResponse companyResponse;
        try {
            companyResponse = companyRemoteService.getById(productDTO.getCompanyId());
        } catch (final ResponseStatusException e) {
            if (e.getStatus().equals(HttpStatus.NOT_FOUND)) {
                throw new NotFoundCompanyException(
                        String.format("Not found company of id %s.", productDTO.getCompanyId()),
                        e
                );
            }
            throw e;
        }

        if (!productRepository.existsById(productDTO.getId())) {
            throw new NotFoundProductException(String.format("Not found product with id %s.", productDTO.getId()));
        }

        final ProductEntity productEntity = productConverter.toEntity(productDTO, companyResponse.getId());
        productRepository.save(productEntity);
    }

    @Override
    public void deleteById(final @NonNull Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundProductException(String.format("Not found product with id %s.", id));
        }

        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> getAll() {
        return productRepository.findAll().stream().map(productConverter::toDTO).collect(Collectors.toList());
    }
}
