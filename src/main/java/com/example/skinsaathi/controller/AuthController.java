package com.example.skinsaathi.controller;

import com.example.skinsaathi.dto.AuthResponse;
import com.example.skinsaathi.dto.SendOtpRequest;
import com.example.skinsaathi.dto.VerifyOtpRequest;
import com.example.skinsaathi.dto.GoogleAppleLoginRequest;
import com.example.skinsaathi.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final OtpService otpService;
    private final AuthService authService;

    @PostMapping("/send-otp")
    public ResponseEntity<Void> sendOtp(
            @RequestBody @Valid SendOtpRequest request
    ) {
        otpService.sendOtp(request.getMobile());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponse> verifyOtp(
            @RequestBody @Valid VerifyOtpRequest request
    ) {
        AuthResponse response = otpService.verifyOtp(
                request.getMobile(),
                request.getOtp(),
                request.getName(),
                request.getEmail()
        );
        return ResponseEntity.ok(response);
    }

    // @CrossOrigin(origins = "*")
    @PostMapping("/google")
    public AuthResponse googleLogin(@RequestBody @Valid GoogleAppleLoginRequest request) {
        return authService.loginWithGoogle(request.getIdToken());
    }

    @PostMapping("/apple")
    public AuthResponse appleLogin(@RequestBody @Valid GoogleAppleLoginRequest request) {
        System.out.println(request.getIdToken());
        return authService.appleLogin(request.getIdToken());
        // return ResponseEntity.ok(new AuthResponse(jwt));
    }

}
