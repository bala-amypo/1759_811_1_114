package com.example.demo.controller;

import com.example.demo.entity.TokenLogEntity;
import com.example.demo.service.TokenLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class TokenLogController {

    private final TokenLogService tokenLogService;

    public TokenLogController(TokenLogService tokenLogService) {
        this.tokenLogService = tokenLogService;
    }

    @PostMapping("/{tokenId}")
    public TokenLogEntity addLog(
            @PathVariable Long tokenId,
            @RequestParam String message
    ) {
        return tokenLogService.addLog(tokenId, message);
    }

    @GetMapping("/{tokenId}")
    public List<TokenLogEntity> getLogs(@PathVariable Long tokenId) {
        return tokenLogService.getLogs(tokenId);
    }
}