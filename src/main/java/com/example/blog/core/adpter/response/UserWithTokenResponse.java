package com.example.blog.core.adpter.response;

import com.example.blog.core.service.dto.UserWithTokenDto;
import com.example.blog.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserWithTokenResponse {

    private String accessToken;
    private String refreshToken;
    private User user;

    public static UserWithTokenResponse from(UserWithTokenDto dto) {
        return new UserWithTokenResponse(dto.getAccessToken(), dto.getRefreshToken(), dto.getUser());
    }
}
