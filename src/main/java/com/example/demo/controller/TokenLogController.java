package com.example.demo.controller;

import com.example.demo.entity.Token;
import com.example.demo.entity.TokenLog;
import com.example.demo.service.TokenLogService;
import com.example.demo.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Token Logs")
@RestController
@RequestMapping("/logs")
public class TokenLogController {

    private final TokenLogService tokenLogService;
    private final TokenService tokenService;

    public TokenLogController(TokenLogService tokenLogService,
                              TokenService tokenService) {
        this.tokenLogService = tokenLogService;
        this.tokenService = tokenService;
    }

    @Operation(summary = "Create a manual log for a token")
    @PostMapping("/{tokenId}")
    public TokenLog createLog(@PathVariable Long tokenId, @RequestParam String message) {
        Token token = tokenService.getById(tokenId);
        return tokenLogService.createLog(token, message);
    }

    @Operation(summary = "Get all logs for a token")
    @GetMapping("/{tokenId}")
    public List<TokenLog> getLogs(@PathVariable Long tokenId) {
        return tokenLogService.getLogsByTokenId(tokenId);
    }
}
