package com.example.demo.report.renderer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class PDFRenderingSimple implements AutoCloseable {

    private PDDocument doc;
    private PDPageContentStream content = null;
    private int textRenderingLineY = 0;

    public void renderText(String info, int marginWidth) throws IOException {
        if(content == null || textRenderingLineY < 12) {
            addNewPage();
        }
        textRenderingLineY -= 12;
        content.beginText();
        content.setFont(PDType1Font.TIMES_ROMAN, 10);
        content.newLineAtOffset(marginWidth, textRenderingLineY);
        content.showText(info);
        content.newLine();
        content.endText();
    }

    private void addNewPage() throws IOException {
        close();
        PDPage page = new PDPage();
        doc.addPage(page);
        content = new PDPageContentStream(doc, page);
        textRenderingLineY = 768;
    }

    @Override
    public void close() throws IOException {
        if (content != null) {
            content.close();
            content = null;
        }
    }

    public void setDocument(PDDocument doc) {
        this.doc = doc;
    }

}
