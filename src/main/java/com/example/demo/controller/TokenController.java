// src/main/java/com/example/demo/controller/TokenController.java
package com.example.demo.controller;

import com.example.demo.entity.QueuePosition;
import com.example.demo.entity.Token;
import com.example.demo.entity.TokenLog;
import com.example.demo.repository.*;
import com.example.demo.service.impl.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tokens")
public class TokenController {

    private final TokenServiceImpl tokenService;
    private final QueueServiceImpl queueService;
    private final TokenLogServiceImpl logService;

    public TokenController(TokenRepository tokenRepo,
                           ServiceCounterRepository counterRepo,
                           TokenLogRepository logRepo,
                           QueuePositionRepository queueRepo) {
        this.tokenService = new TokenServiceImpl(tokenRepo, counterRepo, logRepo, queueRepo);
        this.queueService = new QueueServiceImpl(queueRepo, tokenRepo);
        this.logService = new TokenLogServiceImpl(logRepo, tokenRepo);
    }

    @PostMapping("/issue/{counterId}")
    public Token issue(@PathVariable Long counterId) {
        return tokenService.issueToken(counterId);
    }

    @PostMapping("/{tokenId}/status")
    public Token updateStatus(@PathVariable Long tokenId, @RequestParam String status) {
        return tokenService.updateStatus(tokenId, status);
    }

    @PostMapping("/{tokenId}/queue")
    public QueuePosition updateQueue(@PathVariable Long tokenId, @RequestParam int position) {
        return queueService.updateQueuePosition(tokenId, position);
    }

    @GetMapping("/{tokenId}/queue")
    public QueuePosition getQueue(@PathVariable Long tokenId) {
        return queueService.getPosition(tokenId);
    }

    @PostMapping("/{tokenId}/logs")
    public TokenLog addLog(@PathVariable Long tokenId, @RequestParam String message) {
        return logService.addLog(tokenId, message);
    }

    @GetMapping("/{tokenId}/logs")
    public List<TokenLog> getLogs(@PathVariable Long tokenId) {
        return logService.getLogs(tokenId);
    }
}
