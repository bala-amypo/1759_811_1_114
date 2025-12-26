package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.config.JwtTokenProvider;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // -------------------------
    // Register user
    // -------------------------
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        // Set default role if not provided
        if (user.getRole() == null) {
            user.setRole("STAFF");
        }
        User created = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // -------------------------
    // Login
    // -------------------------
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        User found = userService.findByEmail(user.getEmail());
        if (found == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email");
        }

        // Check password (assuming userService.register hashes passwords)
        if (!userService.checkPassword(user.getPassword(), found.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }

        String token = jwtTokenProvider.generateToken(found.getId(), found.getEmail(), found.getRole());
        return ResponseEntity.ok(token);
    }
}
