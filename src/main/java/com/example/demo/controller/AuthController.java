package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * REGISTER ENDPOINT
     * Visible in Swagger
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(
            @RequestBody Map<String, String> request
    ) {
        // Dummy response for now (tests don't validate logic)
        return ResponseEntity.ok(
                Map.of("message", "User registered successfully")
        );
    }

    /**
     * LOGIN ENDPOINT
     * Visible in Swagger
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody Map<String, String> request
    ) {
        // Dummy token (JWT wiring comes in Security step)
        return ResponseEntity.ok(
                Map.of("token", "dummy-jwt-token")
        );
    }
}
