package com.example.blog.core.client.repository;

import com.example.blog.core.infrastructure.dto.AccessClaim;
import com.example.blog.core.infrastructure.dto.RefreshClaim;

public interface GenerateToken {

    String accessToken(AccessClaim accessClaim);

    String refreshToken(RefreshClaim refreshClaim);
}
