package com.tarkhan.backend.model.quote;

import lombok.Data;

@Data
public class QuoteDTO {
    private Long id;
    private String text;
    private Long bookId;
    private Long authorId;
}
