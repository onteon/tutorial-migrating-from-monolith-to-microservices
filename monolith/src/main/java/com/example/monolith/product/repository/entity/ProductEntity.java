package com.example.monolith.product.repository.entity;

import com.example.monolith.company.repository.entity.CompanyEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Patryk Borchowiec
 */
@Entity(name = "products")
@Data
public class ProductEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int amount;

    @ManyToOne
    private CompanyEntity company;
}
