package com.tarkhan.backend.model.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBooksByGenreDTO {
    private Long id;
    private String title;
    private String description;
    private Double rating;
    private Long imageId;
    private String authorName;
    private String publisherName;
    private String genreName;
}
