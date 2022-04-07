package com.example.report.service.impl;

import com.example.report.repository.entity.CompanyEntity;
import com.example.report.repository.entity.ProductEntity;
import com.example.report.repository.entity.ReportEntity;
import com.example.report.repository.interfaces.CompanyRepository;
import com.example.report.repository.interfaces.ProductRepository;
import com.example.report.repository.interfaces.ReportRepository;
import com.example.report.service.dto.ReportDTO;
import com.example.report.service.dto.ReportInfoDTO;
import com.example.report.service.dto.ReportStatus;
import com.example.report.service.dto.converter.ReportConverter;
import com.example.report.service.exception.NotFoundReportException;
import com.example.report.service.interfaces.ReportService;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Patryk Borchowiec
 */
@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final CompanyRepository companyRepository;
    private final ProductRepository productRepository;
    private final ReportConverter reportConverter;
    private final Font h1;
    private final Font h2;
    private final Font h3;

    public ReportServiceImpl(
            final ReportRepository reportRepository,
            final CompanyRepository companyRepository,
            final ProductRepository productRepository,
            final ReportConverter reportConverter
    ) {
        this.reportRepository = reportRepository;
        this.companyRepository = companyRepository;
        this.productRepository = productRepository;
        this.reportConverter = reportConverter;

        h1 = new Font();
        h1.setSize(22);
        h1.setStyle(Font.BOLD);

        h2 = new Font();
        h2.setSize(18);
        h2.setStyle(Font.BOLD);

        h3 = new Font();
        h3.setSize(16);
        h3.setStyle(Font.BOLD);
    }

    @Override
    public void create(final @NonNull ReportDTO reportDTO) {
        final ReportEntity entity = reportRepository.save(reportConverter.toNewEntity(reportDTO));
        final List<CompanyEntity> companies = companyRepository.findAll();
        final List<ProductEntity> products = productRepository.findAll();

        Document document = new Document();
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final PdfWriter instance = PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();
        instance.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));

        companies.forEach(company -> addCompanyPage(document, company, products));

        document.close();
        final byte[] content = byteArrayOutputStream.toByteArray();
        entity.setPdfContent(content);
        entity.setStatus(ReportStatus.READY.name());

        reportRepository.save(entity);
    }

    private void addCompanyPage(
            final Document document,
            final CompanyEntity company,
            final List<ProductEntity> products
    ) {
        addProducts(document, company, products);
        addSummary(document, company, products);
        document.newPage();
    }

    private void addProducts(
            final Document document,
            final CompanyEntity company,
            final List<ProductEntity> products
    ) {
        document.add(new Paragraph(company.getName(), h1));
        document.add(new Paragraph("Products", h2));
        document.add(new Paragraph(" ", h2));

        final PdfPTable table = new PdfPTable(3);
        table.addCell(new Paragraph("ID", h3));
        table.addCell(new Paragraph("Name", h3));
        table.addCell(new Paragraph("Amount", h3));

        products.stream()
                .filter(product -> Objects.nonNull(product.getCompany()))
                .filter(product -> Objects.equals(product.getCompany().getId(), company.getId()))
                .forEach(product -> {
                    table.addCell(String.valueOf(product.getId()));
                    table.addCell(product.getName());
                    table.addCell(String.valueOf(product.getAmount()));
                });
        document.add(table);
    }

    private void addSummary(
            final Document document,
            final CompanyEntity company,
            final List<ProductEntity> products
    ) {
        document.add(new Paragraph("Summary", h2));
        final long numberOfProducts = products.stream()
                .filter(product -> Objects.nonNull(product.getCompany()))
                .filter(product -> Objects.equals(product.getCompany().getId(), company.getId()))
                .count();

        final int summedAmounts = products.stream()
                .filter(product -> Objects.nonNull(product.getCompany()))
                .filter(product -> Objects.equals(product.getCompany().getId(), company.getId()))
                .mapToInt(ProductEntity::getAmount)
                .sum();

        document.add(new Paragraph("Products: \t" + numberOfProducts));
        document.add(new Paragraph("SummedAmounts: \t" + summedAmounts));
    }

    @Override
    public List<ReportInfoDTO> getAll() {
        return reportRepository.findAll().stream().map(reportConverter::toInfoDTO).collect(Collectors.toList());
    }

    @Override
    public ReportDTO getById(final @NonNull Long id) {
        return reportRepository.findById(id)
                .map(reportConverter::toDTO)
                .orElseThrow(() -> new NotFoundReportException(String.format("Not found report with id %s.", id)));
    }

    @Override
    public void update(final @NonNull ReportDTO reportDTO) {
        final ReportEntity reportEntity = reportRepository.findById(reportDTO.getId())
                .orElseThrow(() ->
                        new NotFoundReportException(String.format("Not found report with id %s.", reportDTO.getId()))
                );
        reportEntity.setName(reportDTO.getName());
        reportRepository.save(reportEntity);
    }

    @Override
    public void deleteById(final @NonNull Long id) {
        if (!reportRepository.existsById(id)) {
            throw new NotFoundReportException(String.format("Not found report with id %s.", id));
        }

        reportRepository.deleteById(id);
    }
}
