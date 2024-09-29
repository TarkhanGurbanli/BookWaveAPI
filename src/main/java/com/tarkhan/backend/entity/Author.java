package com.tarkhan.backend.entity;

import com.tarkhan.backend.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "authors")
public class Author extends BaseEntity {

    @NotBlank(message = "Name cannot be empty")
    @Column(nullable = false)
    private String name;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Nationality cannot be empty")
    private String nationality;

    @Size(max = 1500, message = "Biography cannot exceed 1500 characters")
    @Column(length = 1500)
    private String biography;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image profileImage;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;
}
