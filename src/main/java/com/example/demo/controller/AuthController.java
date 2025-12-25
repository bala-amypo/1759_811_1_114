package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // ------------------ Register User ------------------
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Save user
        User registeredUser = userService.registerUser(user);

        // Return response
        return ResponseEntity.ok("User registered with email: " + registeredUser.getEmail());
    }

    // ------------------ Authenticate User ------------------
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok("Login successful for email: " + user.getEmail());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    // ------------------ DTO for Login ------------------
    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}