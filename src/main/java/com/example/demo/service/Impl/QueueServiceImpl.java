package com.example.demo.service.impl;

import com.example.demo.entity.Token;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.QueueService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QueueServiceImpl implements QueueService {

    private final TokenRepository tokenRepository;

    public QueueServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token addToken(Token token) {
        token.setStatus("WAITING");
        token.setCreatedAt(java.time.LocalDateTime.now());
        token.setUpdatedAt(java.time.LocalDateTime.now());
        return tokenRepository.save(token);
    }

    @Override
    public Token updateTokenStatus(Long tokenId, String status) {
        Optional<Token> t = tokenRepository.findById(tokenId);
        if (t.isPresent()) {
            Token token = t.get();
            token.setStatus(status);
            token.setUpdatedAt(java.time.LocalDateTime.now());
            return tokenRepository.save(token);
        }
        return null;
    }

    @Override
    public Optional<Token> getNextTokenForCounter(Long counterId) {
        return tokenRepository.findAll().stream()
                .filter(token -> token.getServiceCounter() != null && token.getServiceCounter().getId().equals(counterId))
                .filter(token -> "WAITING".equals(token.getStatus()))
                .sorted((a, b) -> a.getCreatedAt().compareTo(b.getCreatedAt()))
                .findFirst();
    }

    @Override
    public List<Token> getAllTokens() {
        return tokenRepository.findAll();
    }
}