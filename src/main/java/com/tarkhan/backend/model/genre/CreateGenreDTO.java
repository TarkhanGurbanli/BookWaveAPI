package com.tarkhan.backend.model.genre;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateGenreDTO {
    @NotBlank(message = "Genre name cannot be empty")
    @Size(max = 100, message = "Genre name cannot exceed 100 characters")
    private String name;
}