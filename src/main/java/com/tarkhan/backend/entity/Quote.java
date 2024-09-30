package com.tarkhan.backend.entity;

import com.tarkhan.backend.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "quotes")
public class Quote extends BaseEntity {

    @NotBlank(message = "Quote text cannot be empty")
    @Size(max = 500, message = "Quote text cannot exceed 500 characters")
    @Column(nullable = false, length = 500)
    private String text;

    @NotNull(message = "Book cannot be null")
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @NotNull(message = "Author cannot be null")
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
}
