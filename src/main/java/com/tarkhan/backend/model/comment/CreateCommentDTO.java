package com.tarkhan.backend.model.comment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateCommentDTO {
    private String text;
    private LocalDateTime createdAt;
    private Long bookId;
    private Long blogId;
}
