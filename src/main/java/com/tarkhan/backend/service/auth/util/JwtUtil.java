package com.tarkhan.backend.service.auth.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarkhan.backend.model.auth.JWTModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final ObjectMapper objectMapper;

    public JWTModel decodeToken(String authHeader) throws Exception {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header.");
        }

        String token = authHeader.substring(7);
        String[] chunks = token.split("\\.");
        if (chunks.length != 3) {
            throw new IllegalArgumentException("Invalid JWT token.");
        }

        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        return objectMapper.readValue(payload, JWTModel.class);
    }
}
