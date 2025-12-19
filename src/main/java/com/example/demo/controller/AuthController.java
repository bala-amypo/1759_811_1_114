package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ===== Register User =====
    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    // ===== Login User =====
    @Operation(summary = "Login user by email")
    @PostMapping("/login")
    public User login(@RequestBody LoginEmailRequest loginRequest) {
        return userService.login(loginRequest.getEmail());
    }

    // ===== Inner class for login request =====
    public static class LoginEmailRequest {
        private String email;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}
