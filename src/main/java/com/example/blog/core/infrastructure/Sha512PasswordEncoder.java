package com.example.blog.core.infrastructure;

import com.example.blog.core.client.PasswordEncoder;
import com.example.blog.core.infrastructure.dto.EncodedPassword;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class Sha512PasswordEncoder implements PasswordEncoder {

    @Override
    public EncodedPassword encode(String rawPassword) {
        final String salt = generateSalt();
        return new EncodedPassword(SHA512(rawPassword, salt), salt);
    }

    @Override
    public boolean matches(String rawPassword, EncodedPassword encodedPassword) {
        final String password = SHA512(rawPassword, encodedPassword.getSalt());
        return password.equals(encodedPassword.getEncodedPassword());
    }

    private String generateSalt() {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] bytes = new byte[16];
            random.nextBytes(bytes);
            return new String(Base64.getEncoder().encode(bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }

    private static String SHA512(String input, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            md.update(input.getBytes());
            return String.format("%0128x", new BigInteger(1, md.digest()));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException();
        }
    }
}
