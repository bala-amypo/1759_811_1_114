package com.example.demo.config;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String SECRET_KEY = "secret"; // replace with secure key in production
    private final long VALIDITY_MS = 3600000; // 1 hour validity
    private final UserService userService;

    // Constructor injection for UserService
    public JwtTokenProvider(UserService userService) {
        this.userService = userService;
    }

    /**
     * Extract JWT token from Authorization header
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Validate token
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Get Spring Security Authentication from JWT token
     */
    public Authentication getAuthentication(String token) {
        String email = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        try {
            User user = userService.findByEmail(email);
            return new UsernamePasswordAuthenticationToken(user, null, null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Generate JWT token for a given user
     */
    public String generateToken(User user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + VALIDITY_MS);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();
    }
}
