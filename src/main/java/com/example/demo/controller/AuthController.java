package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    // LOGIN: uses AuthRequest, returns AuthResponse with JWT
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // Generate JWT token
            String token = tokenProvider.generateToken(authentication);

            AuthResponse response = new AuthResponse(token, request.getUsername());
            return ResponseEntity.ok(response);

        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
    }

    // REGISTER: uses RegisterRequest, returns AuthResponse with JWT
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        // Create User entity from RegisterRequest
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        if (request.getEmail() != null) user.setEmail(request.getEmail());

        // Save user
        User registeredUser = userService.register(user);

        // Authenticate newly registered user to generate JWT
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String token = tokenProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse(token, registeredUser.getUsername());
        return ResponseEntity.ok(response);
    }

    // LOGOUT (optional, mainly for client-side token removal)
    @PostMapping("/logout/{userId}")
    public ResponseEntity<String> logout(@PathVariable Long userId) {
        userService.logout(userId);
        return ResponseEntity.ok("Logged out successfully");
    }
}