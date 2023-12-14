package com.example.blog.core.client.repository;

import com.example.blog.core.service.dto.AccessToken;
import org.springframework.data.repository.CrudRepository;

public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {

}
