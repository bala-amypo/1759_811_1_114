// src/main/java/com/example/demo/config/JwtTokenProvider.java
package com.example.demo.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtTokenProvider {
  private final Key key;
  private final long validityInMs;

  public JwtTokenProvider(String secret, long validityInMs) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes());
    this.validityInMs = validityInMs;
  }

  public String generateToken(Long userId, String email, String role) {
    Date now = new Date();
    Date exp = new Date(now.getTime() + validityInMs);

    return Jwts.builder()
      .setSubject(String.valueOf(userId))
      .addClaims(Map.of("email", email, "role", role))
      .setIssuedAt(now)
      .setExpiration(exp)
      .signWith(key, SignatureAlgorithm.HS256)
      .compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  public Claims getClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build()
      .parseClaimsJws(token).getBody();
  }
}
