package com.example.demo.service.impl;

import com.example.demo.entity.TokenEntity;
import com.example.demo.entity.TokenLogEntity;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.TokenLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TokenLogServiceImpl implements TokenLogService {

    private final TokenLogRepository logRepository;
    private final TokenRepository tokenRepository;

    public TokenLogServiceImpl(
            TokenLogRepository logRepository,
            TokenRepository tokenRepository) {
        this.logRepository = logRepository;
        this.tokenRepository = tokenRepository;
    }

    public TokenLogEntity addLog(Long tokenId, String message) {
        TokenEntity token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        TokenLogEntity log = new TokenLogEntity();
        log.setToken(token);
        log.setLogMessage(message);
        log.setLoggedAt(LocalDateTime.now());

        return logRepository.save(log);
    }

    public List<TokenLogEntity> getLogs(Long tokenId) {
        return logRepository.findByTokenId(tokenId);
    }
}