package com.example.skinsaathi.service;

import com.example.skinsaathi.dto.AuthResponse;

public interface OtpService {

    void sendOtp(String mobile);

    AuthResponse verifyOtp(
            String mobile,
            String otp,
            String name,
            String email
    );
}
