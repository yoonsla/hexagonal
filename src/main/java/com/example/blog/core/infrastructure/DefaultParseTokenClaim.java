package com.example.blog.core.infrastructure;

import com.example.blog.core.client.ParseTokenClaim;
import com.example.blog.core.infrastructure.dto.AccessClaim;
import com.example.blog.core.util.JsonUtil;
import com.example.blog.core.util.JwtUtil;
import java.util.Date;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DefaultParseTokenClaim implements ParseTokenClaim {

    @Override
    public AccessClaim accessToken(String accessToken) {
        return parse(accessToken, AccessClaim.class);
    }

    @Override
    public boolean isTokenExpired(String token) {
        Date expired = JwtUtil.getTokenExpirationTime(token);
        Date now = new Date();
        return expired != null && now.after(expired);
    }

    private <T> T parse(String token, Class<T> clazz) {
        Map<String, Object> tokenClaim = JwtUtil.getTokenClaim(token);
        return JsonUtil.unmarshalFromMap(tokenClaim, clazz);
    }
}
