package com.example.blog.core.util;

import com.example.blog.core.contants.ResponseCode;
import com.example.blog.core.exception.BlogException;
import com.example.blog.core.infrastructure.dto.RsaKeyPair;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

public class RsaUtil {

    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    public static RsaKeyPair createRSAKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(TRANSFORMATION);
            generator.initialize(2048); //1024
            KeyPair keyPair = generator.genKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec publicSpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);

            String modulusStr = publicSpec.getModulus().toString(16);
            String exponentStr = publicSpec.getPublicExponent().toString(16);

            return new RsaKeyPair(privateKey, publicKey, privateKeyStr, publicKeyStr, modulusStr, exponentStr);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decryptRSA(String encryptedValue, PrivateKey privateKey) {
        try {
            // 정종환_Encryption algorithms should be used with secure mode and padding scheme
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedValue.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException ex) {
            throw new BlogException(ResponseCode.RSA_DECODE_FAIL);
        }
    }
}
