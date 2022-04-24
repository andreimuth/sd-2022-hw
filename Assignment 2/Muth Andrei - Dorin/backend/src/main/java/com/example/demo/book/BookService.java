package com.example.demo.book;

import com.example.demo.book.dto.BookDto;
import com.example.demo.book.dto.BookSaleDto;
import com.example.demo.book.mapper.BookMapper;
import com.example.demo.book.model.Book;
import com.example.demo.report.ReportServiceFactory;
import com.example.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final ReportServiceFactory reportServiceFactory;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book with id = " + id + " not found"));
    }

    public List<BookDto> allBooksDto() {
        return bookRepository.findAll(PageRequest.of(0, 10))
                .stream().map(bookMapper::mapToBookDto)
                .collect(Collectors.toList());
    }

    public BookDto create(BookDto book) {
        return bookMapper.mapToBookDto(bookRepository.save(bookMapper.fromDto(book)));
    }

    public BookDto update(Long id, BookDto book) {
        Book actualBook = findById(id);
        actualBook.setTitle(book.getTitle());
        actualBook.setAuthor(book.getAuthor());
        actualBook.setGenre(book.getGenre());
        actualBook.setPrice(book.getPrice());
        actualBook.setQuantity(book.getQuantity());
        return bookMapper.mapToBookDto(bookRepository.save(actualBook));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> findAllByQuantityLessThan(Long quantity) {
        return bookRepository.findAllByQuantityLessThan(quantity);
    }

    public String generateReport(ReportType type) {
        return reportServiceFactory.getReportService(type).generateReport(findAllByQuantityLessThan(1L));
    }

    public List<BookDto> filterBooks(String word) {
        String wordLike = "%" + word + "%";
        return bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike(wordLike, wordLike, wordLike,
                        PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "title")))
                .stream().map(bookMapper::mapToBookDto).collect(Collectors.toList());
    }

    public void sellBooks(BookSaleDto bookSaleDto) {
        Book book = findById(bookSaleDto.getId());
        book.setQuantity(book.getQuantity() - bookSaleDto.getQuantity());
        bookRepository.save(book);
    }

    public Long getQuantityById(Long id) {
        return findById(id).getQuantity();
    }

}
