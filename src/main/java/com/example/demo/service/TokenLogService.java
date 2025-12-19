package com.example.demo.service;

import com.example.demo.entity.Token;
import com.example.demo.entity.TokenLog;

import java.util.List;

public interface TokenLogService {

    TokenLog createLog(Token token, String message);

    List<TokenLog> getLogsByTokenId(Long tokenId);
}
