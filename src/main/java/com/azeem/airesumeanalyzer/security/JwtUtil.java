package com.azeem.airesumeanalyzer.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Secret key is injected from configuration (jwt.secret -> JWT_SECRET env var).
    // Must be at least 32 characters for HS256.
    private final SecretKey secretKey;

    // Token validity in milliseconds, injected from configuration.
    private final long expirationTime;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms:86400000}") long expirationTime) {

        if (secret == null || secret.length() < 32) {
            throw new IllegalStateException(
                    "jwt.secret must be set and at least 32 characters long. " +
                    "Set the JWT_SECRET environment variable.");
        }

        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationTime = expirationTime;
    }

    // Generate Token
    public String generateToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract Username (Email)
    public String extractUsername(String token) {

        return extractClaims(token).getSubject();
    }

    // Validate Token
    public boolean validateToken(String token, String email) {

        String extractedEmail = extractUsername(token);

        return extractedEmail.equals(email)
                && !isTokenExpired(token);
    }

    // Check Expiration
    private boolean isTokenExpired(String token) {

        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }

    // Extract Claims
    private Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}