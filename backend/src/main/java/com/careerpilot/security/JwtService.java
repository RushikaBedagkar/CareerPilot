package com.careerpilot.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    "CareerPilotSecretKeyForJWTAuthenticationSystem2026SecureKey123456789"
                            .getBytes()
            );

    // Generate JWT
    public String generateToken(String email, String role) {

        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 24 * 60 * 60 * 1000
                        )
                )
                .signWith(key)
                .compact();
    }

    // Extract email
    public String extractUsername(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Extract role
    public String extractRole(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    // Validate token
    public boolean isTokenValid(
            String token,
            String email) {

        try {

            String extractedEmail =
                    extractUsername(token);

            return extractedEmail.equals(email)
                    && !isTokenExpired(token);

        } catch (Exception e) {

            return false;
        }
    }

    private boolean isTokenExpired(String token) {

        Date expiration =
                Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getExpiration();

        return expiration.before(new Date());
    }
}