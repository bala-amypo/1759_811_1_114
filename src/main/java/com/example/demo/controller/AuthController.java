package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.config.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtProvider;

    @Autowired
    public AuthController(UserService userService, JwtTokenProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    // ------------------ Register User ------------------
    @PostMapping("/register")
    public User register(@RequestParam String email,
                         @RequestParam String password,
                         @RequestParam(defaultValue = "STAFF") String role,
                         @RequestParam(required = false) String name) {
        User u = new User();
        u.setEmail(email);
        u.setPassword(password);
        u.setRole(role);
        u.setName(name);
        return userService.register(u);
    }

    // ------------------ Login User ------------------
    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String email,
                                     @RequestParam String password) {
        User user = userService.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!userService.checkPassword(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtProvider.generateToken(user.getId(), user.getEmail(), user.getRole());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
}
