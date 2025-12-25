package com.example.demo.service;

import com.example.demo.entity.Token;
import java.util.List;

public interface TokenService {
    Token createToken(Long serviceCounterId);
    Token getTokenById(Long tokenId);
    List<Token> getAllTokens();
    Token updateTokenStatus(Long tokenId, String status);
    void deleteToken(Long tokenId);
}