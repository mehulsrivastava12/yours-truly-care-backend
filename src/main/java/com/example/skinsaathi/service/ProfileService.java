package com.example.skinsaathi.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.skinsaathi.dto.AddressRequest;
import com.example.skinsaathi.dto.AddressResponse;
import com.example.skinsaathi.dto.ProfileResponse;
import com.example.skinsaathi.dto.ProfileUpdateRequest;

public interface ProfileService {

    ProfileResponse getProfile(Long userId);
    void updateProfile(Long userId, ProfileUpdateRequest request, MultipartFile image);
    AddressResponse saveAddress(AddressRequest request, Long userId);
    void deleteAddress(Long addressId, Long userId);
}
