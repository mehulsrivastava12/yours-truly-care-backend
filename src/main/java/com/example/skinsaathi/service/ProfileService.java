package com.example.skinsaathi.service;

import com.example.skinsaathi.dto.ProfileResponse;

public interface ProfileService {

    ProfileResponse getProfile(Long userId);
}
