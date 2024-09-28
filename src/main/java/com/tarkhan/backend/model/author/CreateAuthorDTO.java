package com.tarkhan.backend.model.author;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@Builder
public class CreateAuthorDTO {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate deathDate;
    @NotBlank(message = "Nationality cannot be empty")
    private String nationality;
    private String biography;
    private MultipartFile image;
}

