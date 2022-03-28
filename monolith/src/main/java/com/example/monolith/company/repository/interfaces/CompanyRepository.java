package com.example.monolith.company.repository.interfaces;

import com.example.monolith.company.repository.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Patryk Borchowiec
 */
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
}
