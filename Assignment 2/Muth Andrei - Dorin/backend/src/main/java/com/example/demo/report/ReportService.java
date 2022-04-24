package com.example.demo.report;

import com.example.demo.book.model.Book;

import java.util.List;

public interface ReportService {
    String generateReport(List<Book> books);
    ReportType getType();
}
