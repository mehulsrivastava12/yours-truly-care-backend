package com.example.skinsaathi.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.skinsaathi.dto.ScanResponse;
import com.example.skinsaathi.entity.ScanResult;
import com.example.skinsaathi.repository.ScanResultRepository;
import com.example.skinsaathi.service.ScanService;
import com.example.skinsaathi.service.SkinAiService;
import com.example.skinsaathi.service.SkinInsightService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScanServiceImpl implements ScanService {

    private final ScanResultRepository scanResultRepository;
    private final SkinAiService skinAiService;
    private final SkinInsightService skinInsightService;

    @Override
    public ScanResponse analyzeFace(Long userId, MultipartFile image) {

        Map<String, Object> aiResult = skinAiService.analyze(image);

        ScanResponse response = skinInsightService.buildResponse(aiResult);

        scanResultRepository.save(ScanResult.from(userId, response));

        return response;
    }
    @Override
    public List<ScanResult> findByUserIdOrderByCreatedAtDesc(Long userId){
        return scanResultRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
