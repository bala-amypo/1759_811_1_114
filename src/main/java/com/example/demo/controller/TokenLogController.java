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

    // Get all logs
    @GetMapping
    public ResponseEntity<List<TokenLog>> getAllLogs() {
        return ResponseEntity.ok(tokenLogService.getAllLogs());
    }

    // Add a new log message for a token
    @PostMapping("/{tokenId}")
    public ResponseEntity<TokenLog> logMessage(
            @PathVariable Long tokenId,
            @RequestParam String message
    ) {
        TokenLog log = tokenLogService.logMessage(tokenId, message);
        return ResponseEntity.ok(log);
    }
}
