package com.example.monolith.product.api.v1.pojo.converter;

import com.example.monolith.product.api.v1.pojo.request.CreateProductV1Request;
import com.example.monolith.product.api.v1.pojo.request.UpdateProductV1Request;
import com.example.monolith.product.api.v1.pojo.response.ProductV1Response;
import com.example.monolith.product.service.dto.ProductDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Patryk Borchowiec
 */
@Component
public class ProductV1Converter {
    public ProductV1Response toResponse(final ProductDTO productDTO) {
        if (Objects.isNull(productDTO)) {
            return null;
        }

        return new ProductV1Response(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getAmount(),
                productDTO.getCompanyId()
        );
    }

    public ProductDTO toDTO(final CreateProductV1Request request) {
        if (Objects.isNull(request)) {
            return null;
        }

        return new ProductDTO(
                null,
                request.getName(),
                request.getAmount(),
                request.getCompanyId()
        );
    }

    public ProductDTO toDTO(final UpdateProductV1Request request, final Long id) {
        if (Objects.isNull(request)) {
            return null;
        }

        return new ProductDTO(
                id,
                request.getName(),
                request.getAmount(),
                request.getCompanyId()
        );
    }
}
