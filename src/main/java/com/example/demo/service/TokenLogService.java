package com.example.demo.service;

import com.example.demo.model.TokenLog;

import java.util.List;

public interface TokenLogService {

    TokenLog addLog(Long tokenId, String logMessage);

    List<TokenLog> getLogs(Long tokenId);
}
