package com.indore.metro.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.net.PasswordAuthentication;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="users", schema = "auth")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @PrePersist
    public void generatePublicId() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }

    @Column(unique = true,nullable = false)
    private String email;
    private String password;

    @Column(nullable = false, columnDefinition = "varchar(255) default 'USER'")
    private String role;

    @Column(nullable = false, updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private LocalDateTime createdAt;
}
