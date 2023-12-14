package com.example.blog.core.infrastructure;

import com.example.blog.core.client.repository.GenerateToken;
import com.example.blog.core.infrastructure.dto.AccessClaim;
import com.example.blog.core.infrastructure.dto.RefreshClaim;
import com.example.blog.core.util.JwtUtil;
import org.springframework.stereotype.Component;

@Component
public class DefaultGenerateToken implements GenerateToken {

    @Override
    public String accessToken(AccessClaim accessClaim) {
        return JwtUtil.accessToken(accessClaim);
    }

    @Override
    public String refreshToken(RefreshClaim refreshClaim) {
        return JwtUtil.refreshToken(refreshClaim);
    }
}
