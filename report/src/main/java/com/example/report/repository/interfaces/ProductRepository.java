package com.example.report.repository.interfaces;

import com.example.report.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Patryk Borchowiec
 */
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
