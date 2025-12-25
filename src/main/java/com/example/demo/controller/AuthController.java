package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.config.JwtTokenProvider;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtProvider;

    // Constructor Injection
    public AuthController(UserService userService, JwtTokenProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User registered = userService.register(user);
            return ResponseEntity.ok(registered);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Login and generate JWT
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        try {
            String email = loginRequest.get("email");
            String password = loginRequest.get("password");

            User user = userService.findByEmail(email);
            if (!user.getPassword().equals(password)) {
                throw new Exception("Invalid credentials");
            }

            String token = jwtProvider.generateToken(user);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
