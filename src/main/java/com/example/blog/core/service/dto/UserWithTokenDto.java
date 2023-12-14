package com.example.blog.core.service.dto;

import com.example.blog.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserWithTokenDto {

    private String accessToken;
    private String refreshToken;
    private User user;

    public static UserWithTokenDto of(String accessToken, String refreshToken, User user) {
        return new UserWithTokenDto(accessToken, refreshToken, user);
    }
}
