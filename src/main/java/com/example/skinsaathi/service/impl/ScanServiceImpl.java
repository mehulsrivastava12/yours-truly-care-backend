package com.example.skinsaathi.service.impl;

import com.example.skinsaathi.dto.ScanResponse;
import com.example.skinsaathi.entity.ScanResult;
import com.example.skinsaathi.repository.ScanResultRepository;
import com.example.skinsaathi.service.ScanService;
import com.example.skinsaathi.util.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ScanServiceImpl implements ScanService {

    private final ScanResultRepository scanResultRepository;

    @Override
    public ScanResponse analyzeFace(Long userId, MultipartFile image) {

        // 1. Temporarily store image
        String storedPath = FileStorageUtil.storeTempFile(image);

        // 2. Call face analysis API (MOCK FOR NOW)
        // In real implementation, send image to ML service
        String detectedSkinType = "OILY";
        String detectedIssues = "ACNE,PIGMENTATION";
        double confidence = 0.88;

        // 3. Save result
        ScanResult result = ScanResult.builder()
                .userId(userId)
                .skinType(detectedSkinType)
                .skinIssues(detectedIssues)
                .confidence(confidence)
                .build();

        scanResultRepository.save(result);

        // 4. Delete temp file (privacy)
        FileStorageUtil.deleteTempFile(storedPath);

        return new ScanResponse(detectedSkinType, detectedIssues, confidence);
    }
}
