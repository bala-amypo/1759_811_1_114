package com.example.demo.controller;

import com.example.demo.entity.TokenLog;
import com.example.demo.service.TokenLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class TokenLogController {

    private final TokenLogService logService;

    public TokenLogController(TokenLogService logService) {
        this.logService = logService;
    }

    // Add a log for a token
    @PostMapping("/{tokenId}")
    public ResponseEntity<TokenLog> addLog(@PathVariable Long tokenId,
                                           @RequestParam String message) {
        TokenLog log = logService.addLog(tokenId, message);
        return ResponseEntity.ok(log);
    }

    // Get all logs for a token
    @GetMapping("/{tokenId}/all")
    public ResponseEntity<List<TokenLog>> getLogs(@PathVariable Long tokenId) {
        List<TokenLog> logs = logService.getLogs(tokenId);
        return ResponseEntity.ok(logs);
    }
}
