package com.example.blog.core.service;

import com.example.blog.core.client.repository.AccessTokenRepository;
import com.example.blog.core.client.repository.RefreshTokenRepository;
import com.example.blog.core.contants.ResponseCode;
import com.example.blog.core.exception.BlogException;
import com.example.blog.core.infrastructure.dto.TokenType;
import com.example.blog.core.service.dto.AccessToken;
import com.example.blog.core.service.dto.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class RedisTokenService {

    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public void addToken(String email, String token, TokenType tokenType) {
        switch (tokenType) {
            case ACCESS_TOKEN -> accessTokenRepository.save(new AccessToken(email, token));
            case REFRESH_TOKEN -> refreshTokenRepository.save(new RefreshToken(email, token));
            default -> throw new BlogException(ResponseCode.IS_NOT_EXIST);
        }
    }

    public void deleteToken(String email) {
        accessTokenRepository.deleteById(email);
        refreshTokenRepository.deleteById(email);
    }

    public AccessToken getToken(String email) {
        return accessTokenRepository.findById(email)
            .orElseThrow(() -> new BlogException(ResponseCode.INVALID_ACCESS_TOKEN));
    }
}
