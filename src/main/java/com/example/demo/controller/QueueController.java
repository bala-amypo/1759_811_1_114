package com.example.demo.controller;

import com.example.demo.entity.QueuePosition;
import com.example.demo.service.impl.QueueServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queue")
public class QueueController {

    private final QueueServiceImpl queueService;

    public QueueController(QueueServiceImpl queueService) {
        this.queueService = queueService;
    }

    @GetMapping("/{tokenId}")
    public QueuePosition getQueuePosition(@PathVariable Long tokenId) {
        return queueService.getPosition(tokenId);
    }

    @PostMapping("/update/{tokenId}")
    public QueuePosition updateQueuePosition(@PathVariable Long tokenId, @RequestParam Integer position) {
        return queueService.updateQueuePosition(tokenId, position);
    }
}
