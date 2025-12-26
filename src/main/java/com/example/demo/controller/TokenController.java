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

    @PostMapping("/issue/{counterId}")
    public ResponseEntity<Token> issueToken(@PathVariable Long counterId) {
        Token token = tokenService.issueToken(counterId);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/{tokenId}")
    public ResponseEntity<Token> getToken(@PathVariable Long tokenId) {
        Token token = tokenService.getToken(tokenId);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Token>> getAllTokens() {
        List<Token> tokens = tokenService.getAllTokens();
        return ResponseEntity.ok(tokens);
    }

    @PutMapping("/{tokenId}/status")
    public ResponseEntity<Token> updateStatus(@PathVariable Long tokenId, @RequestParam String status) {
        Token updated = tokenService.updateStatus(tokenId, status);
        return ResponseEntity.ok(updated);
    }
}
