package com.example.demo.controller;

import com.example.demo.entity.QueueEntity;
import com.example.demo.service.QueueService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queue")
@Tag(name = "Queue API")
public class QueueController {

    private final QueueService service;

    public QueueController(QueueService service) {
        this.service = service;
    }

    @PutMapping("/position/{tokenId}/{newPosition}")
    public QueueEntity update(
            @PathVariable Long tokenId,
            @PathVariable Integer newPosition) {
        return service.updateQueuePosition(tokenId, newPosition);
    }

    @GetMapping("/position/{tokenId}")
    public QueueEntity get(@PathVariable Long tokenId) {
        return service.getPosition(tokenId);
    }
}