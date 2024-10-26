package com.tarkhan.backend.model.readBook;

import lombok.Data;

@Data
public class CreateReadBookDTO {
    private Long userId;
    private Long bookId;
}
