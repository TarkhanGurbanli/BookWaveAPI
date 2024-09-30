package com.tarkhan.backend.model.auth;

import lombok.Data;

import java.util.List;

@Data
public class JWTModel {
    private List<String> roles;
    private Long userId;
}
