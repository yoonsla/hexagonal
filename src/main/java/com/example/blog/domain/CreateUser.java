package com.example.blog.domain;

public record CreateUser(Long id, String email, String name, String password, String passwordSalt) {

    public static CreateUser of(String email, String name, String encodedPassword, String salt) {
        return new CreateUser(
            null,
            email,
            name,
            encodedPassword,
            salt
        );
    }
}
