package com.example.skinsaathi.dto;

import lombok.Builder;
import lombok.Getter;

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

}
