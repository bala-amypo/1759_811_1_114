package com.example.demo.service.impl;

import com.example.demo.entity.QueueEntity;
import com.example.demo.entity.TokenEntity;
import com.example.demo.repository.QueueRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.QueueService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class QueueServiceImpl implements QueueService {

    private final QueueRepository queueRepository;
    private final TokenRepository tokenRepository;

    public QueueServiceImpl(
            QueueRepository queueRepository,
            TokenRepository tokenRepository) {
        this.queueRepository = queueRepository;
        this.tokenRepository = tokenRepository;
    }

    public QueueEntity updateQueuePosition(Long tokenId, Integer newPosition) {
        if (newPosition < 1) {
            throw new RuntimeException("Position must be >= 1");
        }

        TokenEntity token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        QueueEntity queue = queueRepository.findByTokenId(tokenId)
                .orElse(new QueueEntity());

        queue.setToken(token);
        queue.setPosition(newPosition);
        queue.setUpdatedAt(LocalDateTime.now());

        return queueRepository.save(queue);
    }

    public QueueEntity getPosition(Long tokenId) {
        return queueRepository.findByTokenId(tokenId)
                .orElseThrow(() -> new RuntimeException("Queue not found"));
    }
}