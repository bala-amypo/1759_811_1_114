package com.example.demo.controller;

import com.example.demo.entity.Token;
import com.example.demo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    // ------------------ Issue Token ------------------
    @PostMapping("/issue")
    public Token issueToken(@RequestParam Long counterId) {
        return tokenService.issueToken(counterId);
    }

    // ------------------ Update Status ------------------
    @PostMapping("/{tokenId}/status")
    public Token updateStatus(@PathVariable Long tokenId,
                              @RequestParam String status) {
        return tokenService.updateStatus(tokenId, status);
    }

    // ------------------ Get Token by ID ------------------
    @GetMapping("/{tokenId}")
    public Token getToken(@PathVariable Long tokenId) {
        return tokenService.getToken(tokenId);
    }

    // ------------------ Get Waiting Tokens for Counter ------------------
    @GetMapping("/waiting")
    public List<Token> getWaitingTokens(@RequestParam Long counterId) {
        return tokenService.getWaitingTokensForCounter(counterId);
    }

    // ------------------ Get Token by Number ------------------
    @GetMapping("/byNumber")
    public Token getTokenByNumber(@RequestParam String tokenNumber) {
        return tokenService.getTokenByNumber(tokenNumber);
    }
}
