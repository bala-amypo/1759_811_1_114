package com.example.demo.service.impl;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.entity.Token;
import com.example.demo.entity.QueuePosition;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.service.TokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final QueuePositionRepository queueRepository;
    private final TokenLogRepository logRepository;
    private final ServiceCounterRepository counterRepository;

    public TokenServiceImpl(TokenRepository tokenRepository,
                            ServiceCounterRepository counterRepository,
                            TokenLogRepository logRepository,
                            QueuePositionRepository queueRepository) {
        this.tokenRepository = tokenRepository;
        this.counterRepository = counterRepository;
        this.logRepository = logRepository;
        this.queueRepository = queueRepository;
    }

    @Override
    public Token issueToken(Long counterId) {
        ServiceCounter counter = counterRepository.findById(counterId)
                .orElseThrow(() -> new RuntimeException("Counter not found"));

        if (!counter.getIsActive()) {
            throw new IllegalArgumentException("Counter is not active");
        }

        Token token = new Token();
        token.setServiceCounter(counter);
        token.setIssuedAt(LocalDateTime.now());
        token.setStatus("WAITING");

        // Assign token number (simple example: counterId + timestamp)
        token.setTokenNumber(counter.getCounterName() + "-" + System.currentTimeMillis());

        Token saved = tokenRepository.save(token);

        // Add queue position
        QueuePosition qp = new QueuePosition();
        qp.setToken(saved);
        qp.setPosition(tokenRepository.findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(counterId, "WAITING").size());
        queueRepository.save(qp);

        return saved;
    }

    @Override
    public Token updateStatus(Long tokenId, String newStatus) {
        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        String oldStatus = token.getStatus();

        switch (newStatus) {
            case "SERVING":
                if (!oldStatus.equals("WAITING")) {
                    throw new IllegalArgumentException("Invalid status transition");
                }
                token.setStatus("SERVING");
                break;
            case "COMPLETED":
            case "CANCELLED":
                if (!oldStatus.equals("SERVING") && !oldStatus.equals("WAITING")) {
                    throw new IllegalArgumentException("Invalid status transition");
                }
                token.setStatus(newStatus);
                token.setCompletedAt(LocalDateTime.now());
                break;
            default:
                throw new IllegalArgumentException("Invalid status");
        }

        Token updated = tokenRepository.save(token);

        // Add log entry
        logRepository.save(new com.example.demo.entity.TokenLog(token, "Status changed to " + newStatus));

        return updated;
    }

    @Override
    public Token getToken(Long tokenId) {
        return tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }

    @Override
    public Optional<Token> findByTokenNumber(String tokenNumber) {
        return tokenRepository.findByTokenNumber(tokenNumber);
    }

    @Override
    public List<Token> getAllTokens() {
        return tokenRepository.findAll();
    }
}
