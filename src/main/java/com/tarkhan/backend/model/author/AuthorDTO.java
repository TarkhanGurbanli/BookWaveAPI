package com.tarkhan.backend.model.author;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private String nationality;
    private String biography;
    private Long imageId;
}
