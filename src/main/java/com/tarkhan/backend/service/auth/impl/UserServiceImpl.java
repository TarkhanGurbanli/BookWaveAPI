package com.tarkhan.backend.service.auth.impl;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.entity.User;
import com.tarkhan.backend.entity.enums.Role;
import com.tarkhan.backend.exception.ResourceNotFoundException;
import com.tarkhan.backend.model.auth.AuthResponse;
import com.tarkhan.backend.model.auth.user.LoginDTO;
import com.tarkhan.backend.model.auth.user.RegisterDTO;
import com.tarkhan.backend.repository.UserRepository;
import com.tarkhan.backend.service.auth.JWTService;
import com.tarkhan.backend.service.auth.UserService;
import com.tarkhan.backend.service.auth.util.JWTUtil;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;

    @Override
    public AuthResponse register(RegisterDTO request) {
        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRole(Role.USER);

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        user = userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponse(Constants.STATUS_CREATED, Constants.MESSAGE_REGISTER_SUCCESSFUL, token);
    }

    @Override
    public AuthResponse login(LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Email", "Email", request.getUsername()));

        String token = jwtService.generateToken(user);
        return new AuthResponse(Constants.STATUS_NO_CONTENT, Constants.MESSAGE_LOGIN_SUCCESSFUL, token);
    }
}
