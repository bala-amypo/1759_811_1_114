package com.example.demo.service.impl;

import com.example.demo.entity.QueuePosition;
import com.example.demo.entity.Token;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.QueueService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QueueServiceImpl implements QueueService {

    private final QueuePositionRepository queueRepository;
    private final TokenRepository tokenRepository;

    // Constructor Injection
    public QueueServiceImpl(QueuePositionRepository queueRepository, TokenRepository tokenRepository) {
        this.queueRepository = queueRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public QueuePosition updateQueuePosition(Long tokenId, int newPosition) throws Exception {
        if (newPosition < 1) {
            throw new Exception("Position must be >= 1");
        }

        QueuePosition queuePosition = queueRepository.findByToken_Id(tokenId);
        if (queuePosition == null) {
            Token token = tokenRepository.findById(tokenId)
                    .orElseThrow(() -> new Exception("not found"));
            queuePosition = new QueuePosition(token, newPosition);
        } else {
            queuePosition.setPosition(newPosition);
        }

        queuePosition.setUpdatedAt(java.time.LocalDateTime.now());
        return queueRepository.save(queuePosition);
    }

    @Override
    public QueuePosition getPosition(Long tokenId) throws Exception {
        QueuePosition queuePosition = queueRepository.findByToken_Id(tokenId);
        if (queuePosition == null) {
            throw new Exception("not found");
        }
        return queuePosition;
    }
}
