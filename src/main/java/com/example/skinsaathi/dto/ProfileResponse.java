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
    private Integer age;
    private String gender;
    private String city;
}
