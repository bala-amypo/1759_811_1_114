package com.example.demo.config;

import com.example.demo.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = "secret";
    private static final long VALIDITY_MS = 3600000; // 1 hour

    /**
     * Generate JWT token for authenticated user
     */
    public String generateToken(User user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + VALIDITY_MS);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userid", user.getId())
                .claim("role", user.getRole().name())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
