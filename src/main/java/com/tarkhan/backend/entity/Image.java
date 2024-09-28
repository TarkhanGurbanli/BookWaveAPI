package com.tarkhan.backend.entity;

import com.tarkhan.backend.entity.base.BaseEntity;
import com.tarkhan.backend.entity.enums.ImageType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "images")
public class Image extends BaseEntity {
    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileUrl;

    @Column(nullable = false)
    private String imageUrl;

    private int status;
    private String message;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;
    private LocalDateTime uploadedAt;
}
