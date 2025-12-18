package com.example.demo.controller;

import com.example.demo.entity.TokenEntity;
import com.example.demo.service.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tokens")
@Tag(name = "Token API")
public class TokenController {

    private final TokenService service;

    public TokenController(TokenService service) {
        this.service = service;
    }

    @PostMapping("/issue/{counterId}")
    public TokenEntity issue(@PathVariable Long counterId) {
        return service.issueToken(counterId);
    }

    @PutMapping("/status/{tokenId}")
    public TokenEntity update(
            @PathVariable Long tokenId,
            @RequestParam String status) {
        return service.updateStatus(tokenId, status);
    }

    @GetMapping("/{tokenId}")
    public TokenEntity get(@PathVariable Long tokenId) {
        return service.getToken(tokenId);
    }
}