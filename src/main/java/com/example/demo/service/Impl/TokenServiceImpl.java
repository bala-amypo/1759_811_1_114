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

        Token token = new Token();
        token.setServiceCounter(counter);
        token.setStatus("WAITING");
        token.setIssuedAt(LocalDateTime.now());
        token.setTokenNumber(generateTokenNumber(counter));
        token = tokenRepo.save(token);

        List<Token> waiting = tokenRepo
            .findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(counterId, "WAITING");
        int pos = waiting.size() + 1;

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

    private String generateTokenNumber(ServiceCounter counter) {
        String prefix = counter.getCounterName() != null ? counter.getCounterName() : "C";
        long ts = System.currentTimeMillis() % 100000;
        return prefix + "-" + ts;
    }
}
