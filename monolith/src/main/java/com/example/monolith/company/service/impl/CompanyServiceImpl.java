package com.example.monolith.company.service.impl;

import com.example.monolith.company.repository.entity.CompanyEntity;
import com.example.monolith.company.repository.interfaces.CompanyRepository;
import com.example.monolith.company.service.dto.CompanyDTO;
import com.example.monolith.company.service.dto.converter.CompanyConverter;
import com.example.monolith.company.service.exception.NotFoundCompanyException;
import com.example.monolith.company.service.interfaces.CompanyService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Patryk Borchowiec
 */
@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyConverter companyConverter;

    public CompanyServiceImpl(final CompanyRepository companyRepository, final CompanyConverter companyConverter) {
        this.companyRepository = companyRepository;
        this.companyConverter = companyConverter;
    }

    @Override
    public void create(final @NonNull CompanyDTO companyDTO) {
        final CompanyEntity companyEntity = companyConverter.toNewEntity(companyDTO);
        companyRepository.save(companyEntity);
    }

    @Override
    public void update(final @NonNull CompanyDTO companyDTO) throws NotFoundCompanyException{
        if (!companyRepository.existsById(companyDTO.getId())) {
            throw new NotFoundCompanyException(String.format("Not found company with id %s", companyDTO.getId()));
        }

        final CompanyEntity companyEntity = companyConverter.toEntity(companyDTO);
        companyRepository.save(companyEntity);
    }

    @Override
    public void deleteById(final @NonNull Long id) throws NotFoundCompanyException {
        if (!companyRepository.existsById(id)) {
            throw new NotFoundCompanyException(String.format("Not found company with id %s", id));
        }

        companyRepository.deleteById(id);
    }

    @Override
    public List<CompanyDTO> getAll() {
        return companyRepository.findAll().stream().map(companyConverter::toDTO).collect(Collectors.toList());
    }
}
