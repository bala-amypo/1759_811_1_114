package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final ServiceCounterRepository serviceCounterRepository;
    private final TokenLogRepository tokenLogRepository;
    private final QueuePositionRepository queuePositionRepository;

    // ✅ Constructor Injection (exact)
    public TokenServiceImpl(TokenRepository tokenRepository,
                            ServiceCounterRepository serviceCounterRepository,
                            TokenLogRepository tokenLogRepository,
                            QueuePositionRepository queuePositionRepository) {
        this.tokenRepository = tokenRepository;
        this.serviceCounterRepository = serviceCounterRepository;
        this.tokenLogRepository = tokenLogRepository;
        this.queuePositionRepository = queuePositionRepository;
    }

    @Override
    public Token issueToken(Long counterId) {
        ServiceCounter counter = serviceCounterRepository.findById(counterId)
                .orElseThrow(() -> new RuntimeException("Service counter not found"));

        if (counter.getIsActive() == null || !counter.getIsActive()) {
            throw new RuntimeException("Counter not active");
        }

        Token token = new Token();
        token.setServiceCounter(counter);
        token.setStatus("WAITING");
        token.setIssuedAt(LocalDateTime.now());
        token.setTokenNumber("T" + System.currentTimeMillis());

        Token savedToken = tokenRepository.save(token);

        // Create QueuePosition
        QueuePosition position = new QueuePosition();
        position.setToken(savedToken);
        position.setPosition(1);
        position.setUpdatedAt(LocalDateTime.now());
        queuePositionRepository.save(position);

        // Create TokenLog
        TokenLog log = new TokenLog(savedToken, "Token issued");
        tokenLogRepository.save(log);

        return savedToken;
    }

    @Override
    public Token updateStatus(Long tokenId, String status) {
        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        String currentStatus = token.getStatus();
        if ("WAITING".equals(currentStatus) && !"SERVING".equals(status) ||
            "SERVING".equals(currentStatus) && !"COMPLETED".equals(status)) {
            throw new RuntimeException("Invalid status transition");
        }

        token.setStatus(status);
        if ("COMPLETED".equals(status)) {
            token.setCompletedAt(LocalDateTime.now());
        }

        Token updatedToken = tokenRepository.save(token);

        TokenLog log = new TokenLog(updatedToken, "Status changed to " + status);
        tokenLogRepository.save(log);

        return updatedToken;
    }

    @Override
    public Token getToken(Long tokenId) {
        return tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }
}
