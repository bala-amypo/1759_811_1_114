package com.example.demo.config;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.security.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String header = request.getHeader("Authorization");
            String token = null;
            if (header != null && header.startsWith("Bearer ")) {
                token = header.substring(7);
            }

            if (token != null && tokenProvider.validateToken(token)) {
                String email = tokenProvider.getUsernameFromToken(token);

                // Fetch user from your existing UserService
                User user = userService.findByEmail(email); // adjust if using username

                if (user != null) {
                    // Create authentication and set context
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
                            );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }

        } catch (Exception ex) {
            // Optionally log the exception
            System.out.println("Could not set user authentication: " + ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}