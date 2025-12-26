package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        // Ensure role is set to default if missing
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("STAFF");
        }
        return userService.register(user);
    }
}
