package com.example.demo.controller;

import com.example.demo.entity.TokenLog;
import com.example.demo.service.TokenLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/token-logs")
public class TokenLogController {

    private final TokenLogService tokenLogService;

    public TokenLogController(TokenLogService tokenLogService) {
        this.tokenLogService = tokenLogService;
    }

    @PostMapping
    public ResponseEntity<TokenLog> addLog(
            @RequestParam Long tokenId,
            @RequestParam String message
    ) {
        return ResponseEntity.ok(
                tokenLogService.addLog(tokenId, message)
        );
    }

    @GetMapping("/{tokenId}")
    public ResponseEntity<List<TokenLog>> getLogs(
            @PathVariable Long tokenId
    ) {
        return ResponseEntity.ok(
                tokenLogService.getLogs(tokenId)
        );
    }
}
