package com.example.demo.service;

import com.example.demo.entity.QueuePosition;

public interface QueueService {
    QueuePosition getPosition(Long tokenId);
    QueuePosition updateQueuePosition(Long tokenId, int position);
}
