package com.example.company.api.v1.pojo.converter;

import com.example.company.api.v1.pojo.request.CreateCompanyV1Request;
import com.example.company.api.v1.pojo.request.UpdateCompanyV1Request;
import com.example.company.api.v1.pojo.response.CompanyV1Response;
import com.example.company.service.dto.CompanyDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Patryk Borchowiec
 */
@Component
public class CompanyV1Converter {
    public CompanyV1Response toResponse(final CompanyDTO companyDTO) {
        if (Objects.isNull(companyDTO)) {
            return null;
        }

        return new CompanyV1Response(companyDTO.getId(), companyDTO.getName());
    }

    public CompanyDTO toDTO(final CreateCompanyV1Request request) {
        if (Objects.isNull(request)) {
            return null;
        }

        return new CompanyDTO(null, request.getName());
    }

    public CompanyDTO toDTO(final UpdateCompanyV1Request request, final Long id) {
        if (Objects.isNull(request)) {
            return null;
        }

        return new CompanyDTO(id, request.getName());
    }
}
