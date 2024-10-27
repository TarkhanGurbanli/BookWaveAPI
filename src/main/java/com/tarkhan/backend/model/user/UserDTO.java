package com.tarkhan.backend.model.user;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private Long imageId;
    private String role;
}