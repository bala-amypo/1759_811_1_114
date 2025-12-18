package com.example.demo.service.impl;

import com.example.demo.entity.QueuePositionEntity;
import com.example.demo.entity.ServiceCounterEntity;
import com.example.demo.entity.TokenEntity;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.TokenService;
import com.example.demo.util.TokenNumberGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final ServiceCounterRepository serviceCounterRepository;
    private final TokenLogRepository tokenLogRepository;
    private final QueuePositionRepository queuePositionRepository;

    // ⚠️ DO NOT change order – test depends on this
    public TokenServiceImpl(
            TokenRepository tokenRepository,
            ServiceCounterRepository serviceCounterRepository,
            TokenLogRepository tokenLogRepository,
            QueuePositionRepository queuePositionRepository
    ) {
        this.tokenRepository = tokenRepository;
        this.serviceCounterRepository = serviceCounterRepository;
        this.tokenLogRepository = tokenLogRepository;
        this.queuePositionRepository = queuePositionRepository;
    }

    @Override
    public TokenEntity issueToken(Long counterId) {

        ServiceCounterEntity counter = serviceCounterRepository.findById(counterId)
                .orElseThrow(() -> new RuntimeException("Counter not found"));

        if (!Boolean.TRUE.equals(counter.getIsActive())) {
            throw new RuntimeException("Counter not active");
        }

        TokenEntity token = new TokenEntity();
        token.setTokenNumber(TokenNumberGenerator.generateTokenNumber());
        token.setStatus(TokenEntity.TokenStatus.WAITING);
        token.setCreatedAt(LocalDateTime.now());

        token = tokenRepository.save(token);

        QueuePositionEntity queue = new QueuePositionEntity();
        queue.setToken(token);
        queue.setPosition(1);
        queue.setUpdatedAt(LocalDateTime.now());

        queuePositionRepository.save(queue);

        return token;
    }

    @Override
    public TokenEntity updateStatus(Long tokenId, String status) {

        TokenEntity token = getToken(tokenId);
        TokenEntity.TokenStatus newStatus =
                TokenEntity.TokenStatus.valueOf(status);

        if (token.getStatus() == TokenEntity.TokenStatus.WAITING &&
                newStatus == TokenEntity.TokenStatus.SERVING ||
            token.getStatus() == TokenEntity.TokenStatus.SERVING &&
                newStatus == TokenEntity.TokenStatus.COMPLETED) {

            token.setStatus(newStatus);

            if (newStatus == TokenEntity.TokenStatus.COMPLETED) {
                token.setCompletedAt(LocalDateTime.now());
            }

            return tokenRepository.save(token);
        }

        throw new RuntimeException("invalid status");
    }

    @Override
    public TokenEntity getToken(Long tokenId) {
        return tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }
}`