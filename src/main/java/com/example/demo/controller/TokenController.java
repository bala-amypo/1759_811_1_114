package com.example.demo.controller;

import com.example.demo.entity.Token;
import com.example.demo.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    private final TokenService tokenService;

    // Constructor Injection
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    // Issue a new token for a counter
    @PostMapping("/issue/{counterId}")
    public ResponseEntity<?> issueToken(@PathVariable Long counterId) {
        try {
            Token token = tokenService.issueToken(counterId);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Update token status
    @PutMapping("/status/{tokenId}")
    public ResponseEntity<?> updateStatus(@PathVariable Long tokenId, @RequestBody Map<String, String> body) {
        try {
            String statusStr = body.get("status");
            Token.Status newStatus = Token.Status.valueOf(statusStr.toUpperCase());
            Token token = tokenService.updateStatus(tokenId, newStatus);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid status"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Get token details
    @GetMapping("/{tokenId}")
    public ResponseEntity<?> getToken(@PathVariable Long tokenId) {
        try {
            Token token = tokenService.getToken(tokenId);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
