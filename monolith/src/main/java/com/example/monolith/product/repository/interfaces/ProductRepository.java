package com.example.monolith.product.repository.interfaces;

import com.example.monolith.product.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Patryk Borchowiec
 */
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
