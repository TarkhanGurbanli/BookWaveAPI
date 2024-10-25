package com.tarkhan.backend.service.impl;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.entity.Image;
import com.tarkhan.backend.entity.User;
import com.tarkhan.backend.entity.enums.ImageType;
import com.tarkhan.backend.exception.BookWaveApiException;
import com.tarkhan.backend.exception.ResourceNotFoundException;
import com.tarkhan.backend.model.auth.AuthResponse;
import com.tarkhan.backend.model.auth.JWTModel;
import com.tarkhan.backend.model.user.ChangePasswordDTO;
import com.tarkhan.backend.model.user.ChangeUsernameDTO;
import com.tarkhan.backend.model.user.UploadImageDTO;
import com.tarkhan.backend.repository.UserRepository;
import com.tarkhan.backend.service.UserService;
import com.tarkhan.backend.service.auth.JwtService;
import com.tarkhan.backend.service.auth.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ImageServiceImpl imageService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JwtService jwtService;

    @Override
    public void changePassword(String token, ChangePasswordDTO request) {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "ID", userId));

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            request.getOldPassword()
                    )
            );

            if (request.getNewPassword() != null && !request.getNewPassword().isEmpty()) {
                String hashedPassword = passwordEncoder.encode(request.getNewPassword());
                user.setPassword(hashedPassword);
            }

            userRepository.save(user);

            jwtService.generateToken(user);

        } catch (Exception e) {
            throw new BookWaveApiException("Error : " + e.getMessage());
        }
    }

    @Override
    public void changeUsername(String token, ChangeUsernameDTO request) {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "ID", userId));

            if (request.getNewUsername() != null && !request.getNewUsername().isEmpty()) {
                user.setUsername(request.getNewUsername());
            } else {
                throw new BookWaveApiException("New username cannot be empty");
            }

            userRepository.save(user);

            jwtService.generateToken(user);

        } catch (Exception e) {
            throw new BookWaveApiException("Error changing username: " + e.getMessage());
        }
    }

    @Override
    public void uploadImage(String token, UploadImageDTO dto) throws IOException {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "ID", userId));

            if (dto.getImage() != null && !dto.getImage().isEmpty()) {
                File tempFile = File.createTempFile("user-", dto.getImage().getOriginalFilename());
                dto.getImage().transferTo(tempFile);

                Image profileImage = imageService.uploadImageToDrive(tempFile, ImageType.USER);

                user.setProfileImage(profileImage);

                tempFile.delete();
            }

            userRepository.save(user);
        } catch (Exception e) {
            throw new BookWaveApiException("Error getting profile image: " + e.getMessage());
        }
    }
}
