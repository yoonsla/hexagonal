package com.example.blog.core.infrastructure.dto;

import com.example.blog.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RefreshClaim {

    private long id;
    private String email;

    public static RefreshClaim from(User user) {
        return new RefreshClaim(user.id(), user.email());
    }
}
