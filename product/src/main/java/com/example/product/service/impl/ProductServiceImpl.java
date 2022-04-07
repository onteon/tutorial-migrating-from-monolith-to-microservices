package com.example.product.service.impl;

import com.example.product.repository.entity.CompanyEntity;
import com.example.product.repository.entity.ProductEntity;
import com.example.product.repository.interfaces.CompanyRepository;
import com.example.product.repository.interfaces.ProductRepository;
import com.example.product.service.dto.ProductDTO;
import com.example.product.service.dto.converter.ProductConverter;
import com.example.product.service.exception.NotFoundCompanyException;
import com.example.product.service.exception.NotFoundProductException;
import com.example.product.service.interfaces.ProductService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Patryk Borchowiec
 */
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final ProductConverter productConverter;

    public ProductServiceImpl(
            final ProductRepository productRepository,
            final CompanyRepository companyRepository,
            final ProductConverter productConverter
    ) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
        this.productConverter = productConverter;
    }

    @Override
    public void create(final @NonNull ProductDTO productDTO) {
        final CompanyEntity companyEntity = companyRepository.findById(productDTO.getCompanyId())
                .orElseThrow(() -> new NotFoundCompanyException(
                        String.format("Not found company of id %s.", productDTO.getCompanyId())
                ));

        final ProductEntity productEntity = productConverter.toNewEntity(productDTO, companyEntity);
        productRepository.save(productEntity);
    }

    @Override
    public void update(final @NonNull ProductDTO productDTO) {
        final CompanyEntity companyEntity = companyRepository.findById(productDTO.getCompanyId())
                .orElseThrow(() -> new NotFoundCompanyException(
                        String.format("Not found company of id %s.", productDTO.getCompanyId())
                ));

        if (!productRepository.existsById(productDTO.getId())) {
            throw new NotFoundProductException(String.format("Not found product with id %s.", productDTO.getId()));
        }

        final ProductEntity productEntity = productConverter.toEntity(productDTO, companyEntity);
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
