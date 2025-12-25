package com.example.demo.service;

import com.example.demo.entity.Token;
import java.util.List;
import java.util.Optional;

public interface QueueService {

    Token addToken(Token token);
    Token updateTokenStatus(Long tokenId, String status);
    Optional<Token> getNextTokenForCounter(Long counterId);
    List<Token> getAllTokens();
}