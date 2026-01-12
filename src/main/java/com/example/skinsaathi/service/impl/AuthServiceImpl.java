package com.example.skinsaathi.service.impl;

import com.example.skinsaathi.dto.AuthResponse;
import com.example.skinsaathi.entity.User;
import com.example.skinsaathi.repository.UserRepository;
import com.example.skinsaathi.security.JwtUtil;
import com.example.skinsaathi.service.AuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Value("${google.client-id}")
    private String googleClientId;

    @Override
    public AuthResponse loginWithGoogle(String idTokenString) {

        GoogleIdTokenVerifier verifier =
                new GoogleIdTokenVerifier.Builder(
                        new NetHttpTransport(),
                        GsonFactory.getDefaultInstance()
                )
                .setAudience(Collections.singletonList(googleClientId))
                .build();

        GoogleIdToken idToken;

        try {
            idToken = verifier.verify(idTokenString);
        } catch (Exception e) {
            throw new RuntimeException("Invalid Google token");
        }

        if (idToken == null) {
            throw new RuntimeException("Invalid Google token");
        }

        GoogleIdToken.Payload payload = idToken.getPayload();

        String email = payload.getEmail();
        String name = (String) payload.get("name");

        // 🔍 Find or create user
        User user = userRepository.findByEmail(email).orElse(null);
        boolean newUser = false;

        if (user == null) {
            newUser = true;
            user = User.builder()
                    .email(email)
                    .name(name)
                    .active(true)
                    .build();
            userRepository.save(user);
        }

        // 🔐 Issue JWT
        String jwt = jwtUtil.generateToken(user.getId());

        return new AuthResponse(jwt, newUser);
    }
}
