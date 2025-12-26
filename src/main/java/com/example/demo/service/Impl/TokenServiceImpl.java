package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.TokenService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TokenServiceImpl implements TokenService {

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

    // --------------------------------------------------
    // ISSUE TOKEN
    // --------------------------------------------------
    @Override
    public Token issueToken(Long counterId) {

        ServiceCounter counter = counterRepository.findById(counterId)
                .orElseThrow(() -> new RuntimeException("Counter not found"));

        if (!Boolean.TRUE.equals(counter.getIsActive())) {
            throw new IllegalArgumentException("Counter not active");
        }

        Token token = new Token();
        token.setServiceCounter(counter);
        token.setStatus("WAITING");
        token.setTokenNumber(counter.getCounterName() + "-" + UUID.randomUUID());

        Token savedToken = tokenRepository.save(token);

        List<Token> waiting =
                tokenRepository.findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(
                        counterId, "WAITING"
                );

        QueuePosition position = new QueuePosition();
        position.setToken(savedToken);
        position.setPosition(waiting.size());
        queueRepository.save(position);

        TokenLog log = new TokenLog();
        log.setToken(savedToken);
        log.setMessage("Token issued");
        logRepository.save(log);

        return savedToken;
    }

    // --------------------------------------------------
    // UPDATE STATUS
    // --------------------------------------------------
    @Override
    public Token updateStatus(Long tokenId, String newStatus) {

        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        String current = token.getStatus();

        if ("WAITING".equals(current) && "SERVING".equals(newStatus)) {
            token.setStatus("SERVING");
        } else if ("SERVING".equals(current) &&
                ("COMPLETED".equals(newStatus) || "CANCELLED".equals(newStatus))) {
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

    // --------------------------------------------------
    // GET TOKEN
    // --------------------------------------------------
    @Override
    public Token getToken(Long tokenId) {
        return tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }
}
