package com.example.blog.core.infrastructure.dto;

import com.example.blog.adapter.out.persistence.entity.UserRole;
import com.example.blog.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccessClaim {

    private Long id;
    private String email;
    private String name;
    private UserRole role;

    public static AccessClaim from(User user) {
        return new AccessClaim(user.id(), user.email(), user.name(), user.role());
    }
}
