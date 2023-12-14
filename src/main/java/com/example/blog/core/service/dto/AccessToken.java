package com.example.blog.core.service.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor
@Setter
@Getter
@RedisHash("access-token")
public class AccessToken {

    @Id
    private String email;
    private String token;
}
