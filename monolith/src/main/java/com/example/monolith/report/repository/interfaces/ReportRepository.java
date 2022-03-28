package com.example.monolith.report.repository.interfaces;

import com.example.monolith.report.repository.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Patryk Borchowiec
 */
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
}
