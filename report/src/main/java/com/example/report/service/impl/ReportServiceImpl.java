package com.example.report.service.impl;

import com.example.report.remote.company.interfaces.CompanyRemoteService;
import com.example.report.remote.company.pojo.response.CompanyResponse;
import com.example.report.remote.product.interfaces.ProductRemoteService;
import com.example.report.remote.product.response.ProductResponse;
import com.example.report.repository.entity.ReportEntity;
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
    private final ReportConverter reportConverter;
    private final CompanyRemoteService companyRemoteService;
    private final ProductRemoteService productRemoteService;
    private final Font h1;
    private final Font h2;
    private final Font h3;

    public ReportServiceImpl(
            final ReportRepository reportRepository,
            final ReportConverter reportConverter,
            final CompanyRemoteService companyRemoteService,
            final ProductRemoteService productRemoteService
    ) {
        this.reportRepository = reportRepository;
        this.reportConverter = reportConverter;
        this.companyRemoteService = companyRemoteService;
        this.productRemoteService = productRemoteService;

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
        final List<CompanyResponse> companies = companyRemoteService.getAll();
        final List<ProductResponse> products = productRemoteService.getAll();

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
            final CompanyResponse company,
            final List<ProductResponse> products
    ) {
        addProducts(document, company, products);
        addSummary(document, company, products);
        document.newPage();
    }

    private void addProducts(
            final Document document,
            final CompanyResponse company,
            final List<ProductResponse> products
    ) {
        document.add(new Paragraph(company.getName(), h1));
        document.add(new Paragraph("Products", h2));
        document.add(new Paragraph(" ", h2));

        final PdfPTable table = new PdfPTable(3);
        table.addCell(new Paragraph("ID", h3));
        table.addCell(new Paragraph("Name", h3));
        table.addCell(new Paragraph("Amount", h3));

        products.stream()
                .filter(product -> Objects.equals(product.getCompanyId(), company.getId()))
                .forEach(product -> {
                    table.addCell(String.valueOf(product.getId()));
                    table.addCell(product.getName());
                    table.addCell(String.valueOf(product.getAmount()));
                });
        document.add(table);
    }

    private void addSummary(
            final Document document,
            final CompanyResponse company,
            final List<ProductResponse> products
    ) {
        document.add(new Paragraph("Summary", h2));
        final long numberOfProducts = products.stream()
                .filter(product -> Objects.equals(product.getCompanyId(), company.getId()))
                .count();

        final int summedAmounts = products.stream()
                .filter(product -> Objects.equals(product.getCompanyId(), company.getId()))
                .mapToInt(ProductResponse::getAmount)
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
