package com.tarkhan.backend.model.author;

import lombok.Data;

@Data
public class AuthorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String bio;
    private String imageUrl;
}
