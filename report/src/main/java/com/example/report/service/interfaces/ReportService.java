package com.example.report.service.interfaces;

import com.example.report.service.dto.ReportDTO;
import com.example.report.service.dto.ReportInfoDTO;
import lombok.NonNull;

import java.util.List;

/**
 * @author Patryk Borchowiec
 */
public interface ReportService {
    void create(final @NonNull ReportDTO reportDTO);
    List<ReportInfoDTO> getAll();
    ReportDTO getById(final @NonNull Long id);
    void update(final @NonNull ReportDTO reportDTO);
    void deleteById(final @NonNull Long id);
}
