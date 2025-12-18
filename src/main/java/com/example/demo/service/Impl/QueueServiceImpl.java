package com.example.demo.service.impl;

import com.example.demo.entity.QueueEntity;
import com.example.demo.repository.QueueRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.QueueService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class QueueServiceImpl implements QueueService {

    private final QueueRepository queueRepository;
    private final TokenRepository tokenRepository;

    // ⚠️ Order matters
    public QueueServiceImpl(
            QueueRepository queueRepository,
            TokenRepository tokenRepository
    ) {
        this.queueRepository = queueRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void updateQueuePosition(Long tokenId, Integer newPosition) {

        if (newPosition < 1) {
            throw new RuntimeException("Position not valid");
        }

        QueueEntity queue = queueRepository.findByTokenId(tokenId)
                .orElseThrow(() -> new RuntimeException("Queue not found"));

        queue.setPosition(newPosition);
        queue.setUpdatedAt(LocalDateTime.now());

        queueRepository.save(queue);
    }

    @Override
    public Integer getPosition(Long tokenId) {
        return queueRepository.findByTokenId(tokenId)
                .orElseThrow(() -> new RuntimeException("Queue not found"))
                .getPosition();
    }
}