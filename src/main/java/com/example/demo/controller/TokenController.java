package com.example.demo.controller;

import com.example.demo.entity.Token;
import com.example.demo.entity.ServiceCounter;
import com.example.demo.entity.User;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import com.example.demo.service.ServiceCounterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Tokens")
@RestController
@RequestMapping("/tokens")
public class TokenController {

    private final TokenService tokenService;
    private final UserService userService;
    private final ServiceCounterService counterService;

    public TokenController(TokenService tokenService,
                           UserService userService,
                           ServiceCounterService counterService) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.counterService = counterService;
    }

    @Operation(summary = "Issue a new token for a user at a counter")
    @PostMapping("/issue/{userId}/{counterId}")
    public Token issueToken(@PathVariable Long userId, @PathVariable Long counterId) {
        User user = userService.login(userService.loginById(userId).getEmail()); // simplified
        ServiceCounter counter = counterService.getById(counterId);
        return tokenService.issueToken(user, counter);
    }

    @Operation(summary = "Update token status")
    @PutMapping("/status/{tokenId}")
    public Token updateStatus(@PathVariable Long tokenId, @RequestParam Token.Status status) {
        return tokenService.updateStatus(tokenId, status);
    }

    @Operation(summary = "Get token by ID")
    @GetMapping("/{tokenId}")
    public Token getToken(@PathVariable Long tokenId) {
        return tokenService.getById(tokenId);
    }
}
