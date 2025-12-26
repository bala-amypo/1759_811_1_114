package com.example.demo.service.impl;

import com.example.demo.entity.TokenLog;
import com.example.demo.repository.TokenLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenLogServiceImpl {

    private final TokenLogRepository repository;

    public TokenLogServiceImpl(TokenLogRepository repository) {
        this.repository = repository;
    }

    public List<TokenLog> getLogsByToken(Long tokenId) {
        return repository.findByToken_Id(tokenId);
    }
}
