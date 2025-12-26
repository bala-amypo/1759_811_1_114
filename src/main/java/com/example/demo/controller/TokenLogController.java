package com.example.demo.controller;

import com.example.demo.entity.TokenLog;
import com.example.demo.service.TokenLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class TokenLogController {

    private final TokenLogService logService;

    public TokenLogController(TokenLogService logService) {
        this.logService = logService;
    }

    // Add a log entry for a token
    @PostMapping("/{tokenId}/add")
    public TokenLog addLog(@PathVariable Long tokenId,
                           @RequestParam String message) {
        return logService.addLog(tokenId, message);
    }

    // Get all logs for a token, ordered by timestamp
    @GetMapping("/{tokenId}")
    public List<TokenLog> getLogs(@PathVariable Long tokenId) {
        return logService.getLogs(tokenId);
    }
}
