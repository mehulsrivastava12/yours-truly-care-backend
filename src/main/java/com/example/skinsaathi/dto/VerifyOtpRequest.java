package com.example.skinsaathi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpRequest {

    @NotBlank
    private String mobile;

    @NotBlank
    private String otp;

    private String name;
    private String email;
}
