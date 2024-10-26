package com.tarkhan.backend.model.readBook;

import lombok.Data;

@Data
public class UpdateReadBookDTO {
    private Long userId;
    private Long bookId;
}
