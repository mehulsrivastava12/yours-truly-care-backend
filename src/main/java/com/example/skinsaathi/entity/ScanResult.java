package com.example.skinsaathi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "scan_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScanResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String skinType;      // OILY, DRY, COMBINATION
    private String skinIssues;    // ACNE,PIGMENTATION
    private Double confidence;    // 0.0 - 1.0

    private LocalDateTime scannedAt;

    @PrePersist
    void onCreate() {
        this.scannedAt = LocalDateTime.now();
    }
}
