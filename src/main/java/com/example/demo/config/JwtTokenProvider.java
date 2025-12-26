package com.example.demo.config;

import com.example.demo.entity.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private final String secretKey = "YourSecretKey12345"; // replace with secure key
    private final long validityInMilliseconds = 3600000; // 1 hour

    // Generate token with userid, email, role
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userid", user.getUserId());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Extract email from JWT
    public String getEmailFromToken(String token) {
        return getAllClaimsFromToken(token).get("email", String.class);
    }

    // Extract all claims
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // Validate token (single argument)
    public boolean validateToken(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            return !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Optional: validate token against specific user
    public boolean validateToken(String token, User user) {
        String email = getEmailFromToken(token);
        return email.equals(user.getEmail()) && validateToken(token);
    }
}
