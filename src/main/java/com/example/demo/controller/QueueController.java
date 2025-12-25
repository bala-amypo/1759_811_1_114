package com.example.demo.controller;

import com.example.demo.entity.QueuePosition;
import com.example.demo.service.QueueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/queue")
public class QueueController {

    private final QueueService queueService;

    // Constructor Injection
    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    // Update queue position
    @PutMapping("/position/{tokenId}/{newPosition}")
    public ResponseEntity<?> updatePosition(@PathVariable Long tokenId, @PathVariable int newPosition) {
        try {
            QueuePosition updated = queueService.updatePosition(tokenId, newPosition);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Get current queue position
    @GetMapping("/position/{tokenId}")
    public ResponseEntity<?> getPosition(@PathVariable Long tokenId) {
        try {
            QueuePosition position = queueService.getPosition(tokenId);
            return ResponseEntity.ok(position);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
