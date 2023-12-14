package com.example.blog.core.infrastructure;

import com.example.blog.core.client.RsaService;
import com.example.blog.core.contants.ResponseCode;
import com.example.blog.core.exception.BlogException;
import com.example.blog.core.infrastructure.dto.RsaCacheKey;
import com.example.blog.core.infrastructure.dto.RsaKeyPair;
import com.example.blog.core.util.RsaUtil;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class RedisRsaService implements RsaService {

    private static final String RSA_CACHE_NAME = "cache-rsa";
    private final RedisTemplate<String, Object> domainRedisTemplate;

    public void refreshRsa() {
        // 기존 RSA 조회 후 backup 으로 저장
        RsaKeyPair oldRsaData = Optional.ofNullable(domainRedisTemplate.opsForHash().get(RSA_CACHE_NAME, RsaCacheKey.NEW_CACHE_KEY.name()))
            .map(rsaKey -> (RsaKeyPair) rsaKey)
            .orElse(RsaUtil.createRSAKeyPair());
        domainRedisTemplate.opsForHash().put(RSA_CACHE_NAME, RsaCacheKey.OLD_CACHE_KEY.name(), oldRsaData);
        // 새로운 RSA 발급 후 저장
        RsaKeyPair newRsaData = RsaUtil.createRSAKeyPair();
        domainRedisTemplate.opsForHash().put(RSA_CACHE_NAME, RsaCacheKey.NEW_CACHE_KEY.name(), newRsaData);

        log.info("old rsa data ==> {}", oldRsaData);
        log.info("new rsa data ==> {}", newRsaData);
    }

    public RsaKeyPair getRsaData(RsaCacheKey cacheKey) {
        return Optional.ofNullable(domainRedisTemplate.opsForHash().get(RSA_CACHE_NAME, cacheKey.name()))
            .map(rsaKey -> (RsaKeyPair) rsaKey)
            .orElseThrow(() -> new BlogException(ResponseCode.RSA_KEY_NOT_EXIST));
    }

    @Override
    public String decryptRSA(String encryptedValue) {
        String decoded = Base64.getEncoder().encodeToString(encryptedValue.getBytes(StandardCharsets.UTF_8));
        try {
            RsaKeyPair rsaKeyPair = getRsaData(RsaCacheKey.NEW_CACHE_KEY);
            return RsaUtil.decryptRSA(decoded, rsaKeyPair.getPrivateKey());
        } catch (RuntimeException e) {
            RsaKeyPair rsaOldKeyPair = getRsaData(RsaCacheKey.OLD_CACHE_KEY);
            return RsaUtil.decryptRSA(decoded, rsaOldKeyPair.getPrivateKey());
        }
    }
}
