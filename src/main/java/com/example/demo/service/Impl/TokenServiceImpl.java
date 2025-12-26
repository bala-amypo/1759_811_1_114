package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TokenServiceImpl {

    private final TokenRepository tokenRepo;
    private final ServiceCounterRepository counterRepo;
    private final TokenLogRepository logRepo;
    private final QueuePositionRepository queueRepo;

    public TokenServiceImpl(
            TokenRepository tokenRepo,
            ServiceCounterRepository counterRepo,
            TokenLogRepository logRepo,
            QueuePositionRepository queueRepo
    ) {
        this.tokenRepo = tokenRepo;
        this.counterRepo = counterRepo;
        this.logRepo = logRepo;
        this.queueRepo = queueRepo;
    }

    public Token issueToken(Long counterId) {
        ServiceCounter counter = counterRepo.findById(counterId)
                .orElseThrow(() -> new RuntimeException("Counter not found"));

        if (!counter.getIsActive()) {
            throw new IllegalArgumentException("Counter not active");
        }

        Token token = new Token();
        token.setServiceCounter(counter);
        token.setStatus("WAITING");
        token.setTokenNumber(counter.getCounterName() + "-" + UUID.randomUUID());

        token = tokenRepo.save(token);

        List<Token> waiting =
                tokenRepo.findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(counterId, "WAITING");

        QueuePosition qp = new QueuePosition();
        qp.setToken(token);
        qp.setPosition(waiting.size());
        queueRepo.save(qp);

        TokenLog log = new TokenLog();
        log.setToken(token);
        log.setMessage("Token issued");
        logRepo.save(log);

        return token;
    }

    public Token updateStatus(Long tokenId, String status) {
        Token token = tokenRepo.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        if (token.getStatus().equals("WAITING") && status.equals("COMPLETED")) {
            throw new IllegalArgumentException("Invalid status");
        }

        token.setStatus(status);

        if (!status.equals("WAITING")) {
            token.setCompletedAt(LocalDateTime.now());
        }

        token = tokenRepo.save(token);

        TokenLog log = new TokenLog();
        log.setToken(token);
        log.setMessage("Status updated to " + status);
        logRepo.save(log);

        return token;
    }

    public Token getToken(Long tokenId) {
        return tokenRepo.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }
}
