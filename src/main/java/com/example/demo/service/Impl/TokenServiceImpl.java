package com.example.demo.service.impl;

import com.example.demo.entity.Token;
import com.example.demo.entity.ServiceCounter;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.service.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ServiceCounterRepository counterRepository;

    @Override
    public Token createToken(Long serviceCounterId) {
        Optional<ServiceCounter> counterOpt = counterRepository.findById(serviceCounterId);
        if (!counterOpt.isPresent()) {
            throw new RuntimeException("Service counter not found");
        }

        Token token = new Token();
        token.setServiceCounter(counterOpt.get());
        token.setStatus("WAITING");  // default status
        token.setCreatedAt(LocalDateTime.now());

        // Generate token number (optional logic)
        long nextNumber = tokenRepository.count() + 1;
        token.setTokenNumber("T" + nextNumber);

        return tokenRepository.save(token);
    }

    @Override
    public Token getTokenById(Long tokenId) {
        return tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }

    @Override
    public List<Token> getAllTokens() {
        return tokenRepository.findAll();
    }

    @Override
    public Token updateTokenStatus(Long tokenId, String status) {
        Token token = getTokenById(tokenId);
        token.setStatus(status);
        token.setUpdatedAt(LocalDateTime.now());
        return tokenRepository.save(token);
    }

    @Override
    public void deleteToken(Long tokenId) {
        tokenRepository.deleteById(tokenId);
    }
}