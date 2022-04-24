package com.example.demo.report;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.demo.report.ReportType.CSV;
import static com.example.demo.report.ReportType.PDF;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReportServiceFactoryTest {

    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Test
    void getReportService() {
        ReportService csvReportService = reportServiceFactory.getReportService(CSV);
        assertEquals("CSV report generated", csvReportService.generateReport(List.of()));

        ReportService pdfReportService = reportServiceFactory.getReportService(PDF);
        assertEquals("PDF report generated", pdfReportService.generateReport(List.of()));
    }

}
