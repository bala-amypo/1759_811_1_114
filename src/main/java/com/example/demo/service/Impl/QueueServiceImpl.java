package com.example.demo.service.impl;

import com.example.demo.entity.QueuePosition;
import com.example.demo.entity.Token;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.QueueService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QueueServiceImpl implements QueueService {

    private final QueuePositionRepository queueRepo;
    private final TokenRepository tokenRepo;

    public QueueServiceImpl(QueuePositionRepository queueRepo, TokenRepository tokenRepo) {
        this.queueRepo = queueRepo;
        this.tokenRepo = tokenRepo;
    }

    @Override
    public QueuePosition updateQueuePosition(Long tokenId, Integer position) {
        if (position < 1) throw new IllegalArgumentException("Position must be >= 1");

        Optional<Token> tOpt = tokenRepo.findById(tokenId);
        if (tOpt.isEmpty()) throw new RuntimeException("Token not found");

        QueuePosition qp = new QueuePosition();
        qp.setToken(tOpt.get());
        qp.setPosition(position);
        queueRepo.save(qp);
        return qp;
    }

    @Override
    public QueuePosition getPosition(Long tokenId) {
        return queueRepo.findByToken_Id(tokenId)
                .orElseThrow(() -> new RuntimeException("QueuePosition not found"));
    }
}
