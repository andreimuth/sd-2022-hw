package com.example.demo.book.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BookDto {
    private Long id;
    @NotEmpty(message = "Book title is required")
    private String title;
    @NotEmpty(message = "Book author is required")
    private String author;
    @NotEmpty(message = "Book genre is required")
    private String genre;
    @NotNull(message = "Quantity cannot be null")
    private Long quantity;
    @NotNull(message = "Price cannot be null")
    private Float price;
}
