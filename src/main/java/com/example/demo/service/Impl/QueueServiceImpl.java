package com.example.demo.service.impl;

import com.example.demo.entity.Token;
import com.example.demo.entity.TokenLog;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.QueueService;
import com.example.demo.service.TokenLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueueServiceImpl implements QueueService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private TokenLogService tokenLogService;

    @Override
    public Token getNextToken(Long counterId) {
        // Get the first waiting token for the counter
        List<Token> waitingTokens = tokenRepository.findAll().stream()
                .filter(t -> t.getServiceCounter().getId().equals(counterId))
                .filter(t -> "WAITING".equals(t.getStatus()))
                .sorted(Comparator.comparing(Token::getCreatedAt))
                .collect(Collectors.toList());

        if (waitingTokens.isEmpty()) {
            return null; // No tokens waiting
        }

        Token nextToken = waitingTokens.get(0);
        nextToken.setStatus("IN_PROGRESS");
        tokenRepository.save(nextToken);

        // Log the action
        tokenLogService.logAction(nextToken.getId(), "IN_PROGRESS", "System");

        return nextToken;
    }

    @Override
    public List<Token> getQueue(Long counterId) {
        return tokenRepository.findAll().stream()
                .filter(t -> t.getServiceCounter().getId().equals(counterId))
                .filter(t -> "WAITING".equals(t.getStatus()))
                .sorted(Comparator.comparing(Token::getCreatedAt))
                .collect(Collectors.toList());
    }

    @Override
    public void removeTokenFromQueue(Long tokenId) {
        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        token.setStatus("COMPLETED");
        tokenRepository.save(token);

        // Log completion
        tokenLogService.logAction(tokenId, "COMPLETED", "System");
    }
}