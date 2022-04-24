package com.example.demo.book;

import com.example.demo.book.dto.BookDto;
import com.example.demo.book.mapper.BookMapper;
import com.example.demo.book.model.Book;
import com.example.demo.report.ReportServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository, bookMapper, reportServiceFactory);
    }

    @Test
    void findAll() {
        when(bookRepository.findAll(PageRequest.of(0, 10))).thenReturn(Page.empty());

        List<BookDto> all = bookService.allBooksDto();
        assertEquals(0, all.size());
    }

}
