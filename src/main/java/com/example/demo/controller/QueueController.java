package com.example.demo.controller;

import com.example.demo.entity.QueuePosition;
import com.example.demo.service.QueueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queue")
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @PutMapping("/{tokenId}")
    public ResponseEntity<QueuePosition> updateQueuePosition(
            @PathVariable Long tokenId,
            @RequestParam int position
    ) {
        return ResponseEntity.ok(
                queueService.updateQueuePosition(tokenId, position)
        );
    }

    @GetMapping("/{tokenId}")
    public ResponseEntity<QueuePosition> getQueuePosition(
            @PathVariable Long tokenId
    ) {
        return ResponseEntity.ok(
                queueService.getPosition(tokenId)
        );
    }
}
