package com.example.demo.service;

import com.example.demo.entity.TokenLog;
import java.util.List;

public interface TokenLogService {
    TokenLog logAction(Long tokenId, String action, String performedBy);
    List<TokenLog> getLogsByToken(Long tokenId);
    List<TokenLog> getAllLogs();
}