package com.example.blog.core.client;

import com.example.blog.core.infrastructure.dto.RsaCacheKey;
import com.example.blog.core.infrastructure.dto.RsaKeyPair;

public interface RsaService {

    void refreshRsa();

    RsaKeyPair getRsaData(RsaCacheKey cacheKey);

    String decryptRSA(String encryptedValue);
}
