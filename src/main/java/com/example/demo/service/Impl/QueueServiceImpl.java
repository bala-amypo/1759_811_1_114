package com.example.demo.service;

import com.example.demo.model.QueuePosition;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class QueueServiceImpl implements QueueService {

    private final QueuePositionRepository queuePositionRepository;
    private final TokenRepository tokenRepository;

    // ✅ Constructor Injection (exact)
    public QueueServiceImpl(QueuePositionRepository queuePositionRepository,
                            TokenRepository tokenRepository) {
        this.queuePositionRepository = queuePositionRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public QueuePosition updateQueuePosition(Long tokenId, Integer newPosition) {
        if (newPosition < 1) {
            throw new RuntimeException("Position must be >= 1");
        }

        QueuePosition queuePosition = queuePositionRepository.findByToken_Id(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        queuePosition.setPosition(newPosition);
        queuePosition.setUpdatedAt(LocalDateTime.now());
        return queuePositionRepository.save(queuePosition);
    }

    @Override
    public QueuePosition getPosition(Long tokenId) {
        return queuePositionRepository.findByToken_Id(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }
}
