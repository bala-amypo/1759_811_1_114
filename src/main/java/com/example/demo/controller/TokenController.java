package com.example.demo.controller;

import com.example.demo.entity.TokenEntity;
import com.example.demo.service.TokenService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/issue/{counterId}")
    public TokenEntity issueToken(@PathVariable Long counterId) {
        return tokenService.issueToken(counterId);
    }

    @PutMapping("/status/{tokenId}")
    public TokenEntity updateStatus(
            @PathVariable Long tokenId,
            @RequestParam String status
    ) {
        return tokenService.updateStatus(tokenId, status);
    }

    @GetMapping("/{tokenId}")
    public TokenEntity getToken(@PathVariable Long tokenId) {
        return tokenService.getToken(tokenId);
    }
}