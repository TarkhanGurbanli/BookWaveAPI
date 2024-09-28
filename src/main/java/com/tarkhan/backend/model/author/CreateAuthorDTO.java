package com.tarkhan.backend.model.author;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CreateAuthorDTO {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private LocalDate birthDate;
    private LocalDate deathDate;
    @NotBlank(message = "Nationality cannot be empty")
    private String nationality;
    private String biography;
}

