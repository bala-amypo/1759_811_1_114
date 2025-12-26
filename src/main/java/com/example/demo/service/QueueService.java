// src/main/java/com/example/demo/service/QueueService.java
package com.example.demo.service;

import com.example.demo.entity.QueuePosition;

public interface QueueService {
    QueuePosition updateQueuePosition(Long tokenId, int newPosition);
    QueuePosition getPosition(Long tokenId);
}
