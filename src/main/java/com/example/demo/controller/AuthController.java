package com.example.demo.controller;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // ------------------ Register ------------------
    @PostMapping("/register")
    public User register(@RequestParam String name,
                         @RequestParam String email,
                         @RequestParam String password,
                         @RequestParam(defaultValue = "STAFF") String role) {

        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPassword(password);
        u.setRole(role);

        return userService.register(u); // hashed inside service
    }

    // ------------------ Login ------------------
    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String email,
                                     @RequestParam String password) {

        User user = userService.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("Invalid email or password");
        }

        if (!userService.checkPassword(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail(), user.getRole());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
}
