package com.example.skinsaathi.service.impl;

import com.example.skinsaathi.dto.AuthResponse;
import com.example.skinsaathi.entity.OtpVerification;
import com.example.skinsaathi.entity.User;
import com.example.skinsaathi.repository.OtpVerificationRepository;
import com.example.skinsaathi.repository.UserRepository;
import com.example.skinsaathi.service.OtpService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.skinsaathi.security.JwtUtil;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final OtpVerificationRepository otpRepo;
    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;

    @Value("${twilio.phone.from}")
    private String fromNumber;

    private static final int OTP_EXPIRY_MINUTES = 5;

    /* ======================
       SEND OTP (TWILIO)
    ====================== */
    @Override
    public void sendOtp(String mobile) {

        String otp = generateOtp();

        OtpVerification entity = OtpVerification.builder()
                .mobile(mobile)
                .otp(otp)
                .expiresAt(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES))
                .used(false)
                .build();

        otpRepo.save(entity);

        // Send SMS via Twilio
        Message.creator(
                new PhoneNumber("+91" + mobile),
                new PhoneNumber(fromNumber),
                "Your YoursTrulyCare OTP is " + otp + ". Valid for 5 minutes."
        ).create();
    }

    /* ======================
       VERIFY OTP
    ====================== */
    @Override
    public AuthResponse verifyOtp(
            String mobile,
            String otp,
            String name,
            String email
    ) {
        // 1️⃣ Fetch latest unused OTP
        OtpVerification verification =
                otpRepo.findTopByMobileAndUsedFalseOrderByCreatedAtDesc(mobile)
                        .orElseThrow(() -> new RuntimeException("OTP not found"));

        // 2️⃣ Validate OTP
        if (verification.isUsed()) {
            throw new RuntimeException("OTP already used");
        }

        if (verification.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!verification.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        // 3️⃣ Mark OTP as used
        verification.setUsed(true);
        otpRepo.save(verification);

        // 4️⃣ Find or create user
        User user = userRepo.findByMobile(mobile).orElse(null);
        boolean newUser = false;

        if (user == null) {
            newUser = true;
            user = User.builder()
                    .name(name != null ? name : "User")
                    .email(email)
                    .mobile(mobile)
                    .active(true)
                    .build();
            userRepo.save(user);
        }

        // 5️⃣ Update last login
        user.setLastLoginAt(LocalDateTime.now());
        userRepo.save(user);

        // 6️⃣ 🔐 GENERATE REAL JWT
        String token = jwtUtil.generateToken(user.getId());

        // 7️⃣ Return real token
        return new AuthResponse(token, newUser);
    }

    /* ======================
       UTIL
    ====================== */
    private String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}
