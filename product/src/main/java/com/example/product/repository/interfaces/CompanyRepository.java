package com.example.product.repository.interfaces;

import com.example.product.repository.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Patryk Borchowiec
 */
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
}
