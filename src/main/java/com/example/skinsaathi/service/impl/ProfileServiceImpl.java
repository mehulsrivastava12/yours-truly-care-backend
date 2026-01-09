package com.example.skinsaathi.service.impl;

import com.example.skinsaathi.dto.ProfileResponse;
import com.example.skinsaathi.entity.User;
import com.example.skinsaathi.repository.UserRepository;
import com.example.skinsaathi.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .age(user.getAge())
                .gender(user.getGender())
                .city(user.getCity())
                .build();
    }
}
