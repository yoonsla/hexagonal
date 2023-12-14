package com.example.blog.domain;

import com.example.blog.adapter.out.persistence.entity.UserRole;
import com.example.blog.core.client.PasswordEncoder;
import com.example.blog.core.infrastructure.dto.EncodedPassword;

public record User(Long id, String email, String name, UserRole role, String password, String passwordSalt) {

    public static User ofNotExist() {
        return new User(null, null, null, null, null, null);
    }

    public static User ofCreated(CreateUser createUser) {
        return new User(
            null,
            createUser.email(),
            createUser.name(),
            UserRole.USER,
            createUser.password(),
            createUser.passwordSalt()
        );
    }

    public boolean notExist() {
        return id == null;
    }

    public boolean isEqualPassword(PasswordEncoder passwordEncoder, String password) {
        final EncodedPassword encodedPassword = new EncodedPassword(this.password, this.passwordSalt);
        return passwordEncoder.matches(password, encodedPassword);
    }

    public boolean isNotEqualPassword(PasswordEncoder passwordEncoder, String password) {
        return !isEqualPassword(passwordEncoder, password);
    }

    public User updatePassword(EncodedPassword encodedPassword) {
        return new User(id, email, name, role, encodedPassword.getEncodedPassword(), encodedPassword.getSalt());
    }
}
