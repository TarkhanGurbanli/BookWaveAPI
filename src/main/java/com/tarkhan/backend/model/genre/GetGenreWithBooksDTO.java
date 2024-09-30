package com.tarkhan.backend.model.genre;

import com.tarkhan.backend.model.book.BookDTO;
import lombok.Data;

import java.util.List;

@Data
public class GetGenreWithBooksDTO {
    private Long id;
    private String name;
    private List<BookDTO> books;
}