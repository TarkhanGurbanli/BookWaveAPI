package com.tarkhan.backend.model.auth.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterDTO {
    @NotBlank(message = "First name must not be blank")
    private String username;

    @Email(message = "Please enter a valid email address.")
    @NotBlank(message = "Email must not be blank")
    private String email;

    @NotBlank(message = "Password must not be blank")
    private String password;
}
