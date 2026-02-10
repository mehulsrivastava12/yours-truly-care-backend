package com.example.skinsaathi.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "users",
    indexes = {
        @Index(name = "idx_user_mobile", columnList = "mobile"),
        @Index(name = "idx_user_email", columnList = "email")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic profile
    @Column(nullable = true)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true, nullable = true)
    private String mobile;

    // Account status
    @Column(nullable = false)
    private boolean active = true;

    // Optional profile details (can be completed later)
    private LocalDate  dob;
    private String gender;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    // Auth & tracking
    private LocalDateTime lastLoginAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageData;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
