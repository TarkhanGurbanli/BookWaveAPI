package com.tarkhan.backend.model.book;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class UpdateBookDTO {
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 10, message = "Rating must not exceed 5")
    private Double rating;

    private MultipartFile image;
    private Long authorId;
    private Long publisherId;

    @NotNull(message = "Genre cannot be null")
    private Long genreId;
}
