package com.example.demo.service.impl;

import com.example.demo.entity.Token;
import com.example.demo.entity.ServiceCounter;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.service.TokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final ServiceCounterRepository counterRepository;
    private final TokenLogRepository tokenLogRepository;
    private final QueuePositionRepository queuePositionRepository;

    // Constructor injection in exact required order
    public TokenServiceImpl(TokenRepository tokenRepository,
                            ServiceCounterRepository counterRepository,
                            TokenLogRepository tokenLogRepository,
                            QueuePositionRepository queuePositionRepository) {
        this.tokenRepository = tokenRepository;
        this.counterRepository = counterRepository;
        this.tokenLogRepository = tokenLogRepository;
        this.queuePositionRepository = queuePositionRepository;
    }

    @Override
    public Token issueToken(User user, ServiceCounter counter) {
        if (counter.getIsActive() == null || !counter.getIsActive()) {
            throw new ResourceNotFoundException("Counter not active");
        }

        Token token = new Token();

        // Generate next token number
        Long lastTokenNumber = tokenRepository.findTopByOrderByTokenNumberDesc()
                .map(Token::getTokenNumber)
                .orElse(0L);
        token.setTokenNumber(lastTokenNumber + 1);

        token.setUser(user);
        token.setCounter(counter);
        token.setStatus(Token.Status.WAITING);
        token.setCreatedAt(LocalDateTime.now());

        return tokenRepository.save(token);
    }

    @Override
    public Token updateStatus(Long tokenId, Token.Status newStatus) {
        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));

        // Status chain validation
        if (token.getStatus() == Token.Status.WAITING && newStatus != Token.Status.SERVING) {
            throw new ResourceNotFoundException("Invalid status transition");
        }
        if (token.getStatus() == Token.Status.SERVING && newStatus != Token.Status.COMPLETED) {
            throw new ResourceNotFoundException("Invalid status transition");
        }
        if (token.getStatus() == Token.Status.COMPLETED) {
            throw new ResourceNotFoundException("Token already completed");
        }

        token.setStatus(newStatus);

        if (newStatus == Token.Status.COMPLETED) {
            token.setCompletedAt(LocalDateTime.now());
        }

        return tokenRepository.save(token);
    }

    @Override
    public Token getById(Long tokenId) {
        return tokenRepository.findById(tokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
    }
}
