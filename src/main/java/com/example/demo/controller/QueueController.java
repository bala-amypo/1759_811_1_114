package com.example.demo.controller;

import com.example.demo.entity.QueuePosition;
import com.example.demo.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queue")
public class QueueController {

    private final QueueService queueService;

    @Autowired
    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    // ------------------ Get Queue Position by Token ------------------
    @GetMapping("/{tokenId}")
    public QueuePosition getPosition(@PathVariable Long tokenId) {
        return queueService.getPosition(tokenId);
    }

    // ------------------ Update Queue Position ------------------
    @PostMapping("/{tokenId}/update")
    public QueuePosition updatePosition(@PathVariable Long tokenId,
                                        @RequestParam Integer position) {
        return queueService.updateQueuePosition(tokenId, position);
    }
}
