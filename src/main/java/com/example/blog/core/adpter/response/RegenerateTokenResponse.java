package com.example.blog.core.adpter.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegenerateTokenResponse {

    private String accessToken;

    public static RegenerateTokenResponse from(String accessToken) {
        return new RegenerateTokenResponse(accessToken);
    }
}
