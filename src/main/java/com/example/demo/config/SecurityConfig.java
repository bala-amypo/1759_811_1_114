package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // disable CSRF for simplicity
            .authorizeHttpRequests()
                // allow public access to Swagger UI and API docs
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                ).permitAll()
                // allow public access to AuthController endpoints
                .requestMatchers("/auth/**").permitAll()
                // all other endpoints require authentication
                .anyRequest().authenticated()
            .and()
            .httpBasic(); // optional for testing, can be removed if JWT used

        return http.build();
    }
}
