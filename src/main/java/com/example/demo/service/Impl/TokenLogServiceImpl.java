package com.example.demo.service.impl;

import com.example.demo.entity.Token;
import com.example.demo.entity.TokenLog;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.TokenLogService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TokenLogServiceImpl implements TokenLogService {

    private final TokenLogRepository logRepository;
    private final TokenRepository tokenRepository;

    // Constructor Injection
    public TokenLogServiceImpl(TokenLogRepository logRepository, TokenRepository tokenRepository) {
        this.logRepository = logRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public TokenLog createLog(Long tokenId, String message) throws Exception {
        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new Exception("Token not found"));

        TokenLog log = new TokenLog(token, message);
        return logRepository.save(log);
    }

    @Override
    public List<TokenLog> getLogs(Long tokenId) throws Exception {
        if (!tokenRepository.existsById(tokenId)) {
            throw new Exception("Token not found");
        }
        return logRepository.findByToken_IdOrderByLoggedAtAsc(tokenId);
    }
}
