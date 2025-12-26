package com.example.demo.service;

import com.example.demo.entity.Token;
import com.example.demo.entity.TokenLog;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenLogServiceImpl implements TokenLogService {

    private final TokenLogRepository tokenLogRepository;
    private final TokenRepository tokenRepository;

    // ✅ Constructor Injection (exact)
    public TokenLogServiceImpl(TokenLogRepository tokenLogRepository,
                               TokenRepository tokenRepository) {
        this.tokenLogRepository = tokenLogRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public TokenLog addLog(Long tokenId, String logMessage) {
        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("not found"));

        TokenLog log = new TokenLog(token, logMessage);
        return tokenLogRepository.save(log);
    }

    @Override
    public List<TokenLog> getLogs(Long tokenId) {
        return tokenLogRepository.findByToken_IdOrderByLoggedAtAsc(tokenId);
    }
}
