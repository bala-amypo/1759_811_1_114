package com.example.demo.controller;

import com.example.demo.model.Token;
import com.example.demo.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tokens")
@Tag(name = "Tokens", description = "Manage tokens")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/issue/{counterId}")
    @Operation(summary = "Issue a new token for a counter")
    public ResponseEntity<Token> issueToken(@PathVariable Long counterId) {
        Token token = tokenService.issueToken(counterId);
        return ResponseEntity.ok(token);
    }

    @PutMapping("/status/{tokenId}")
    @Operation(summary = "Update token status")
    public ResponseEntity<Token> updateStatus(@PathVariable Long tokenId, @RequestParam String status) {
        Token token = tokenService.updateStatus(tokenId, status);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/{tokenId}")
    @Operation(summary = "Get token details")
    public ResponseEntity<Token> getToken(@PathVariable Long tokenId) {
        Token token = tokenService.getToken(tokenId);
        return ResponseEntity.ok(token);
    }
}
