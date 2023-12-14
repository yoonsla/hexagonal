package com.example.blog.core.util;

import com.example.blog.core.infrastructure.dto.AccessClaim;
import com.example.blog.core.infrastructure.dto.RefreshClaim;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static RSAKey RSA_KEY;

    public static void setRsaKey(RSAKey rsaKey) {
        RSA_KEY = rsaKey;
    }

    public static String accessToken(AccessClaim accessClaim) {
        Date now = new Date();
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
            .claim("id", accessClaim.getId())
            .claim("email", accessClaim.getEmail())
            .claim("name", accessClaim.getName())
            .expirationTime(new Date(now.getTime() + (1000 * 60 * 60 * 2)))
            .issuer("blog-svc")
            .subject("token")
            .audience("")
            .issueTime(now)
            .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(RSA_KEY.getKeyID()).build(), claimsSet);
        return signedJWT.serialize();
    }

    public static String refreshToken(RefreshClaim refreshClaim) {
        Date now = new Date();
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
            .claim("id", refreshClaim.getId())
            .claim("email", refreshClaim.getEmail())
            .expirationTime(new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 14)))
            .issuer("blog-svc")
            .subject("token")
            .audience("")
            .issueTime(now)
            .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(RSA_KEY.getKeyID()).build(), claimsSet);
        return signedJWT.serialize();
    }

    public static Map<String, Object> getTokenClaim(String token) {
        try {
            return SignedJWT.parse(token.replace("Bearer", "")).getJWTClaimsSet().getClaims();
        } catch (ParseException e) {
            throw new RuntimeException();
        }
    }

    public static Date getTokenExpirationTime(String token) {
        try {
            return SignedJWT.parse(token.replace("Bearer", "")).getJWTClaimsSet().getExpirationTime();
        } catch (ParseException e) {
            throw new RuntimeException();
        }
    }
}
