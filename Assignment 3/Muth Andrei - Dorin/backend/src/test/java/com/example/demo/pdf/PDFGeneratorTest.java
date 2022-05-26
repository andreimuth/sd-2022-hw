package com.example.demo.pdf;

import com.example.demo.TestCreationFactory;
import com.example.demo.app.model.App;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PDFGeneratorTest {

    @Autowired
    private PDFGenerator pdfGenerator;

    @Test
    void testExport() {
        App app = TestCreationFactory.newApp();
        File file = pdfGenerator.exportPDF(app);
        assertEquals(file.getName(), "application.pdf");
    }

}
