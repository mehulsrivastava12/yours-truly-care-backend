package com.example.skinsaathi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import com.example.skinsaathi.dto.ScanResponse;

@Entity
@Table(name = "scan_results")
@Getter
@Setter
public class ScanResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String skinType;

    @Column(length = 500)
    private String insight;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "scan_tips",
            joinColumns = @JoinColumn(name = "scan_result_id")
    )
    @Column(name = "tip")
    private List<String> tips;

    @Column(length = 500)
    private String disclaimer;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    /* =========================
       FACTORY METHOD
       ========================= */
    public static ScanResult from(Long userId, ScanResponse response) {
        ScanResult result = new ScanResult();
        result.setUserId(userId);
        result.setSkinType(response.getSkinType());
        result.setInsight(response.getInsight());
        result.setTips(response.getTips());
        result.setDisclaimer(response.getDisclaimer());
        result.setCreatedAt(LocalDateTime.now());
        return result;
    }
}
