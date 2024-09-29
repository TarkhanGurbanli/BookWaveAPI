package com.tarkhan.backend.model.publisher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePublisherDTO {
    @NotBlank(message = "Publisher name cannot be empty")
    @Size(max = 150, message = "Publisher name cannot exceed 150 characters")
    private String name;
}
