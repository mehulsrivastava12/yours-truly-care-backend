package com.example.skinsaathi.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(
        regexp = "^[6-9]\\d{9}$",
        message = "Invalid Indian mobile number"
    )
    private String mobile;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
