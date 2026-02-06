package com.example.skinsaathi.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.skinsaathi.dto.ScanResponse;
import com.example.skinsaathi.entity.ScanResult;

public interface ScanService {

    ScanResponse analyzeFace(Long userId, MultipartFile image);
    List<ScanResult> findByUserIdOrderByCreatedAtDesc(Long userId);
}
