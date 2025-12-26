package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.config.JwtTokenProvider;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // ---------------------------
    // Register endpoint
    // ---------------------------
    @PostMapping("/register")
    public User register(@RequestParam String name,
                         @RequestParam String email,
                         @RequestParam String password) {

        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPassword(password);
        u.setRole("STAFF"); // default role

        return userService.register(u);
    }

    // ---------------------------
    // Login endpoint
    // ---------------------------
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password) {

        User u = userService.findByEmail(email);

        if (u == null || !userService.checkPassword(u, password)) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtTokenProvider.generateToken(u.getId(), u.getEmail(), u.getRole());
    }
}
