package com.tarkhan.backend.entity;

import com.tarkhan.backend.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "publishers")
public class Publisher extends BaseEntity {

    @NotBlank(message = "Publisher name cannot be empty")
    @Size(max = 150, message = "Publisher name cannot exceed 150 characters")
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Book> books;
}
