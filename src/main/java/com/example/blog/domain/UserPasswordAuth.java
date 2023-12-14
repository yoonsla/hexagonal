package com.example.blog.domain;

import java.time.LocalDateTime;

public record UserPasswordAuth(
    Long id,
    String email,
    String authCode,
    LocalDateTime createdAt
) {

    public static UserPasswordAuth of(Long id, String email, String authCode, LocalDateTime createdAt) {
        return new UserPasswordAuth(id, email, authCode, createdAt);
    }

    public static UserPasswordAuth ofCreated(String email, String authCode) {
        return new UserPasswordAuth(null, email, authCode, LocalDateTime.now());
    }

    public static UserPasswordAuth ofNotExist() {
        return new UserPasswordAuth(null, null, null, null);
    }

    public boolean notExist() {
        return this.id == null;
    }

    public boolean isOverTime() {
        return LocalDateTime.now().isAfter(this.createdAt().plusMinutes(10));
    }
}
