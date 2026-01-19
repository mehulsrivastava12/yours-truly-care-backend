package com.example.skinsaathi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class GoogleAppleLoginRequest {

    @NotBlank
    private String idToken;
}
