package com.example.demo.controller;

import com.example.demo.entity.TokenLogEntity;
import com.example.demo.service.TokenLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
@Tag(name = "Token Log API")
public class TokenLogController {

    private final TokenLogService service;

    public TokenLogController(TokenLogService service) {
        this.service = service;
    }

    @PostMapping("/{tokenId}")
    public TokenLogEntity add(
            @PathVariable Long tokenId,
            @RequestParam String message) {
        return service.addLog(tokenId, message);
    }

    @GetMapping("/{tokenId}")
    public List<TokenLogEntity> get(@PathVariable Long tokenId) {
        return service.getLogs(tokenId);
    }
}