package com.example.demo.service.impl;

import com.example.demo.entity.Token;
import com.example.demo.entity.TokenLog;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.TokenLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenLogServiceImpl implements TokenLogService {

    private final TokenLogRepository tokenLogRepository;
    private final TokenRepository tokenRepository;

    // Constructor Injection (required order)
    public TokenLogServiceImpl(TokenLogRepository tokenLogRepository,
                               TokenRepository tokenRepository) {
        this.tokenLogRepository = tokenLogRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public TokenLog createLog(Token token, String message) {
        if (token == null) {
            throw new ResourceNotFoundException("Token not found");
        }
        TokenLog log = new TokenLog();
        log.setToken(token);
        log.setLogMessage(message);
        return tokenLogRepository.save(log);
    }

    @Override
    public List<TokenLog> getLogsByTokenId(Long tokenId) {
        if (!tokenRepository.existsById(tokenId)) {
            throw new ResourceNotFoundException("Token not found");
        }
        return tokenLogRepository.findByTokenIdOrderByLoggedAtAsc(tokenId);
    }
}
