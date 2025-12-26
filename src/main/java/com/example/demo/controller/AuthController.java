package com.example.demo.controller;

import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register user")
    @PostMapping("/register")
    public String register(
            @RequestBody(
                    description = "User registration",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    example = "{ \"name\": \"John\", \"email\": \"john@mail.com\", \"password\": \"1234\", \"role\": \"STAFF\" }"
                            )
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody Map<String, String> body
    ) {
        return "User registered";
    }

    @Operation(summary = "Login user")
    @PostMapping("/login")
    public String login(
            @RequestBody(
                    description = "Login credentials",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    example = "{ \"email\": \"john@mail.com\", \"password\": \"1234\" }"
                            )
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody Map<String, String> body
    ) {
        return "Login successful";
    }
}
