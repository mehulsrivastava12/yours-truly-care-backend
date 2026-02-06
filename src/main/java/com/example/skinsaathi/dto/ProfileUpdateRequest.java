package com.example.skinsaathi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateRequest {

    private String name;
    private String mobile;
    private String email;
    private String gender;
    private String dob;   // dd/MM/yyyy
}
