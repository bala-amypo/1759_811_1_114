package com.example.demo.controller;

import com.example.demo.entity.Token;
import com.example.demo.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    // ------------------------------
    // Issue token for a counter
    // ------------------------------
    @PostMapping("/issue/{counterId}")
    public ResponseEntity<Token> issueToken(@PathVariable Long counterId) {
        Token token = tokenService.issueToken(counterId);
        return ResponseEntity.ok(token);
    }

    // ------------------------------
    // Generate token (used by tests)
    // ------------------------------
    @PostMapping("/generate/{userId}/{counterId}")
    public ResponseEntity<Token> generateToken(@PathVariable Long userId,
                                               @PathVariable Long counterId) {
        Token token = tokenService.generateToken(userId, counterId);
        return ResponseEntity.ok(token);
    }

    // ------------------------------
    // Update token status
    // ------------------------------
    @PutMapping("/{tokenId}/status")
    public ResponseEntity<Token> updateStatus(@PathVariable Long tokenId,
                                              @RequestParam String status) {
        Token updated = tokenService.updateStatus(tokenId, status);
        return ResponseEntity.ok(updated);
    }

    // ------------------------------
    // Get token by ID
    // ------------------------------
    @GetMapping("/{tokenId}")
    public ResponseEntity<Token> getToken(@PathVariable Long tokenId) {
        Token token = tokenService.getToken(tokenId);
        return ResponseEntity.ok(token);
    }

    // ------------------------------
    // Get all tokens (used by tests)
    // ------------------------------
    @GetMapping("/all")
    public ResponseEntity<List<Token>> getAllTokens() {
        List<Token> tokens = tokenService.getAllTokens();
        return ResponseEntity.ok(tokens);
    }
}
