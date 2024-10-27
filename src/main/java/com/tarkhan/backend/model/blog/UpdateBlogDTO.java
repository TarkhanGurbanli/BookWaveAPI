package com.tarkhan.backend.model.blog;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UpdateBlogDTO {
    private String title;
    private String content;
    private List<MultipartFile> images;
}
