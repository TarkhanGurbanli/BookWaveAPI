package com.tarkhan.backend.model.blog;

import com.tarkhan.backend.model.comment.CommentDTO;
import lombok.Data;

import java.util.List;

@Data
public class BlogDTO {
    private Long id;
    private String title;
    private String content;
    private List<Long> imageIds;
    private List<CommentDTO> comments;
}