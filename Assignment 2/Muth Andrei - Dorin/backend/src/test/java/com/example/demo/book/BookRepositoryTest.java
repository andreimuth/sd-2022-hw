package com.example.demo.book;

import com.example.demo.book.dto.BookDto;
import com.example.demo.book.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        Book bookSaved = repository.save(Book.builder()
                .title("title").author("author").genre("genre").price(10F).quantity(10L).build());

        assertNotNull(bookSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Book.builder().build());
        });
    }

    @Test
    public void testFindAll() {
        Book book1 = Book.builder()
                .title("title").author("author").genre("genre").price(10F).quantity(10L).build();
        Book book2 = Book.builder()
                .title("title1").author("author1").genre("genre1").price(10F).quantity(10L).build();
        repository.saveAll(List.of(book1, book2));
        List<Book> all = repository.findAll();
        assertEquals(2, all.size());
    }

    @Test
    public void testFilterAndPagination() {
        Book book1 = Book.builder()
                .title("Harry Potter").author("J. K. Rowling").genre("wizards/fantasy").price(10F).quantity(10L).build();
        Book book2 = Book.builder()
                .title("Lord of the Rings").author("J. R. R. Tolkien").genre("fantasy").price(10F).quantity(10L).build();
        Book book3 = Book.builder()
                .title("Title").author("Someone").genre("whatever").price(10F).quantity(10L).build();
        repository.saveAll(List.of(book1, book2, book3));
        Page<Book> fantasyBooks = repository.findAllByTitleLikeOrAuthorLikeOrGenreLike
                ("something", "something", "%fantasy%", PageRequest.of(0, 1));
        assertTrue(fantasyBooks.hasContent());
        assertEquals(2, fantasyBooks.getTotalElements());
        assertEquals(2, fantasyBooks.getTotalPages());
        assertEquals(0, fantasyBooks.getNumber());
        assertEquals(1, fantasyBooks.getSize());
    }

}
