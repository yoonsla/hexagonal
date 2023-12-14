package com.example.blog.core.infrastructure;

import com.example.blog.core.client.TokenStore;
import com.example.blog.core.infrastructure.dto.TokenType;
import com.example.blog.core.service.RedisTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class RedisTokenStore implements TokenStore {

    private final RedisTokenService redisTokenService;

    @Override
    public void saveAccessToken(String email, String accessToken) {
        redisTokenService.addToken(email, accessToken, TokenType.ACCESS_TOKEN);
    }

    @Override
    public void saveRefreshToken(String email, String refreshToken) {
        redisTokenService.addToken(email, refreshToken, TokenType.REFRESH_TOKEN);
    }

    @Override
    public void delete(String email) {
        redisTokenService.deleteToken(email);
    }
}
