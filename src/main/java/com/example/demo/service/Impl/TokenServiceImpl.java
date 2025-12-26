package com.example.demo.service.impl;

import com.example.demo.entity.Token;
import com.example.demo.entity.ServiceCounter;
import com.example.demo.entity.QueuePosition;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.service.TokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
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

        if (!counter.getIsActive()) {
            throw new IllegalArgumentException("Counter is not active");
        }

        Token token = new Token();
        token.setServiceCounter(counter);
        token.setStatus("WAITING");
        token.setIssuedAt(LocalDateTime.now());

        token = tokenRepo.save(token);

        // Create queue position
        QueuePosition qp = new QueuePosition();
        qp.setToken(token);
        qp.setPosition(tokenRepo.findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(counterId, "WAITING").size());
        queueRepo.save(qp);

        // Log creation
        logRepo.save(tokenLog(token, "Token issued"));

        return token;
    }

    @Override
    public Token updateStatus(Long tokenId, String status) {
        Token token = tokenRepo.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        String current = token.getStatus();
        switch (status) {
            case "SERVING":
                if (!"WAITING".equals(current)) throw new IllegalArgumentException("Invalid status transition");
                token.setStatus("SERVING");
                break;
            case "COMPLETED":
                if (!"SERVING".equals(current)) throw new IllegalArgumentException("Invalid status transition");
                token.setStatus("COMPLETED");
                token.setCompletedAt(LocalDateTime.now());
                break;
            case "CANCELLED":
                if ("COMPLETED".equals(current)) throw new IllegalArgumentException("Cannot cancel completed token");
                token.setStatus("CANCELLED");
                token.setCompletedAt(LocalDateTime.now());
                break;
            default:
                throw new IllegalArgumentException("Unknown status");
        }

        token = tokenRepo.save(token);
        logRepo.save(tokenLog(token, "Status updated to " + status));

        return token;
    }

    @Override
    public Token getToken(Long tokenId) {
        return tokenRepo.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }

    @Override
    public List<Token> getAllTokens() {
        return tokenRepo.findAll();
    }

    // Utility method to create a log entry
    private com.example.demo.entity.TokenLog tokenLog(Token token, String message) {
        com.example.demo.entity.TokenLog log = new com.example.demo.entity.TokenLog();
        log.setToken(token);
        log.setMessage(message);
        log.setLoggedAt(LocalDateTime.now());
        return log;
    }
}
