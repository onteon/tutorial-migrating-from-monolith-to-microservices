package com.example.product.service.interfaces;

import com.example.product.service.dto.ProductDTO;
import lombok.NonNull;

import java.util.List;

/**
 * @author Patryk Borchowiec
 */
public interface ProductService {
    void create(final @NonNull ProductDTO productDTO);
    void update(final @NonNull ProductDTO productDTO);
    void deleteById(final @NonNull Long id);
    List<ProductDTO> getAll();
}
