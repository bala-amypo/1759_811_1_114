package com.example.demo.security;

import com.example.demo.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.security.Key;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret:SecretKeyForJWTGeneration12345}") // fallback secret
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}") // 24 hours in ms
    private long jwtExpirationInMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // Generate token from Authentication object
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal(); // from AuthController

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(user.getEmail()) // use email as username
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Get username/email from token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Validate JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            System.out.println("Invalid JWT: " + ex.getMessage());
        }
        return false;
    }
}