package com.example.blog.adapter.out.persistence.entity;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPasswordAuthRepository extends JpaRepository<UserPasswordAuthEntity, Long> {

    List<UserPasswordAuthEntity> findByEmail(String email);
}
