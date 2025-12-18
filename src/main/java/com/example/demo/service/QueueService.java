package com.example.demo.service;

import com.example.demo.entity.QueueEntity;

public interface QueueService {
    QueueEntity updateQueuePosition(Long tokenId, Integer newPosition);
    QueueEntity getPosition(Long tokenId);
}