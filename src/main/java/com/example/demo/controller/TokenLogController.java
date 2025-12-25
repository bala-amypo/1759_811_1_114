package com.example.demo.controller;

import com.example.demo.entity.TokenLog;
import com.example.demo.service.TokenLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/logs")
public class TokenLogController {

    private final TokenLogService tokenLogService;

    // Constructor Injection
    public TokenLogController(TokenLogService tokenLogService) {
        this.tokenLogService = tokenLogService;
    }

    // Create a new log
    @PostMapping("/{tokenId}")
    public ResponseEntity<?> createLog(@PathVariable Long tokenId, @RequestBody Map<String, String> body) {
        try {
            String message = body.get("message");
            TokenLog log = tokenLogService.createLog(tokenId, message);
            return ResponseEntity.ok(log);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // List all logs for a token
    @GetMapping("/{tokenId}")
    public ResponseEntity<?> getLogs(@PathVariable Long tokenId) {
        try {
            List<TokenLog> logs = tokenLogService.getLogs(tokenId);
            return ResponseEntity.ok(logs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
