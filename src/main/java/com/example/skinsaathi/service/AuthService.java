package com.example.skinsaathi.service;

import com.example.skinsaathi.dto.AuthResponse;

public interface AuthService {

    // AuthResponse register(RegisterRequest request);

    // AuthResponse login(LoginRequest request);

    AuthResponse loginWithGoogle(String idToken);

    AuthResponse appleLogin(String idToken);
}
