package com.example.company.service.interfaces;

import com.example.company.service.dto.CompanyDTO;
import com.example.company.service.exception.NotFoundCompanyException;
import lombok.NonNull;

import java.util.List;

/**
 * @author Patryk Borchowiec
 */
public interface CompanyService {
    void create(final @NonNull CompanyDTO companyDTO);
    void update(final @NonNull CompanyDTO companyDTO) throws NotFoundCompanyException;
    void deleteById(final @NonNull Long id) throws NotFoundCompanyException;
    List<CompanyDTO> getAll();
}
