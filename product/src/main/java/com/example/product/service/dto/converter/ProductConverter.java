package com.example.product.service.dto.converter;

import com.example.product.repository.entity.ProductEntity;
import com.example.product.service.dto.ProductDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Patryk Borchowiec
 */
@Component
public class ProductConverter {
    public ProductEntity toNewEntity(final ProductDTO productDTO, final Long companyId) {
        if (Objects.isNull(productDTO)) {
            return null;
        }

        final ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDTO.getName());
        productEntity.setAmount(productDTO.getAmount());
        productEntity.setCompany(companyId);

        return productEntity;
    }

    public ProductEntity toEntity(final ProductDTO productDTO, final Long companyId) {
        if (Objects.isNull(productDTO)) {
            return null;
        }

        final ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productDTO.getId());
        productEntity.setName(productDTO.getName());
        productEntity.setAmount(productDTO.getAmount());
        productEntity.setCompany(companyId);

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
                productEntity.getCompany()
        );
    }
}
