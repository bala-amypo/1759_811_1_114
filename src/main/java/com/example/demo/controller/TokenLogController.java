package com.example.demo.controller;

import com.example.demo.entity.TokenLog;
import com.example.demo.service.TokenLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/token-logs")
public class TokenLogController {

    @Autowired
    private TokenLogService tokenLogService;

    // Log an action for a token
    @PostMapping("/log/{tokenId}")
    public ResponseEntity<TokenLog> logAction(@PathVariable Long tokenId,
                                              @RequestParam String action,
                                              @RequestParam(required = false) String performedBy) {
        TokenLog log = tokenLogService.logAction(tokenId, action, performedBy != null ? performedBy : "System");
        return ResponseEntity.ok(log);
    }

    // Get logs for a specific token
    @GetMapping("/token/{tokenId}")
    public ResponseEntity<List<TokenLog>> getLogsByToken(@PathVariable Long tokenId) {
        List<TokenLog> logs = tokenLogService.getLogsByToken(tokenId);
        return ResponseEntity.ok(logs);
    }

    // Get all token logs
    @GetMapping("/all")
    public ResponseEntity<List<TokenLog>> getAllLogs() {
        List<TokenLog> logs = tokenLogService.getAllLogs();
        return ResponseEntity.ok(logs);
    }
}