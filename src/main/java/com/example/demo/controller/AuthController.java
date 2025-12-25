package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // LOGIN: takes AuthRequest, returns AuthResponse
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        User user = userService.login(request.getUsername(), request.getPassword());
        if (user == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
        // You can generate a token if your system requires it, else return username only
        String token = user.getToken() != null ? user.getToken() : "dummy-token"; // replace with real token logic
        AuthResponse response = new AuthResponse(token, user.getUsername());
        return ResponseEntity.ok(response);
    }

    // REGISTER: takes RegisterRequest, returns AuthResponse or User info
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        // Map RegisterRequest to User entity
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        if (request.getEmail() != null) user.setEmail(request.getEmail());

        User registeredUser = userService.register(user);

        // Generate token if required (or dummy for now)
        String token = registeredUser.getToken() != null ? registeredUser.getToken() : "dummy-token";
        AuthResponse response = new AuthResponse(token, registeredUser.getUsername());

        return ResponseEntity.ok(response);
    }

    // LOGOUT: optional, using userId as path variable
    @PostMapping("/logout/{userId}")
    public ResponseEntity<String> logout(@PathVariable Long userId) {
        userService.logout(userId);
        return ResponseEntity.ok("Logged out successfully");
    }
}