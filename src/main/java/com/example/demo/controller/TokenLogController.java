package com.example.demo.controller;

import com.example.demo.entity.TokenLog;
import com.example.demo.service.TokenLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/token-logs")
public class TokenLogController {

    private final TokenLogService logService;

    @Autowired
    public TokenLogController(TokenLogService logService) {
        this.logService = logService;
    }

    // ------------------ Add Log to a Token ------------------
    @PostMapping("/{tokenId}/add")
    public TokenLog addLog(@PathVariable Long tokenId,
                           @RequestParam String message) {
        return logService.addLog(tokenId, message);
    }

    // ------------------ Get Logs for a Token ------------------
    @GetMapping("/{tokenId}")
    public List<TokenLog> getLogs(@PathVariable Long tokenId) {
        return logService.getLogs(tokenId);
    }
}
