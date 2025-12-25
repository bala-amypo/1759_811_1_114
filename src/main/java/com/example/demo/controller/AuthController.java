package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());

        User registeredUser = userService.registerUser(user);

        String token = tokenProvider.generateToken(registeredUser.getUsername());

        AuthResponse response = new AuthResponse(registeredUser.getUsername(),
                                                 registeredUser.getEmail(),
                                                 token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest request) {

        User user = userService.authenticate(request.getUsername(), request.getPassword());

        String token = tokenProvider.generateToken(user.getUsername());

        AuthResponse response = new AuthResponse(user.getUsername(),
                                                 user.getEmail(),
                                                 token);

        return ResponseEntity.ok(response);
    }
}