package com.example.skinsaathi.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import com.example.skinsaathi.dto.ProfileResponse;
import com.example.skinsaathi.entity.User;
import com.example.skinsaathi.repository.UserRepository;
import com.example.skinsaathi.service.ProfileService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.skinsaathi.dto.ProfileUpdateRequest;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    @Override
    public ProfileResponse getProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .dob(user.getDob() != null ? user.getDob().toString() : null)
                .gender(user.getGender())
                .imageData(user.getImageData() != null ? Base64.getEncoder().encodeToString(user.getImageData()) : null)
                .build();
    }

    @Override
    public void updateProfile(Long userId, ProfileUpdateRequest req, MultipartFile image) {

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (req.getName() != null) user.setName(req.getName());
        if (req.getMobile() != null) user.setMobile(req.getMobile());
        if (req.getGender() != null) user.setGender(req.getGender());

        if (req.getDob() != null) {
            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            user.setDob(LocalDate.parse(req.getDob(), f));
        }

        if (image != null && !image.isEmpty()) {
            try {
                user.setImageData(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to save image");
            }
        }

        userRepository.save(user);
    }

}
