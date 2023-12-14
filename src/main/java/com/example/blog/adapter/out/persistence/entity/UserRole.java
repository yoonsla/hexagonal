package com.example.blog.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {

    ADMIN,
    USER;

    public boolean isAdmin() {
        return this.equals(ADMIN);
    }

    public boolean isUser() {
        return this.equals(USER);
    }
}
