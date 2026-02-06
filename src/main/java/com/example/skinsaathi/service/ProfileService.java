package com.example.skinsaathi.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.skinsaathi.dto.ProfileResponse;
import com.example.skinsaathi.dto.ProfileUpdateRequest;

public interface ProfileService {

    ProfileResponse getProfile(Long userId);
    void updateProfile(Long userId, ProfileUpdateRequest request, MultipartFile image);

}
