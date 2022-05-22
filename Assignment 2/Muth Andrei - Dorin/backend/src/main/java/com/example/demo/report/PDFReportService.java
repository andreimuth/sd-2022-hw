package com.example.demo.report;

import com.example.demo.book.model.Book;
import com.example.demo.report.mapper.ObjectToPdfTextMapper;
import com.example.demo.report.renderer.PDFRenderingSimple;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.example.demo.report.ReportType.PDF;

@Service
public class PDFReportService implements ReportService {

    private final ObjectToPdfTextMapper<Book> mapper = new ObjectToPdfTextMapper<>(Book.class);
    private final PDFRenderingSimple renderer = new PDFRenderingSimple();
    private final File file = new File("reports.pdf");

    @Override
    public File generateReport(List<Book> books) {

        PDDocument document = new PDDocument();

        try {
            renderer.setDocument(document);
            for(Book book : books) {
                renderer.renderText(mapper.mapToText(book), 25);
            }
            renderer.close();
            document.save(file);
            document.close();
        } catch (IOException e) {
            throw new RuntimeException("Error while generating PDF report");
        }
        return file;
    }

    @Override
    public ReportType getType() {
        return PDF;
    }
}
