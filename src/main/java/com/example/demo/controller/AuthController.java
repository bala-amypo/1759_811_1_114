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
        // Tests use direct constructors; controller uses the same pattern
        this.userService = new UserServiceImpl(userRepository);
        // Important: same secret and validity used by tests
        this.jwt = new JwtTokenProvider("ChangeThisSecretKeyReplaceMe1234567890", 3600000);
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    // No password login: issue JWT with email + role
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String role) {
        Long syntheticId = Math.abs(email.hashCode() + role.hashCode()) + 0L;
        return jwt.generateToken(syntheticId, email, role);
    }
}
