package com.tarkhan.backend.model.quote;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateQuoteDTO {
    @NotBlank(message = "Quote text cannot be empty")
    @Size(max = 500, message = "Quote text cannot exceed 500 characters")
    private String text;

    @NotNull(message = "Book cannot be null")
    private Long bookId;

    @NotNull(message = "Author cannot be null")
    private Long authorId;
}
