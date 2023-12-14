package com.example.blog.core.infrastructure.dto;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RsaKeyPair implements Serializable {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    private String privateKeyStr;
    private String publicKeyStr;
    private String modulusStr;
    private String exponentStr;
}
