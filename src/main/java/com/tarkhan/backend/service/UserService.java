package com.tarkhan.backend.service;

import com.tarkhan.backend.model.user.UploadImageDTO;

import java.io.IOException;

public interface UserService {
    void uploadImage(String token, UploadImageDTO dto) throws IOException;
}
