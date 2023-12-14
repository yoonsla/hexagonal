package com.example.blog.adapter.out.persistence;

import com.example.blog.adapter.out.persistence.entity.UserPasswordAuthEntity;
import com.example.blog.adapter.out.persistence.entity.UserPasswordAuthRepository;
import com.example.blog.application.port.out.CommandUserPasswordAuthPort;
import com.example.blog.application.port.out.QueryUserPasswordAuthPort;
import com.example.blog.domain.UserPasswordAuth;
import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserPasswordAuthPersistenceAdapter implements CommandUserPasswordAuthPort, QueryUserPasswordAuthPort {

    private final UserPasswordAuthRepository userPasswordAuthRepository;

    @Override
    public UserPasswordAuth save(UserPasswordAuth passwordAuth) {
        return userPasswordAuthRepository.save(UserPasswordAuthEntity.from(passwordAuth)).toDomain();
    }

    @Override
    public UserPasswordAuth findByLastAuthCode(String email) {
        return userPasswordAuthRepository.findByEmail(email).stream()
            .sorted(Comparator.comparing(UserPasswordAuthEntity::getCreatedAt).reversed())
            .limit(1)
            .map(UserPasswordAuthEntity::toDomain)
            .findAny()
            .orElse(UserPasswordAuth.ofNotExist());
    }
}
