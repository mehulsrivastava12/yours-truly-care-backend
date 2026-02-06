package com.example.skinsaathi.controller;
import org.springframework.http.MediaType;

import com.example.skinsaathi.dto.ProfileResponse;
import com.example.skinsaathi.service.ProfileService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.multipart.MultipartFile;

import com.example.skinsaathi.dto.ProfileUpdateRequest;
import com.example.skinsaathi.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profile")
    public ProfileResponse getProfile(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return profileService.getProfile(userId);
    }

    @PutMapping(value = "/users/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfile(
            @RequestPart("data") String data,
            @RequestPart(value = "image", required = false) MultipartFile image,
            Authentication authentication
    ) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ProfileUpdateRequest request = mapper.readValue(data, ProfileUpdateRequest.class);
        Long userId = (Long) authentication.getPrincipal();
        profileService.updateProfile(userId, request, image);
        return ResponseEntity.ok("Profile updated");
    }

}
