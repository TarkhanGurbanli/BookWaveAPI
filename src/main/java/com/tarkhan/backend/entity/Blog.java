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

    @PastOrPresent(message = "Created date must be in the past or present")
    private LocalDateTime createdAt;

    private String imageUrl;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @NotNull(message = "Author cannot be null")
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
}
