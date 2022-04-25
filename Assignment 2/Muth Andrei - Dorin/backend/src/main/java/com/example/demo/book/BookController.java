package com.example.demo.book;

import com.example.demo.book.dto.BookDto;
import com.example.demo.book.dto.BookSaleDto;
import com.example.demo.book.model.Book;
import com.example.demo.report.ReportType;
import com.example.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping(FIND_ALL)
    public List<BookDto> allItems() {
        return bookService.allBooksDto();
    }

    @GetMapping(ID)
    public Book findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping(ADD_BOOK)
    public BookDto create(@Valid @RequestBody BookDto book) {
        return bookService.create(book);
    }

    @PutMapping(UPDATE_BOOK)
    public BookDto update(@PathVariable Long id, @Valid @RequestBody BookDto book) {
        return bookService.update(id, book);
    }

    @DeleteMapping(DELETE_BOOK)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping(GENERATE_REPORT)
    public String generateReport(@PathVariable ReportType type) {
        return bookService.generateReport(type);
    }

    @GetMapping(FILTER_BOOKS)
    public List<BookDto> filterBooks(@PathVariable String word) {
        return bookService.filterBooks(word);
    }

    @PatchMapping(SELL_BOOKS)
    public ResponseEntity<MessageResponse> sellBooks(@PathVariable Long id, @RequestBody BookSaleDto bookSaleDto) {
        Long quantity = bookService.getQuantityById(id);
        if (quantity < bookSaleDto.getQuantity()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Not enough books in stock"));
        }
        bookService.sellBooks(bookSaleDto);
        return ResponseEntity.ok(new MessageResponse("Successfully sold"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage()));
    }

}
