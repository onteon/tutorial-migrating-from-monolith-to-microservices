package com.example.report.repository.interfaces;

import com.example.report.repository.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Patryk Borchowiec
 */
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
}
