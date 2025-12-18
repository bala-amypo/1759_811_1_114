package com.example.demo.service;

import com.example.demo.entity.TokenLogEntity;
import java.util.List;

public interface TokenLogService {
    TokenLogEntity addLog(Long tokenId, String message);
    List<TokenLogEntity> getLogs(Long tokenId);
}