package com.example.demo.service;

import com.example.demo.entity.TokenLog;
import java.util.List;

public interface TokenLogService {
    TokenLog addLog(Long tokenId, String message);

    // RENAMED to match controller/testcases
    List<TokenLog> getLogsByToken(Long tokenId);
}
