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

    private final TokenLogRepository tokenLogRepository;
    private final TokenRepository tokenRepository;

    // ⚠️ Order matters – tests depend on this
    public TokenLogServiceImpl(
            TokenLogRepository tokenLogRepository,
            TokenRepository tokenRepository
    ) {
        this.tokenLogRepository = tokenLogRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public TokenLogEntity addLog(Long tokenId, String message) {

        TokenEntity token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        TokenLogEntity log = new TokenLogEntity();
        log.setToken(token);
        log.setLogMessage(message);
        log.setLoggedAt(LocalDateTime.now());

        return tokenLogRepository.save(log);
    }

    @Override
    public List<TokenLogEntity> getLogs(Long tokenId) {
        return tokenLogRepository.findByTokenId(tokenId);
    }
}