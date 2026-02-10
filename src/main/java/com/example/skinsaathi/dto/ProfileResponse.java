package com.example.skinsaathi.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;
import com.example.skinsaathi.dto.AddressResponse;

@Getter
@Builder
public class ProfileResponse {

    private Long id;
    private String name;
    private String email;
    private String mobile;
    private String dob;
    private String gender;
    private String imageData;
    private List<AddressResponse> addresses;
}
