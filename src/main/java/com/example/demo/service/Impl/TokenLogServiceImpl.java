package com.example.demo.service.impl;

import com.example.demo.entity.Token;
import com.example.demo.entity.TokenLog;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.TokenLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TokenLogServiceImpl implements TokenLogService {

    @Autowired
    private TokenLogRepository tokenLogRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public TokenLog logAction(Long tokenId, String action, String performedBy) {
        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        TokenLog log = new TokenLog();
        log.setToken(token);
        log.setAction(action);
        log.setPerformedBy(performedBy);
        log.setActionTime(LocalDateTime.now());

        return tokenLogRepository.save(log);
    }

    @Override
    public List<TokenLog> getLogsByToken(Long tokenId) {
        return tokenLogRepository.findByTokenId(tokenId);
    }

    @Override
    public List<TokenLog> getAllLogs() {
        return tokenLogRepository.findAll();
    }
}