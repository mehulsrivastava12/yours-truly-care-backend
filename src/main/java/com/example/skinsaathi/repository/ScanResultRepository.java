package com.example.skinsaathi.repository;

import com.example.skinsaathi.entity.ScanResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScanResultRepository extends JpaRepository<ScanResult, Long> {

    List<ScanResult> findByUserIdOrderByScannedAtDesc(Long userId);
}
