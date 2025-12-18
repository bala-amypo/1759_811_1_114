package com.example.demo.controller;

import com.example.demo.service.QueueService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queue")
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @PutMapping("/position/{tokenId}/{newPosition}")
    public String updatePosition(
            @PathVariable Long tokenId,
            @PathVariable Integer newPosition
    ) {
        queueService.updateQueuePosition(tokenId, newPosition);
        return "Queue position updated";
    }

    @GetMapping("/position/{tokenId}")
    public Integer getPosition(@PathVariable Long tokenId) {
        return queueService.getPosition(tokenId);
    }
}