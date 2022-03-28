package com.example.monolith.report.repository.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * @author Patryk Borchowiec
 */
@Entity(name = "reports")
@Data
public class ReportEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Lob
    private byte[] pdfContent;

    private String status;
}
