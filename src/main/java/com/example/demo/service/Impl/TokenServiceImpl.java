// src/main/java/com/example/demo/service/impl/TokenServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.TokenService;

import java.time.LocalDateTime;
import java.util.List;

public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepo;
    private final ServiceCounterRepository counterRepo;
    private final TokenLogRepository logRepo;
    private final QueuePositionRepository queueRepo;

    public TokenServiceImpl(TokenRepository tokenRepo,
                            ServiceCounterRepository counterRepo,
                            TokenLogRepository logRepo,
                            QueuePositionRepository queueRepo) {
        this.tokenRepo = tokenRepo;
        this.counterRepo = counterRepo;
        this.logRepo = logRepo;
        this.queueRepo = queueRepo;
    }

    @Override
    public Token issueToken(Long counterId) {
        ServiceCounter counter = counterRepo.findById(counterId)
            .orElseThrow(() -> new RuntimeException("Counter not found"));
        if (Boolean.FALSE.equals(counter.getIsActive())) {
            throw new IllegalArgumentException("Counter not active");
        }

        // Generate sequential token number per counter
        int nextNumber = generateTokenNumber(counterId);

        Token token = new Token();
        token.setServiceCounter(counter);
        token.setStatus("WAITING");
        token.setIssuedAt(LocalDateTime.now());
        token.setTokenNumber(nextNumber);
        token = tokenRepo.save(token);

        // Position is existing waiting count + 1
        List<Token> waiting = tokenRepo
            .findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(counterId, "WAITING");
        int pos = waiting.size();

        QueuePosition qp = new QueuePosition();
        qp.setToken(token);
        qp.setPosition(pos);
        queueRepo.save(qp);

        TokenLog log = new TokenLog();
        log.setToken(token);
        log.setMessage("Token issued");
        logRepo.save(log);

        return token;
    }

    @Override
    public Token updateStatus(Long tokenId, String newStatus) {
        Token token = tokenRepo.findById(tokenId)
            .orElseThrow(() -> new RuntimeException("Token not found"));

        String current = token.getStatus();
        if (!isValidTransition(current, newStatus)) {
            throw new IllegalArgumentException("Invalid status transition");
        }

        token.setStatus(newStatus);
        if ("COMPLETED".equals(newStatus) || "CANCELLED".equals(newStatus)) {
            token.setCompletedAt(LocalDateTime.now());
        }
        token = tokenRepo.save(token);

        TokenLog log = new TokenLog();
        log.setToken(token);
        log.setMessage("Status changed to " + newStatus);
        logRepo.save(log);

        return token;
    }

    @Override
    public Token getToken(Long id) {
        return tokenRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Token not found"));
    }

    private boolean isValidTransition(String from, String to) {
        if ("WAITING".equals(from) && ("SERVING".equals(to) || "CANCELLED".equals(to))) return true;
        if ("SERVING".equals(from) && "COMPLETED".equals(to)) return true;
        return false;
    }

    /**
     * Generate sequential token number per counter.
     * Uses the count of existing tokens for that counter + 1.
     */
    private int generateTokenNumber(Long counterId) {
        List<Token> allTokens = tokenRepo.findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(counterId, "WAITING");
        return allTokens.size() + 1;
    }
}
