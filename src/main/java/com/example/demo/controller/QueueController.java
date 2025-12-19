package com.example.demo.controller;

import com.example.demo.entity.Queue;
import com.example.demo.entity.Token;
import com.example.demo.service.QueueService;
import com.example.demo.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Queue")
@RestController
@RequestMapping("/queue")
public class QueueController {

    private final QueueService queueService;
    private final TokenService tokenService;

    public QueueController(QueueService queueService, TokenService tokenService) {
        this.queueService = queueService;
        this.tokenService = tokenService;
    }

    @Operation(summary = "Update position of a token in queue")
    @PutMapping("/{tokenId}/{newPosition}")
    public Queue updatePosition(@PathVariable Long tokenId, @PathVariable Integer newPosition) {
        Token token = tokenService.getById(tokenId);
        return queueService.updatePosition(token, newPosition);
    }

    @Operation(summary = "Get queue entry by token")
    @GetMapping("/{tokenId}")
    public Queue getQueueByToken(@PathVariable Long tokenId) {
        return queueService.getByTokenId(tokenId);
    }
}
