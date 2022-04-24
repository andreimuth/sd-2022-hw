package com.example.demo.book;

import com.example.demo.book.dto.BookDto;
import com.example.demo.book.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void beforeEach() {
        bookRepository.deleteAll();
    }

    @Test
    void findAll() {
        Book book1 = Book.builder()
                .title("Harry Potter").author("J. K. Rowling").genre("wizards/fantasy").price(10F).quantity(10L).build();
        Book book2 = Book.builder()
                .title("Lord of the Rings").author("J. R. R. Tolkien").genre("fantasy").price(10F).quantity(10L).build();
        Book book3 = Book.builder()
                .title("Title").author("Someone").genre("whatever").price(10F).quantity(10L).build();
        bookRepository.saveAll(List.of(book1, book2, book3));

        List<BookDto> all = bookService.allBooksDto();
        assertEquals(3, all.size());
    }

}
