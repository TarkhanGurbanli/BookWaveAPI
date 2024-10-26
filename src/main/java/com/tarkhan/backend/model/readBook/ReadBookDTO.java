package com.tarkhan.backend.model.readBook;

import lombok.Data;

@Data
public class ReadBookDTO {
    private Long id;
    private Long userId;
    private Long bookId;
}
