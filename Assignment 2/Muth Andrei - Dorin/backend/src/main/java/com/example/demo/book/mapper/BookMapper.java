package com.example.demo.book.mapper;

import com.example.demo.book.dto.BookDto;
import com.example.demo.book.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {


    BookDto mapToBookDto(Book book);

    Book fromDto(BookDto bookDto);

}
