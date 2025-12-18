package com.example.demo.service.impl;

import com.example.demo.entity.TokenEntity;
import com.example.demo.entity.ServiceCounterEntity;
import com.example.demo.repository.*;
import com.example.demo.service.TokenService;
import com.example.demo.util.TokenNumberGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final ServiceCounterRepository counterRepository;
    private final TokenLogRepository logRepository;
    private final QueueRepository queueRepository;

    public TokenServiceImpl(
            TokenRepository tokenRepository,
            ServiceCounterRepository counterRepository,
            TokenLogRepository logRepository,
            QueueRepository queueRepository) {

        this.tokenRepository = tokenRepository;
        this.counterRepository = counterRepository;
        this.logRepository = logRepository;
        this.queueRepository = queueRepository;
    }

    public TokenEntity issueToken(Long counterId) {
        ServiceCounterEntity counter = counterRepository.findById(counterId)
                .orElseThrow(() -> new RuntimeException("Counter not found"));

        if (!counter.getIsActive()) {
            throw new RuntimeException("Counter not active");
        }

        TokenEntity token = new TokenEntity();
        token.setTokenNumber(TokenNumberGenerator.generateTokenNumber());
        token.setStatus(TokenEntity.TokenStatus.WAITING);
        token.setCreatedAt(LocalDateTime.now());

        return tokenRepository.save(token);
    }

    public TokenEntity updateStatus(Long tokenId, String status) {
        TokenEntity token = getToken(tokenId);
        TokenEntity.TokenStatus newStatus =
                TokenEntity.TokenStatus.valueOf(status);

        if (token.getStatus() == TokenEntity.TokenStatus.WAITING &&
                newStatus == TokenEntity.TokenStatus.SERVING ||
            token.getStatus() == TokenEntity.TokenStatus.SERVING &&
                newStatus == TokenEntity.TokenStatus.COMPLETED) {

            token.setStatus(newStatus);
        } else {
            throw new RuntimeException("invalid status");
        }

        if (newStatus == TokenEntity.TokenStatus.COMPLETED) {
            token.setCompletedAt(LocalDateTime.now());
        }

        return tokenRepository.save(token);
    }

    public TokenEntity getToken(Long tokenId) {
        return tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }
}