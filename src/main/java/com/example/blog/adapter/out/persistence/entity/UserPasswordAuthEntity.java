package com.example.blog.adapter.out.persistence.entity;

import com.example.blog.domain.UserPasswordAuth;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_password_auth")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPasswordAuthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "authCode")
    private String authCode;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    public static UserPasswordAuthEntity from(UserPasswordAuth passwordAuth) {
        return new UserPasswordAuthEntity(passwordAuth.id(), passwordAuth.email(), passwordAuth.authCode(), passwordAuth.createdAt());
    }

    public UserPasswordAuth toDomain() {
        return UserPasswordAuth.of(id, email, authCode, createdAt);
    }
}
