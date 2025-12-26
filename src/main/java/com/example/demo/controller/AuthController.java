package com.example.demo.controller;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(UserService userService, JwtTokenProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User created = userService.register(user);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        User existing = userService.findByEmail(user.getEmail());
        if (existing == null || !passwordEncoder.matches(user.getPassword(), existing.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
        String token = jwtProvider.generateToken(existing.getId(), existing.getEmail(), existing.getRole());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
