package com.example.skinsaathi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ScanResponse {

    private String skinType;
    private String insight;
    private List<String> tips;
    private String disclaimer;
}
