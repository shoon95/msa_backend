package com.sparta.msa_exam.auth.utils;


import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {

    private final SecretKey secretKey;
    @Value("${service.jwt.expiredMs}")
    private Long expirationTime;

    public JwtUtils(@Value("${service.jwt.secret-key}") String secretKey) {

        byte[] decode = Base64.getDecoder().decode(secretKey);
        this.secretKey = new SecretKeySpec(decode, 0, decode.length, "HmacSHA256");
    };

    public boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date(System.currentTimeMillis()));
    }
    public String generateToken(String username) {
        return Jwts.builder()
                .claim("username", username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+expirationTime))
                .signWith(secretKey)
                .compact();
    }


}

