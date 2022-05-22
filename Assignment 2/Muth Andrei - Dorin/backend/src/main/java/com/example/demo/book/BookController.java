package com.example.demo.book;

import com.example.demo.book.dto.BookDto;
import com.example.demo.book.dto.BookSaleDto;
import com.example.demo.book.model.Book;
import com.example.demo.report.ReportType;
import com.example.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.catalina.connector.Response;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import static com.example.demo.UrlMapping.*;
import static java.util.Base64.getEncoder;

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
    public void demo(HttpServletResponse response, @PathVariable String type) { // (1) Return byte array response
        File file = bookService.generateReport(ReportType.valueOf(type));
        System.out.println(file.getAbsolutePath());
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        try (InputStream inputStream = new FileInputStream(file)) {
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
