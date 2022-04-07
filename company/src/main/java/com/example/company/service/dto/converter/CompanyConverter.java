package com.example.company.service.dto.converter;

import com.example.company.repository.entity.CompanyEntity;
import com.example.company.service.dto.CompanyDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Patryk Borchowiec
 */
@Component
public class CompanyConverter {
    public CompanyEntity toNewEntity(final CompanyDTO companyDTO) {
        if (Objects.isNull(companyDTO)) {
            return null;
        }

        final CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setName(companyDTO.getName());

        return companyEntity;
    }

    public CompanyEntity toEntity(final CompanyDTO companyDTO) {
        if (Objects.isNull(companyDTO)) {
            return null;
        }

        final CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(companyDTO.getId());
        companyEntity.setName(companyDTO.getName());

        return companyEntity;
    }

    public CompanyDTO toDTO(final CompanyEntity companyEntity) {
        if (Objects.isNull(companyEntity)) {
            return null;
        }

        return new CompanyDTO(companyEntity.getId(), companyEntity.getName());
    }
}
