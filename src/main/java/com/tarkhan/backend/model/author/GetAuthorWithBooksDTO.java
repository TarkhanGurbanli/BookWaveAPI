package com.tarkhan.backend.model.author;

import com.tarkhan.backend.model.book.BookDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GetAuthorWithBooksDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String nationality;
    private String biography;
    private Long imageId;
    private List<BookDTO> books;
}
