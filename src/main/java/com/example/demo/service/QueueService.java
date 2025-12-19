package com.example.demo.service;

import com.example.demo.entity.Queue;
import com.example.demo.entity.Token;

public interface QueueService {

    Queue updatePosition(Token token, Integer newPosition);

    Queue getByTokenId(Long tokenId);
}
