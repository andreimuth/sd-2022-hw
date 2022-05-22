package com.example.demo.pdf;

import com.example.demo.app.model.App;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.stream.Stream;

@Service
public class PDFGenerator {

    private final ObjectToPDFTextMapper<App> mapper = new ObjectToPDFTextMapper<>(App.class);
    private final PDFRenderingSimple renderer = new PDFRenderingSimple();
    private final File file = new File("application.pdf");

    public File exportPDF(App app) {
        PDDocument document = new PDDocument();
        try {
            renderer.setDocument(document);
            Stream<String> lines = mapper.mapToText(app).lines();
            for(String line : lines.toArray(String[]::new)) {
                renderer.renderText(line, 25);
            }
            //renderer.renderText(mapper.mapToText(app), 25);
            renderer.close();
            document.save(file);
            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error while creating PDF document", e);
        }
        return file;
    }

}
