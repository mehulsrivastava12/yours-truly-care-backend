package com.example.skinsaathi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    // User can login via email OR mobile
    private String email;

    private String mobile;

    @NotBlank(message = "Password is required")
    private String password;
}
