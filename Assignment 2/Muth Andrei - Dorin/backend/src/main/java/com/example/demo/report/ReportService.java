package com.example.demo.report;

import com.example.demo.book.model.Book;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

public interface ReportService {
    File generateReport(List<Book> books);
    ReportType getType();
}
