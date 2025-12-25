package com.example.demo.service.impl;

import com.example.demo.entity.TokenLog;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.service.TokenLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenLogServiceImpl implements TokenLogService {

    private final TokenLogRepository tokenLogRepository;

    public TokenLogServiceImpl(TokenLogRepository tokenLogRepository) {
        this.tokenLogRepository = tokenLogRepository;
    }

    @Override
    public TokenLog createLog(TokenLog log) {
        return tokenLogRepository.save(log);
    }

    @Override
    public List<TokenLog> getLogsByTokenId(Long tokenId) {
        return tokenLogRepository.findByTokenId(tokenId);
    }
}