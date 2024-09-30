package com.tarkhan.backend.model.book;

import com.tarkhan.backend.model.author.AuthorDTO;
import com.tarkhan.backend.model.genre.GenreDTO;
import com.tarkhan.backend.model.publisher.PublisherDTO;
import lombok.Data;

@Data
public class GetBookWithDetailsDTO {
    private Long id;
    private String title;
    private String description;
    private Double rating;
    private Long imageId;
    private AuthorDTO author;
    private GenreDTO genre;
    private PublisherDTO publisher;
}