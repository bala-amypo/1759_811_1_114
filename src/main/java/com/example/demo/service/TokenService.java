package com.example.demo.service;

import com.example.demo.entity.Token;

import java.util.List;
import java.util.Optional;

public interface TokenService {

    Token createToken(Token token);
    Token updateToken(Long id, Token token);
    Optional<Token> getTokenById(Long id);
    List<Token> getAllTokens();
}