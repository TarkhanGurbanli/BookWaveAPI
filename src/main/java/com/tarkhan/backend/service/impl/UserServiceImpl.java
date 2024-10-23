package com.tarkhan.backend.service.impl;

import com.tarkhan.backend.entity.Image;
import com.tarkhan.backend.entity.User;
import com.tarkhan.backend.entity.enums.ImageType;
import com.tarkhan.backend.exception.BookWaveApiException;
import com.tarkhan.backend.exception.ResourceNotFoundException;
import com.tarkhan.backend.mapping.AuthorMapping;
import com.tarkhan.backend.model.auth.JWTModel;
import com.tarkhan.backend.model.user.UploadImageDTO;
import com.tarkhan.backend.repository.UserRepository;
import com.tarkhan.backend.service.UserService;
import com.tarkhan.backend.service.auth.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final JwtUtil jwtUtil;

    @Override
    public void uploadImage(String token, UploadImageDTO dto) throws IOException {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "ID", userId));

            User user = modelMapper.map(dto, User.class);
            if (dto.getImage() != null && !dto.getImage().isEmpty()) {
                File tempFile = File.createTempFile("user-", dto.getImage().getOriginalFilename());
                dto.getImage().transferTo(tempFile);

                Image profileImage = imageService.uploadImageToDrive(tempFile, ImageType.AUTHOR);

                user.setProfileImage(profileImage);

                tempFile.delete();
            }
            userRepository.save(user);
        } catch (Exception e) {
            throw new BookWaveApiException("Error getting profile image: " + e.getMessage());
        }
    }
}
