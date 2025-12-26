package com.example.demo.service;

import com.example.demo.entity.QueuePosition;

public interface QueueService {

    QueuePosition updateQueuePosition(Long tokenId, int position);

    QueuePosition getPosition(Long tokenId);
}
