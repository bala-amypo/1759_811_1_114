package com.example.demo.service;

import com.example.demo.entity.QueuePosition;

public interface QueueService {

    QueuePosition updatePosition(Long tokenId, int newPosition) throws Exception;

    QueuePosition getPosition(Long tokenId) throws Exception;
}
