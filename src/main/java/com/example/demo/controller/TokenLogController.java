package com.example.demo.controller;

import com.example.demo.entity.TokenLog;
import com.example.demo.service.impl.TokenLogServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class TokenLogController {

    private final TokenLogServiceImpl logService;

    public TokenLogController(TokenLogServiceImpl logService) {
        this.logService = logService;
    }

    @PostMapping("/add/{tokenId}")
    public TokenLog addLog(@PathVariable Long tokenId, @RequestParam String message) {
        return logService.addLog(tokenId, message);
    }

    @GetMapping("/{tokenId}")
    public List<TokenLog> getLogs(@PathVariable Long tokenId) {
        return logService.getLogs(tokenId);
    }
}
