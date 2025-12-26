package com.example.demo.controller;

import com.example.demo.entity.Token;
import com.example.demo.service.TokenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    // Issue a new token for a counter
    @PostMapping("/issue/{counterId}")
    public Token issueToken(@PathVariable Long counterId) {
        return tokenService.issueToken(counterId);
    }

    // Update token status (WAITING -> SERVING -> COMPLETED/CANCELLED)
    @PutMapping("/{tokenId}/status")
    public Token updateStatus(@PathVariable Long tokenId,
                              @RequestParam String status) {
        return tokenService.updateStatus(tokenId, status);
    }

    // Get a token by its ID
    @GetMapping("/{tokenId}")
    public Token getToken(@PathVariable Long tokenId) {
        return tokenService.getToken(tokenId);
    }

    // Find token by token number (used in test t45)
    @GetMapping("/by-number")
    public Token findByTokenNumber(@RequestParam String tokenNumber) {
        return tokenService.getTokenByNumber(tokenNumber);
    }

    // Get all waiting tokens for a counter
    @GetMapping("/waiting/{counterId}")
    public List<Token> getWaitingTokens(@PathVariable Long counterId) {
        return tokenService.getWaitingTokens(counterId);
    }
}
