package com.example.company.repository.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Patryk Borchowiec
 */
@Entity(name = "companies")
@Data
public class CompanyEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
