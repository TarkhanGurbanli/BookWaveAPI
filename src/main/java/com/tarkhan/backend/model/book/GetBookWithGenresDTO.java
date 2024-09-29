package com.tarkhan.backend.model.book;

import com.tarkhan.backend.model.genre.GenreDTO;
import lombok.Data;

import java.util.List;

@Data
public class GetBookWithGenresDTO {
    private Long id;
    private String title;
    private String description;
    private Double rating;
    private Long imageId;
    private GenreDTO genre;
}
