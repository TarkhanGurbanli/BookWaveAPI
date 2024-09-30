package com.tarkhan.backend.service.auth;

import com.tarkhan.backend.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface JWTService {
    String extractUsername(String token);
    List<String> getRolesFromToken(String token);
    boolean isValid(String token, UserDetails user);
    String generateToken(User user);
}
