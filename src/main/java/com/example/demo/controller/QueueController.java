package com.example.demo.controller;

import com.example.demo.entity.Token;
import com.example.demo.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/queue")
public class QueueController {

    @Autowired
    private QueueService queueService;

    // Get the next token for a service counter
    @GetMapping("/next/{counterId}")
    public ResponseEntity<Token> getNextToken(@PathVariable Long counterId) {
        Token nextToken = queueService.getNextToken(counterId);
        if (nextToken == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(nextToken);
    }

    // Get all waiting tokens in queue for a counter
    @GetMapping("/all/{counterId}")
    public ResponseEntity<List<Token>> getQueue(@PathVariable Long counterId) {
        List<Token> tokens = queueService.getQueue(counterId);
        return ResponseEntity.ok(tokens);
    }

    // Complete a token (remove from queue)
    @PutMapping("/complete/{tokenId}")
    public ResponseEntity<Void> completeToken(@PathVariable Long tokenId) {
        queueService.removeTokenFromQueue(tokenId);
        return ResponseEntity.ok().build();
    }
}