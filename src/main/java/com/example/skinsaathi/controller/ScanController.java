package com.example.skinsaathi.controller;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.example.skinsaathi.dto.ScanResponse;
import com.example.skinsaathi.service.ScanService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.skinsaathi.entity.ScanResult;
import com.example.skinsaathi.service.SkinAiService;
import org.springframework.http.ResponseEntity;

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
    
    @GetMapping("/history/{userId}")
    public ResponseEntity<?> history(@PathVariable Long userId) {
        List<ScanResult> results =  scanService.findByUserIdOrderByCreatedAtDesc(userId);
        if (results.isEmpty()) {
            return ResponseEntity.ok("No result found");
        }
        return ResponseEntity.ok(results);
    }
}
