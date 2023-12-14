package com.example.blog.core.client;

public interface TokenStore {

    void saveAccessToken(String email, String accessToken);

    void saveRefreshToken(String email, String refreshToken);

    void delete(String email);
}
