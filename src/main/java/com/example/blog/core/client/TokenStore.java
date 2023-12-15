package com.example.blog.core.client;

import com.example.blog.core.service.dto.AccessToken;

public interface TokenStore {

    void saveAccessToken(String email, String accessToken);

    void saveRefreshToken(String email, String refreshToken);

    void delete(String email);

    AccessToken getToken(String email);
}
