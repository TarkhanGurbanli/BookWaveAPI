package com.tarkhan.backend.model.user;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UploadImageDTO {
    private MultipartFile image;
}
