package com.example.skinsaathi.repository;

import com.example.skinsaathi.entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OtpVerificationRepository
        extends JpaRepository<OtpVerification, Long> {

    Optional<OtpVerification> findTopByMobileAndUsedFalseOrderByCreatedAtDesc(
            String mobile
    );
}
