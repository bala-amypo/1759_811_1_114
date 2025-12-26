package com.example.demo.service.impl;

import com.example.demo.entity.Token;
import com.example.demo.entity.ServiceCounter;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.service.TokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final ServiceCounterRepository counterRepository;
    private final TokenLogRepository logRepository;
    private final QueuePositionRepository queueRepository;

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
        token.setStatus("WAITING");
        token.setIssuedAt(LocalDateTime.now());
        token = tokenRepository.save(token);

        // Create initial queue position
        queueRepository.save(new com.example.demo.entity.QueuePosition(token, 1));

        // Log token creation
        logRepository.save(new com.example.demo.entity.TokenLog(token, "Token issued"));

        return token;
    }

    @Override
    public Token updateStatus(Long tokenId, String newStatus) {
        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        String oldStatus = token.getStatus();
        if (oldStatus.equals("WAITING") && newStatus.equals("SERVING")) {
            token.setStatus("SERVING");
        } else if (oldStatus.equals("SERVING") && (newStatus.equals("COMPLETED") || newStatus.equals("CANCELLED"))) {
            token.setStatus(newStatus);
            token.setCompletedAt(LocalDateTime.now());
        } else {
            throw new IllegalArgumentException("Invalid status transition");
        }

        tokenRepository.save(token);
        logRepository.save(new com.example.demo.entity.TokenLog(token, "Status updated to " + newStatus));
        return token;
    }

    @Override
    public Token getToken(Long tokenId) {
        return tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }

    @Override
    public List<Token> getAllTokens() {
        return tokenRepository.findAll();
    }
}
