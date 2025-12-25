package com.example.demo.service.impl;

import com.example.demo.entity.Token;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.TokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token createToken(Token token) {
        token.setCreatedAt(LocalDateTime.now());
        token.setUpdatedAt(LocalDateTime.now());
        return tokenRepository.save(token);
    }

    @Override
    public Token updateToken(Long id, Token token) {
        Optional<Token> existing = tokenRepository.findById(id);
        if (existing.isPresent()) {
            Token t = existing.get();
            t.setStatus(token.getStatus());
            t.setServiceCounter(token.getServiceCounter());
            t.setTokenNumber(token.getTokenNumber());
            t.setUpdatedAt(LocalDateTime.now());
            return tokenRepository.save(t);
        }
        return null;
    }

    @Override
    public Optional<Token> getTokenById(Long id) {
        return tokenRepository.findById(id);
    }

    @Override
    public List<Token> getAllTokens() {
        return tokenRepository.findAll();
    }
}