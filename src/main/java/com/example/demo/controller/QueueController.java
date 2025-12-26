package com.example.demo.controller;

import com.example.demo.entity.QueuePosition;
import com.example.demo.service.QueueService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queue")
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    // Get the current position of a token
    @GetMapping("/{tokenId}")
    public QueuePosition getPosition(@PathVariable Long tokenId) {
        return queueService.getPosition(tokenId);
    }

    // Update the position of a token in the queue
    @PutMapping("/{tokenId}/position")
    public QueuePosition updatePosition(@PathVariable Long tokenId,
                                        @RequestParam Integer position) {
        return queueService.updateQueuePosition(tokenId, position);
    }
}
