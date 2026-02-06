package com.example.skinsaathi.controller;
import java.util.Optional;

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
import com.example.skinsaathi.repository.UserRepository;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final UserRepository userRepository;

    @GetMapping("/getProfile")
    public ProfileResponse getProfile(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return profileService.getProfile(userId);
    }

    @PutMapping(value = "/updateProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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

    @DeleteMapping("/delete/profileImage")
    public ResponseEntity<?> removeProfileImage(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = optionalUser.get();
        user.setImageData(null);
        userRepository.save(user);

        return ResponseEntity.ok("Profile picture removed successfully");
    }

}
