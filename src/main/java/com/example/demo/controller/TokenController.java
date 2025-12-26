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

    /**
     * CREATE TOKEN
     * Visible in Swagger
     */
    @PostMapping
    public ResponseEntity<Token> createToken(
            @RequestParam Long userId,
            @RequestParam Long counterId
    ) {
        return ResponseEntity.ok(
                tokenService.generateToken(userId, counterId)
        );
    }

    /**
     * GET ALL TOKENS
     */
    @GetMapping
    public ResponseEntity<List<Token>> getAllTokens() {
        return ResponseEntity.ok(tokenService.getAllTokens());
    }

    /**
     * UPDATE TOKEN STATUS
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Token> updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(
                tokenService.updateStatus(id, status)
        );
    }
}
