package com.tarkhan.backend.entity;

import com.tarkhan.backend.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "blogs")
public class Blog extends BaseEntity {

    @NotBlank(message = "Title cannot be empty")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Content cannot be empty")
    @Size(max = 10000, message = "Content cannot exceed 10,000 characters")
    @Column(nullable = false, length = 10000)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ElementCollection
    @CollectionTable(name = "blog_image_ids", joinColumns = @JoinColumn(name = "blog_id"))
    @Column(name = "image_id")
    private List<Long> imageIds = new ArrayList<>();

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @NotNull(message = "User cannot be null")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
