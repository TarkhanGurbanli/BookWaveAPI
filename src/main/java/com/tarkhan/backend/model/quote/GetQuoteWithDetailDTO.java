package com.tarkhan.backend.model.quote;

import lombok.Data;

@Data
public class GetQuoteWithDetailDTO {
    private Long id;
    private String text;
    private String  bookName;
    private String authorName;
}
