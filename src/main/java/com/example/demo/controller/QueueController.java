// src/main/java/com/example/demo/controller/QueueController.java
package com.example.demo.controller;

import com.example.demo.entity.QueuePosition;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.impl.QueueServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/queue")
public class QueueController {

    private final QueueServiceImpl queueService;

    public QueueController(QueuePositionRepository queueRepo, TokenRepository tokenRepo) {
        this.queueService = new QueueServiceImpl(queueRepo, tokenRepo);
    }

    @PostMapping("/{tokenId}")
    public QueuePosition updateQueue(@PathVariable Long tokenId, @RequestParam int position) {
        return queueService.updateQueuePosition(tokenId, position);
    }

    @GetMapping("/{tokenId}")
    public QueuePosition getQueue(@PathVariable Long tokenId) {
        return queueService.getPosition(tokenId);
    }
}
