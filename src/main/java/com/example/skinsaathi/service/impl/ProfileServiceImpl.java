package com.example.skinsaathi.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.skinsaathi.dto.AddressRequest;
import com.example.skinsaathi.dto.AddressResponse;
import com.example.skinsaathi.dto.ProfileResponse;
import com.example.skinsaathi.dto.ProfileUpdateRequest;
import com.example.skinsaathi.entity.Address;
import com.example.skinsaathi.entity.User;
import com.example.skinsaathi.repository.AddressRepository;
import com.example.skinsaathi.repository.UserRepository;
import com.example.skinsaathi.service.ProfileService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Override
    public ProfileResponse getProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<AddressResponse> addressResponses =
                user.getAddresses()
                        .stream()
                        .map(address -> AddressResponse.builder()
                                .id(address.getId())
                                .pincode(address.getPincode())
                                .houseNo(address.getHouseNo())
                                .area(address.getArea())
                                .city(address.getCity())
                                .state(address.getState())
                                .country(address.getCountry())
                                .contactName(address.getContactName())
                                .phoneNumber(address.getPhoneNumber())
                                .defaultAddress(address.getDefaultAddress())
                                .build())
                        .toList();
        return ProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .dob(user.getDob() != null ? user.getDob().toString() : null)
                .gender(user.getGender())
                .addresses(addressResponses)
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

    @Override
    @Transactional
    public AddressResponse saveAddress(AddressRequest request, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getDefaultAddress()) {
            addressRepository.clearDefaultForUser(user.getId());
        }

        Address address = Address.builder()
                .user(user)
                .pincode(request.getPincode())
                .houseNo(request.getHouseNo())
                .area(request.getArea())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .contactName(request.getContactName())
                .phoneNumber(request.getPhoneNumber())
                .defaultAddress(request.getDefaultAddress())
                .build();

        Address saved = addressRepository.save(address);

        return AddressResponse.builder()
                .id(saved.getId())
                .pincode(saved.getPincode())
                .houseNo(saved.getHouseNo())
                .area(saved.getArea())
                .city(saved.getCity())
                .state(saved.getState())
                .country(saved.getCountry())
                .contactName(saved.getContactName())
                .phoneNumber(saved.getPhoneNumber())
                .defaultAddress(saved.getDefaultAddress())
                .build();
    }

    @Transactional
    @Override
    public void deleteAddress(Long addressId, Long userId) {

        Address address = addressRepository
                .findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        boolean wasDefault = address.getDefaultAddress();

        addressRepository.delete(address);

        // 🔥 VERY IMPORTANT — assign new default if needed
        if (wasDefault) {

            addressRepository
                .findAllByUserId(userId)
                .stream()
                .findFirst()
                .ifPresent(a -> {
                    a.setDefaultAddress(true);
                    addressRepository.save(a);
                });
        }
    }

}
