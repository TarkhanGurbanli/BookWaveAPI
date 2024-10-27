package com.tarkhan.backend.service.auth;

import com.tarkhan.backend.model.auth.AuthResponse;
import com.tarkhan.backend.model.auth.user.LoginDTO;
import com.tarkhan.backend.model.auth.user.RegisterDTO;

public interface AuthService {
    AuthResponse register(RegisterDTO request);
    AuthResponse login(LoginDTO request);
    AuthResponse refreshAccessToken(String refreshToken);
}
