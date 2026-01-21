package com.example.skinsaathi.controller;

import org.springframework.security.core.Authentication;

import com.example.skinsaathi.dto.ScanResponse;
import com.example.skinsaathi.service.ScanService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.skinsaathi.service.SkinAiService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScanController {

    private final ScanService scanService;

    @PostMapping("/scan")
    public ScanResponse scanFace(@RequestParam("image") MultipartFile image, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        System.out.println("userId: "+userId);
        return scanService.analyzeFace(userId, image);
    }
}
