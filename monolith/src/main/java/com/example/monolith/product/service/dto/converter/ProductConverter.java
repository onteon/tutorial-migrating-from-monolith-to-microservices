package com.example.monolith.product.service.dto.converter;

import com.example.monolith.company.repository.entity.CompanyEntity;
import com.example.monolith.product.repository.entity.ProductEntity;
import com.example.monolith.product.service.dto.ProductDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Patryk Borchowiec
 */
@Component
public class ProductConverter {
    public ProductEntity toNewEntity(final ProductDTO productDTO, final CompanyEntity companyEntity) {
        if (Objects.isNull(productDTO)) {
            return null;
        }

        final ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDTO.getName());
        productEntity.setAmount(productDTO.getAmount());
        productEntity.setCompany(companyEntity);

        return productEntity;
    }

    public ProductEntity toEntity(final ProductDTO productDTO, final CompanyEntity companyEntity) {
        if (Objects.isNull(productDTO)) {
            return null;
        }

        final ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productDTO.getId());
        productEntity.setName(productDTO.getName());
        productEntity.setAmount(productDTO.getAmount());
        productEntity.setCompany(companyEntity);

        return productEntity;
    }

    public ProductDTO toDTO(final ProductEntity productEntity) {
        if (Objects.isNull(productEntity)) {
            return null;
        }

        return new ProductDTO(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getAmount(),
                Optional.ofNullable(productEntity.getCompany()).map(CompanyEntity::getId).orElse(null)
        );
    }
}
