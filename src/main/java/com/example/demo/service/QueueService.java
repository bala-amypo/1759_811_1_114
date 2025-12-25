package com.example.demo.service;

import com.example.demo.entity.Token;
import java.util.List;

public interface QueueService {
    Token getNextToken(Long counterId); // Get the next token for a counter
    List<Token> getQueue(Long counterId); // Get all waiting tokens for a counter
    void removeTokenFromQueue(Long tokenId); // Remove a token from queue after served
}