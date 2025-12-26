package com.example.demo.controller;

import com.example.demo.entity.QueuePosition;
import com.example.demo.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queue")
public class QueueController {

    @Autowired
    private QueueService queueService;

    @PutMapping("/{tokenId}")
    public QueuePosition updatePosition(@PathVariable Long tokenId,
                                        @RequestParam int position) {
        return queueService.updateQueuePosition(tokenId, position);
    }

    @GetMapping("/{tokenId}")
    public QueuePosition getPosition(@PathVariable Long tokenId) {
        return queueService.getPosition(tokenId);
    }
}
