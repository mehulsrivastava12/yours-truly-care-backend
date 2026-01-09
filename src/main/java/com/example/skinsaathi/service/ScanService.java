package com.example.skinsaathi.service;

import com.example.skinsaathi.dto.ScanResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ScanService {

    ScanResponse analyzeFace(Long userId, MultipartFile image);
}
