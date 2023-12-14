package com.example.blog.core.config;

import com.example.blog.core.util.JwtUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Profile("!local")
@Component
@RequiredArgsConstructor
@Log4j2
public class AppEventListener {

    @EventListener(ApplicationReadyEvent.class)
    public void generateRsaForToken() throws JOSEException {
        RSAKey rsaKey = new RSAKeyGenerator(2048)
            .keyID(UUID.randomUUID().toString())
            .generate();
        JwtUtil.setRsaKey(rsaKey);
    }
}
