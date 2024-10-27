package com.tarkhan.backend.service.auth.impl;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.entity.User;
import com.tarkhan.backend.entity.enums.Role;
import com.tarkhan.backend.exception.BookWaveApiException;
import com.tarkhan.backend.exception.ResourceNotFoundException;
import com.tarkhan.backend.model.auth.AuthResponse;
import com.tarkhan.backend.model.auth.user.LoginDTO;
import com.tarkhan.backend.model.auth.user.RegisterDTO;
import com.tarkhan.backend.repository.UserRepository;
import com.tarkhan.backend.service.auth.AuthService;
import com.tarkhan.backend.service.auth.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    @Override
    public AuthResponse register(RegisterDTO request) {
        User user = modelMapper.map(request, User.class);
        user.setRole(Role.USER);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        user = userRepository.save(user);

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponse(Constants.STATUS_CREATED, Constants.MESSAGE_REGISTER_SUCCESSFUL, accessToken, refreshToken);
    }

    @Override
    public AuthResponse login(LoginDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new BookWaveApiException(
                    "Invalid username or password. Please check your credentials and try again.");
        }

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("Username", "Invalid username or password", request.getUsername()));

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponse(Constants.STATUS_OK, Constants.MESSAGE_LOGIN_SUCCESSFUL, accessToken, refreshToken);
    }

    @Override
    public AuthResponse refreshAccessToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Username not found", username));

        if (jwtService.isRefreshTokenValid(refreshToken, user)) {
            String newAccessToken = jwtService.generateToken(user);
            return new AuthResponse(Constants.STATUS_OK, "New access token generated.", newAccessToken, refreshToken);
        } else {
            throw new BookWaveApiException("Invalid refresh token.");
        }
    }
}
