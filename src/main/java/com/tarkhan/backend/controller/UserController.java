package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.auth.AuthResponse;
import com.tarkhan.backend.model.user.ChangePasswordDTO;
import com.tarkhan.backend.model.user.ChangeUsernameDTO;
import com.tarkhan.backend.model.user.UploadImageDTO;
import com.tarkhan.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new author")
    public ResponseEntity<ResponseModel> uploadImage(
            @RequestHeader("Authorization") String token,
            @ModelAttribute UploadImageDTO dto
    ) throws IOException {
        userService.uploadImage(token, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<AuthResponse> changePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody ChangePasswordDTO request
    ) {
        userService.changePassword(token, request);
        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(
                Constants.STATUS_NO_CONTENT,
                Constants.MESSAGE_UPDATE_SUCCESSFUL,
                token
        ));
    }

    @PatchMapping("/changeUsername")
    public ResponseEntity<AuthResponse> changeUsername(
            @RequestHeader("Authorization") String token,
            @RequestBody ChangeUsernameDTO request
    ) {
        userService.changeUsername(token, request);
        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(
                Constants.STATUS_NO_CONTENT,
                Constants.MESSAGE_UPDATE_SUCCESSFUL,
                token
        ));
    }
}
