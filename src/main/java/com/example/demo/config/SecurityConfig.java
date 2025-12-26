// src/main/java/com/example/demo/config/SecurityConfig.java
package com.example.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.jsonwebtoken.Claims;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.List;

@Configuration
public class SecurityConfig {

  // Use same secret and validity that tests use
  private final JwtTokenProvider jwt = new JwtTokenProvider(
    "ChangeThisSecretKeyReplaceMe1234567890", 3600000);

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
        // Public endpoints: auth + swagger
        .requestMatchers("/api/auth/**").permitAll()
        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
        // Everything else requires JWT
        .anyRequest().authenticated()
      )
      .addFilterBefore(new JwtAuthFilter(jwt), UsernamePasswordAuthenticationFilter.class)
      .httpBasic(Customizer.withDefaults()); // not used; no login page or basic auth

    return http.build();
  }

  static class JwtAuthFilter extends org.springframework.web.filter.OncePerRequestFilter {
    private final JwtTokenProvider jwt;

    JwtAuthFilter(JwtTokenProvider jwt) { this.jwt = jwt; }

    @Override
    protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain
    ) throws ServletException, IOException {
      String header = request.getHeader("Authorization");
      if (header != null && header.startsWith("Bearer ")) {
        String token = header.substring(7);
        if (jwt.validateToken(token)) {
          Claims claims = jwt.getClaims(token);
          String role = claims.get("role", String.class);
          List<GrantedAuthority> auths = List.of(new SimpleGrantedAuthority("ROLE_" + role));
          Authentication auth = new UsernamePasswordAuthenticationToken(
            claims.getSubject(), null, auths);
          org.springframework.security.core.context.SecurityContextHolder.getContext()
            .setAuthentication(auth);
        }
      }
      chain.doFilter(request, response);
    }
  }
}
