package com.example.report.repository.interfaces;

import com.example.report.repository.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Patryk Borchowiec
 */
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
}
