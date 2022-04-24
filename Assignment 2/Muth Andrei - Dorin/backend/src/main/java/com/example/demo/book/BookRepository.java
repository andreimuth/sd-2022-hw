package com.example.demo.book;

import com.example.demo.book.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByQuantityLessThan(Long quantity);
    Page<Book> findAllByTitleLikeOrAuthorLikeOrGenreLike(String title, String author, String genre, Pageable pageable);

    Page<Book> findAll(Pageable pageable);
}
