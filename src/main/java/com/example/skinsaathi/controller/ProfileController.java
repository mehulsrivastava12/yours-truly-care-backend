package com.example.skinsaathi.controller;

import com.example.skinsaathi.dto.ProfileResponse;
import com.example.skinsaathi.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profile")
    public ProfileResponse getProfile(Authentication authentication) {
        // 🔐 User ID comes ONLY from JWT
        Long userId = (Long) authentication.getPrincipal();
        return profileService.getProfile(userId);
    }
}
