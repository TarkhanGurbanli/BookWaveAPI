package com.tarkhan.backend.model.author;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class UpdateAuthorDTO {
    private String name;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private String nationality;
    private String biography;
}
