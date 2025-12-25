package com.example.demo.service;

import com.example.demo.entity.TokenLog;
import java.util.List;

public interface TokenLogService {

    TokenLog createLog(TokenLog log);
    List<TokenLog> getLogsByTokenId(Long tokenId);
}