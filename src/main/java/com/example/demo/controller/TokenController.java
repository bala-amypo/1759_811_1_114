package com.example.demo.controller;

import com.example.demo.entity.Token;
import com.example.demo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    // Create token for a counter
    @PostMapping("/create/{counterId}")
    public ResponseEntity<Token> createToken(@PathVariable Long counterId) {
        Token token = tokenService.createToken(counterId);
        return ResponseEntity.ok(token);
    }

    // Get token by ID
    @GetMapping("/{id}")
    public ResponseEntity<Token> getTokenById(@PathVariable Long id) {
        Token token = tokenService.getTokenById(id);
        return ResponseEntity.ok(token);
    }

    // Get all tokens
    @GetMapping("/all")
    public ResponseEntity<List<Token>> getAllTokens() {
        List<Token> tokens = tokenService.getAllTokens();
        return ResponseEntity.ok(tokens);
    }

    // Update token status
    @PutMapping("/status/{tokenId}")
    public ResponseEntity<Token> updateTokenStatus(@PathVariable Long tokenId, @RequestParam String status) {
        Token updatedToken = tokenService.updateTokenStatus(tokenId, status);
        return ResponseEntity.ok(updatedToken);
    }

    // Delete token
    @DeleteMapping("/delete/{tokenId}")
    public ResponseEntity<Void> deleteToken(@PathVariable Long tokenId) {
        tokenService.deleteToken(tokenId);
        return ResponseEntity.ok().build();
    }
}