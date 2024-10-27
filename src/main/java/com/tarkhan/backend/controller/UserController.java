package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.auth.AuthResponse;
import com.tarkhan.backend.model.user.ChangePasswordDTO;
import com.tarkhan.backend.model.user.ChangeUsernameDTO;
import com.tarkhan.backend.model.user.UploadImageDTO;
import com.tarkhan.backend.model.user.UserDTO;
import com.tarkhan.backend.service.EmailSenderService;
import com.tarkhan.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
    public ResponseEntity<ResponseModel> changePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody ChangePasswordDTO request
    ) {
        userService.changePassword(token, request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(
                Constants.STATUS_NO_CONTENT,
                Constants.MESSAGE_UPDATE_SUCCESSFUL
        ));
    }

    @PatchMapping("/changeUsername")
    public ResponseEntity<ResponseModel> changeUsername(
            @RequestHeader("Authorization") String token,
            @RequestBody ChangeUsernameDTO request
    ) {
        userService.changeUsername(token, request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(
                Constants.STATUS_NO_CONTENT,
                Constants.MESSAGE_UPDATE_SUCCESSFUL
        ));
    }

    @GetMapping("/admin")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
