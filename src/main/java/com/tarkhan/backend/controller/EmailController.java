package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.service.EmailSenderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/emails")
@RequiredArgsConstructor
@Tag(name = "Email Management")
public class EmailController {

    private final EmailSenderService emailSenderService;

    @PostMapping
    @Operation(summary = "Send emails",
            description = "Sends emails to all recipients with the provided subject and body.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Emails sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or authorization")
    })
    public ResponseEntity<ResponseModel> sendEmails(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @Parameter(description = "Subject of the email") @RequestParam String subject,
            @Parameter(description = "Body of the email") @RequestParam String body
    ) {
        emailSenderService.sendEmailToAll(token, subject, body);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(
                Constants.STATUS_OK,
                "Emails sent successfully."
        ));
    }
}
