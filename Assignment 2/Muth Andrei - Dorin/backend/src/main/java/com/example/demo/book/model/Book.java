package com.example.demo.book.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    @Size(min = 1, max = 512, message = "Title must be between 1 and 512 characters")
    private String title;

    @Column(length = 512, nullable = false)
    @Size(min = 1, max = 512, message = "Author must be between 1 and 512 characters")
    private String author;

    @Column(length = 512, nullable = false)
    @Size(min = 1, max = 512, message = "Genre must be between 1 and 512 characters")
    private String genre;

    @Column(nullable = false)
    @Min(value = 0,message = "Quantity must be greater than 0")
    private Long quantity;

    @Column(nullable = false)
    @Min(value = 0,message = "Price must be greater than 0")
    private Float price;

}