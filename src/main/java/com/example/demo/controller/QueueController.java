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

    @GetMapping("/{tokenId}")
    public ResponseEntity<QueuePosition> getPosition(@PathVariable Long tokenId) {
        QueuePosition qp = queueService.getPosition(tokenId);
        return ResponseEntity.ok(qp);
    }

    @PutMapping("/{tokenId}")
    public ResponseEntity<QueuePosition> updatePosition(@PathVariable Long tokenId,
                                                        @RequestParam int position) {
        QueuePosition qp = queueService.updateQueuePosition(tokenId, position);
        return ResponseEntity.ok(qp);
    }
}
