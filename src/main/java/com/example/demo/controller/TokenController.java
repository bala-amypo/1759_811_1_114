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

    // Issue a token for a counter
    @PostMapping("/issue/{counterId}")
    public Token issueToken(@PathVariable Long counterId) {
        return tokenService.issueToken(counterId);
    }

    // Update status of a token
    @PutMapping("/{tokenId}/status")
    public Token updateStatus(@PathVariable Long tokenId, @RequestParam String status) {
        return tokenService.updateStatus(tokenId, status);
    }

    // Get token by ID
    @GetMapping("/{tokenId}")
    public Token getToken(@PathVariable Long tokenId) {
        return tokenService.getToken(tokenId);
    }

    // Optional: find by token number (for Swagger clarity)
    @GetMapping("/by-number/{tokenNumber}")
    public Token getTokenByNumber(@PathVariable String tokenNumber) {
        return tokenService.findByTokenNumber(tokenNumber)
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }
}
