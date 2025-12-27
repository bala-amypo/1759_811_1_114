
package com.example.demo.controller;

import com.example.demo.entity.TokenLog;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.impl.TokenLogServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class TokenLogController {

    private final TokenLogServiceImpl logService;

    public TokenLogController(TokenLogRepository logRepo, TokenRepository tokenRepo) {
        this.logService = new TokenLogServiceImpl(logRepo, tokenRepo);
    }

    @PostMapping("/{tokenId}")
    public TokenLog addLog(@PathVariable Long tokenId, @RequestParam String message) {
        return logService.addLog(tokenId, message);
    }

    @GetMapping("/{tokenId}")
    public List<TokenLog> getLogs(@PathVariable Long tokenId) {
        return logService.getLogs(tokenId);
    }
}
