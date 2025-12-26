package com.example.demo.service.Impl;

import com.example.demo.entity.TokenLog;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.service.TokenLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TokenLogServiceImpl implements TokenLogService {

    private final TokenLogRepository tokenLogRepository;

    public TokenLogServiceImpl(TokenLogRepository tokenLogRepository) {
        this.tokenLogRepository = tokenLogRepository;
    }

    @Override
    public List<TokenLog> getAllLogs() {
        return tokenLogRepository.findAll();
    }

    @Override
    public TokenLog logMessage(Long tokenId, String message) {
        TokenLog log = new TokenLog();
        log.setTokenId(tokenId);
        log.setMessage(message);
        log.setLoggedAt(LocalDateTime.now());

        return tokenLogRepository.save(log);
    }
}
