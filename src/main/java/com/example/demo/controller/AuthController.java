// src/main/java/com/example/demo/controller/AuthController.java
package com.example.demo.controller;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserServiceImpl userService;
    private final JwtTokenProvider jwt;

    public AuthController(UserRepository userRepository) {
        this.userService = new UserServiceImpl(userRepository);
        this.jwt = new JwtTokenProvider("ChangeThisSecretKeyReplaceMe1234567890", 3600000);
    }

    /**
     * Register a new user and immediately return a JWT token.
     */
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        User created = userService.register(user);
        return jwt.generateToken(created.getId(), created.getEmail(), created.getRole());
    }

    /**
     * Login using email + password, return JWT if valid.
     */
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        User found = userService.findByEmail(email);
        if (found == null) {
            throw new RuntimeException("User not found");
        }
        // Compare hashed password
        String hashed = Integer.toHexString((password + "_salt").hashCode());
        if (!found.getPassword().equals(hashed)) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwt.generateToken(found.getId(), found.getEmail(), found.getRole());
    }
}
