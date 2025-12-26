package com.example.demo.service.impl;

import com.example.demo.entity.*;
import org.springframework.stereotype.Service;
import com.example.demo.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TokenServiceImpl {

    private final TokenRepository tokenRepository;
    private final ServiceCounterRepository counterRepository;
    private final TokenLogRepository logRepository;
    private final QueuePositionRepository queueRepository;

    public TokenServiceImpl(
            TokenRepository tokenRepository,
            ServiceCounterRepository counterRepository,
            TokenLogRepository logRepository,
            QueuePositionRepository queueRepository
    ) {
        this.tokenRepository = tokenRepository;
        this.counterRepository = counterRepository;
        this.logRepository = logRepository;
        this.queueRepository = queueRepository;
    }

    // -------------------------------------------------
    // Issue Token
    // -------------------------------------------------
    public Token issueToken(Long counterId) {

        ServiceCounter counter = counterRepository.findById(counterId)
                .orElseThrow(() -> new RuntimeException("Counter not found"));

        if (!counter.getIsActive()) {
            throw new IllegalArgumentException("Counter not active");
        }

        Token token = new Token();
        token.setServiceCounter(counter);
        token.setStatus("WAITING");
        token.setTokenNumber(UUID.randomUUID().toString());

        Token savedToken = tokenRepository.save(token);

        List<Token> waiting =
                tokenRepository.findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(
                        counterId, "WAITING"
                );

        QueuePosition qp = new QueuePosition();
        qp.setToken(savedToken);
        qp.setPosition(waiting.size());
        queueRepository.save(qp);

        TokenLog log = new TokenLog();
        log.setToken(savedToken);
        log.setMessage("Token issued");
        logRepository.save(log);

        return savedToken;
    }

    // -------------------------------------------------
    // Update Token Status
    // -------------------------------------------------
    public Token updateStatus(Long tokenId, String newStatus) {

        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        String current = token.getStatus();

        if ("WAITING".equals(current) && "SERVING".equals(newStatus)) {
            token.setStatus("SERVING");
        } else if ("SERVING".equals(current)
                && ("COMPLETED".equals(newStatus) || "CANCELLED".equals(newStatus))) {

            token.setStatus(newStatus);
            token.setCompletedAt(LocalDateTime.now());

        } else {
            throw new IllegalArgumentException("Invalid status transition");
        }

        Token saved = tokenRepository.save(token);

        TokenLog log = new TokenLog();
        log.setToken(saved);
        log.setMessage("Status changed to " + newStatus);
        logRepository.save(log);

        return saved;
    }

    // -------------------------------------------------
    // Get Token
    // -------------------------------------------------
    public Token getToken(Long tokenId) {
        return tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }
}
