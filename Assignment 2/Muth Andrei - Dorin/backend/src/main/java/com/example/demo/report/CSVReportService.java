package com.example.demo.report;

import com.example.demo.book.model.Book;
import com.example.demo.report.mapper.ObjectToCsvRowMapper;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.report.ReportType.*;

@Service
public class CSVReportService implements ReportService {

    private File file = new File("report.csv");
    private final ObjectToCsvRowMapper<Book> mapper = new ObjectToCsvRowMapper<>(Book.class);
    @Override
    public String generateReport(List<Book> books) {
        try {
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile);
            String[] header = mapper.getHeader();
            writer.writeNext(header);
            List<String[]> data = new ArrayList<>();
            for(Book book : books) {
                data.add(mapper.mapToCsvRow(book));
            }
            writer.writeAll(data);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Error while generating CSV report");
        }
        return "CSV report generated";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }

}
