package com.tarkhan.backend.service;

import com.tarkhan.backend.model.auth.AuthResponse;
import com.tarkhan.backend.model.user.ChangePasswordDTO;
import com.tarkhan.backend.model.user.ChangeUsernameDTO;
import com.tarkhan.backend.model.user.UploadImageDTO;

import java.io.IOException;

public interface UserService {
    void changePassword(String token, ChangePasswordDTO request);
    void changeUsername(String token, ChangeUsernameDTO request);
    void uploadImage(String token, UploadImageDTO dto) throws IOException;
}
