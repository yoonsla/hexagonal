package com.example.blog.adapter.out.persistence;

import com.example.blog.adapter.out.persistence.entity.UserEntity;
import com.example.blog.adapter.out.persistence.entity.UserRepository;
import com.example.blog.application.port.out.CommandUserPort;
import com.example.blog.application.port.out.QueryUserPort;
import com.example.blog.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserPersistenceAdapter implements QueryUserPort, CommandUserPort {

    private final UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .map(UserEntity::toDomain)
            .orElse(User.ofNotExist());
    }

    @Override
    public User save(User user) {
        return userRepository.save(UserEntity.from(user)).toDomain();
    }
}
