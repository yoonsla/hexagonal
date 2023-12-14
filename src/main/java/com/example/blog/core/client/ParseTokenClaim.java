package com.example.blog.core.client;

import com.example.blog.core.infrastructure.dto.AccessClaim;

public interface ParseTokenClaim {

    AccessClaim accessToken(String accessToken);

    boolean isTokenExpired(String token);
}
