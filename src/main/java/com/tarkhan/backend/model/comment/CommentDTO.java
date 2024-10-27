package com.tarkhan.backend.model.comment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    private String text;
    private LocalDateTime createdAt;
    private Long bookId;
    private Long blogId;
}