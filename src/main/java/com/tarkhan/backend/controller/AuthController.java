package com.tarkhan.backend.controller;

import com.tarkhan.backend.entity.User;
import com.tarkhan.backend.model.auth.AuthResponse;
import com.tarkhan.backend.model.auth.user.LoginDTO;
import com.tarkhan.backend.model.auth.user.RegisterDTO;
import com.tarkhan.backend.service.auth.AuthService;
import com.tarkhan.backend.service.auth.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterDTO request
    ) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginDTO request
    ) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/refresh-token")
    public ResponseEntity<AuthResponse> refreshAccessToken(
            @RequestBody String refreshToken)
    {
        AuthResponse response = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/toggle-role/{userId}")
    public ResponseEntity<AuthResponse> toggleUserRole(
            @PathVariable Long userId
    ) {
        AuthResponse response = authService.toggleUserRole(userId);
        return ResponseEntity.ok(response);
    }
}
