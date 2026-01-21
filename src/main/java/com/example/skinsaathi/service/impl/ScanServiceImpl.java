package com.example.skinsaathi.service.impl;

import com.example.skinsaathi.dto.ScanResponse;
import com.example.skinsaathi.entity.ScanResult;
import com.example.skinsaathi.repository.ScanResultRepository;
import com.example.skinsaathi.service.ScanService;
import com.example.skinsaathi.util.FileStorageUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;
import com.example.skinsaathi.service.SkinAiService;
import com.example.skinsaathi.service.SkinInsightService;

@Service
@RequiredArgsConstructor
public class ScanServiceImpl implements ScanService {

    private final ScanResultRepository scanResultRepository;
    private final SkinAiService skinAiService;
    private final SkinInsightService skinInsightService;

    @Override
    public ScanResponse analyzeFace(Long userId, MultipartFile image) {

        // 1️⃣ Call Python AI service
        Map<String, Object> aiResult = skinAiService.analyze(image);

        // 2️⃣ Convert AI result to user-safe response
        ScanResponse response = skinInsightService.buildResponse(aiResult);

        // 3️⃣ Persist scan history (optional but recommended)

        scanResultRepository.save(ScanResult.from(userId, response));

        return response;
    }
}
