package com.example.skinsaathi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ScanResponse {

    private String skinType;
    private String skinIssues;
    private Double confidence;
}
