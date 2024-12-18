package com.tarkhan.backend.model.book;

import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String description;
    private Double rating;
    private Long imageId;
    private Long authorId;
    private String authorName;
    private Long publisherId;
    private String publisherName;
    private Long genreId;
    private String genreName;
}
