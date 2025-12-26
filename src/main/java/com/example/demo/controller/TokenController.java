package com.example.demo.controller;

import com.example.demo.entity.Token;
import com.example.demo.service.TokenService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    // Update token status
    @PutMapping("/{tokenId}/status")
    public Token updateStatus(@PathVariable Long tokenId,
                              @RequestParam String status) {
        return tokenService.updateStatus(tokenId, status);
    }

    // Get token by ID
    @GetMapping("/{tokenId}")
    public Token getToken(@PathVariable Long tokenId) {
        return tokenService.getToken(tokenId);
    }

    // Get token by token number (required by test cases)
    @GetMapping("/by-number/{tokenNumber}")
    public Optional<Token> findByTokenNumber(@PathVariable String tokenNumber) {
        return tokenService.findByTokenNumber(tokenNumber);
    }
}
