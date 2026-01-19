// package com.example.skinsaathi.entity;

// import jakarta.persistence.*;
// import lombok.*;

// import java.time.LocalDateTime;

// @Entity
// @Table(name = "users")
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// public class User {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(nullable = false)
//     private String name;

//     @Column(unique = true)
//     private String email;

//     @Column(unique = true)
//     private String mobile;

//     // @Column(nullable = false)
//     // private String password;

//     @Column(nullable = false)
//     private boolean active = true;

//     private Integer age;
//     private String gender;
//     private String city;

//     private LocalDateTime createdAt;

//     @PrePersist
//     void onCreate() {
//         this.createdAt = LocalDateTime.now();
//     }
// }


package com.example.skinsaathi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private Integer age;
    private String gender;
    private String city;

    // Auth & tracking
    private LocalDateTime lastLoginAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
