package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailSenderService emailSenderService;

    @PostMapping
    public ResponseEntity<ResponseModel> sendEmails(
            @RequestHeader("Authorization") String token,
            @RequestParam String subject,
            @RequestParam String body
    ) {
        emailSenderService.sendEmailToAll(token, subject, body);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(
                Constants.STATUS_OK,
                "Emails sent successfully."
        ));
    }
}
