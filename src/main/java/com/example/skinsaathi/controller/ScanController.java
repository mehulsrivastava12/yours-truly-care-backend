package com.example.skinsaathi.controller;

import com.example.skinsaathi.dto.ScanResponse;
import com.example.skinsaathi.service.ScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/scan")
@RequiredArgsConstructor
public class ScanController {

    private final ScanService scanService;

    @PostMapping("/face")
    public ScanResponse scanFace(
            @RequestParam Long userId,
            @RequestParam("image") MultipartFile image
    ) {
        return scanService.analyzeFace(userId, image);
    }
}
